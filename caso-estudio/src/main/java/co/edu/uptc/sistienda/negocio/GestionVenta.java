package co.edu.uptc.sistienda.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.uptc.sistienda.interfaces.IGestionVenta;
import co.edu.uptc.sistienda.modelo.DetalleDevolucion;
import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Devolucion;
import co.edu.uptc.sistienda.modelo.Venta;

public class GestionVenta {

	private IGestionVenta repositorioVenta;
	private GestionProducto gestionProducto;
	private GestionContabilidad gestionContabilidad;
	private int correlativoFactura = 1;
	private int correlativoDevolucion = 1;
	private List<Devolucion> devoluciones = new ArrayList<>();

	public GestionVenta(IGestionVenta repositorioVenta, GestionProducto gestionProducto) {
		this(repositorioVenta, gestionProducto, null);
	}

	public GestionVenta(IGestionVenta repositorioVenta, GestionProducto gestionProducto,
			GestionContabilidad gestionContabilidad) {
		this.repositorioVenta = repositorioVenta;
		this.gestionProducto = gestionProducto;
		this.gestionContabilidad = gestionContabilidad;
		this.correlativoFactura = calcularSiguienteConsecutivoFactura();
	}

	public String generarNumeroFactura() {
		return String.format("FV-%05d", correlativoFactura++);
	}

	public void resgistrarVenta(Venta venta) throws Exception {
		if (venta.getCliente() == null) {
			throw new Exception("Debe selecionar un cliente para la venta");
		}
		if (venta.getItems() == null || venta.getItems().isEmpty()) {
			throw new Exception("La venta debe tener al menos un producto");
		}
		if (venta.getFormaPago() == null) {
			throw new Exception("Debe seleccionar una forma de pago");
		}

		for (DetalleVenta item : venta.getItems()) {
			int stock = item.getProducto().getStockActual();
			if (item.getCantidad() > stock) {
				throw new Exception("Stock insuficiente para " + item.getProducto().getNombreProducto() + "Disponible: "
						+ stock + ", solicitado: " + item.getCantidad());
			}
		}

		for (DetalleVenta item : venta.getItems()) {
			item.getProducto().setStockActual(item.getProducto().getStockActual() - item.getCantidad());
			gestionProducto.modificarProducto(item.getProducto());
		}
		venta.setCufe(generarCufe(venta));
		venta.setValorEnLetras(convertirValorALetras(venta.getTotal()));
		repositorioVenta.guardarVenta(venta);
		if (gestionContabilidad != null) {
			gestionContabilidad.registrarMovimientoPorVenta(venta);
		}
	}

	public void anularVenta(String numeroFactura) throws Exception {
		Venta venta = repositorioVenta.buscarVentaPorNumeroFactura(numeroFactura);
		if (venta == null) {
			throw new Exception("No se encontró la venta: " + numeroFactura);
		}
		if (venta.isAnulada()) {
			throw new Exception("La venta ya está anulada: ");
		}
		for (DetalleVenta item : venta.getItems()) {
			item.getProducto().setStockActual(item.getProducto().getStockActual() + item.getCantidad());
			gestionProducto.modificarProducto(item.getProducto());
		}
		repositorioVenta.anularVenta(numeroFactura);
		if (gestionContabilidad != null) {
			gestionContabilidad.anularMovimientoPorDocumento(numeroFactura);
		}
	}

	public Venta consultarVentaPorFactura(String numeroFactura) {
		return repositorioVenta.buscarVentaPorNumeroFactura(numeroFactura);
	}

	public List<Venta> obtenerListaVentas() {
		return repositorioVenta.obtenerListaVentas();
	}

	public List<Venta> consultarVentasPorFecha(LocalDate fecha) {
		return repositorioVenta.obtenerListaVentas().stream().filter(v -> v.getFechaHora().toLocalDate().equals(fecha))
				.collect(Collectors.toList());
	}

	public List<Venta> consultarHistorialComprasCliente(String codigoCliente) {
		return repositorioVenta
				.obtenerListaVentas().stream().filter(v -> v.getCliente() != null
						&& v.getCliente().getCodigoCliente().equalsIgnoreCase(codigoCliente) && !v.isAnulada())
				.collect(Collectors.toList());
	}

	public List<Venta> consultarVentasPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
		return repositorioVenta.obtenerListaVentas().stream().filter(v -> {
			LocalDate fechaVenta = v.getFechaHora().toLocalDate();
			return (fechaInicio == null || !fechaVenta.isBefore(fechaInicio))
					&& (fechaFin == null || !fechaVenta.isAfter(fechaFin));
		}).collect(Collectors.toList());
	}

	public String generarNumeroDevolucion() {
		return String.format("DEV-%05d", correlativoDevolucion++);
	}

	// Registra una devolución parcial o total de una venta, cada producto devuelto,
	// se devuelve el stock al inventario
	public void registrarDevolucion(String numeroFactura, List<DetalleDevolucion> detalles, String motivo)
			throws Exception {
		Venta venta = repositorioVenta.buscarVentaPorNumeroFactura(numeroFactura);
		if (venta == null) {
			throw new Exception("No se encontró la venta: " + numeroFactura);
		}
		if (venta.isAnulada()) {
			throw new Exception("No se puede devolver sobre una factura anulada");
		}
		if (detalles == null || detalles.isEmpty()) {
			throw new Exception("Selecione al menos un producto para devolver");
		}
		if (motivo == null || motivo.trim().isEmpty()) {
			throw new Exception("Ingrese el motivo de la devolución");
		}

		// Reintregra al inventario solo las unidades que el cliente está devolviendo
		for (DetalleDevolucion detalle : detalles) {
			DetalleVenta itemOriginal = detalle.getVentaOriginal();
			int stockActual = itemOriginal.getProducto().getStockActual();
			itemOriginal.getProducto().setStockActual(stockActual + detalle.getCantidadDevuelta());
			gestionProducto.modificarProducto(itemOriginal.getProducto());
		}
		devoluciones.add(new Devolucion(generarNumeroDevolucion(), venta, detalles, motivo));
	}

	public List<Devolucion> obtenerListaDevoluciones() {
		return devoluciones;
	}

	private int calcularSiguienteConsecutivoFactura() {
		int mayor = 0;
		for (Venta venta : repositorioVenta.obtenerListaVentas()) {
			String numero = venta.getNumeroFactura();
			if (numero != null && numero.startsWith("FV-")) {
				try {
					mayor = Math.max(mayor, Integer.parseInt(numero.substring(3)));
				} catch (NumberFormatException ignored) {
					// Si hay una factura con formato manual, no afecta el consecutivo automatico.
				}
			}
		}
		return mayor + 1;
	}

	private String generarCufe(Venta venta) {

		return "CUFE-" + venta.getNumeroFactura() + "-" + System.currentTimeMillis();
	}

	private String convertirValorALetras(double total) {

		long valor = Math.round(total);

		return valor + " PESOS M/CTE";
	}

}