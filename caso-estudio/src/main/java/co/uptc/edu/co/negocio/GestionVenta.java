package co.uptc.edu.co.negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.TransaccionBD;
import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.VentaDAO;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;

public class GestionVenta implements IGestionVenta {

	private static final String PREFIJO_FACTURA = "factura_";
	private static final int DIGITOS_FACTURA = 6;

	private List<Venta> ventas;
	private final VentaDAO ventaDAO;
	private final IGestionInventario gestionInventario;
	private final IGestionContabilidad gestionContabilidad;

	public GestionVenta(VentaDAO ventaDAO, IGestionInventario gestionInventario,
			IGestionContabilidad gestionContabilidad) {

		if (ventaDAO == null) {

			throw new IllegalArgumentException("La ventaDAO no puede ser nulo");
		}
		if (gestionInventario == null) {
			throw new IllegalArgumentException("La gestionInventario no puede ser nula");
		}
		if (gestionContabilidad == null) {
			throw new IllegalArgumentException("La gestionContabilidad no puede ser nula");
		}
		this.ventaDAO = ventaDAO;
		this.gestionInventario = gestionInventario;
		this.gestionContabilidad = gestionContabilidad;

		try {
			ventas = ventaDAO.listarVentas();
		} catch (Exception e) {
			ventas = new ArrayList<>();
			System.out.println("Error al cargar ventas: " + e.getMessage());
		}

	}

	private void recargarVentas() throws Exception {
		ventas = ventaDAO.listarVentas();
	}

	@Override
	public void recargar() throws Exception {
		recargarVentas();
	}

	@Override
	public void registrarVenta(Venta venta) throws Exception {
		validarVenta(venta);

		if (venta.getFechaHora() == null) {
			venta.setFechaHora(LocalDateTime.now());
		}

		if (venta.getEstado() == null) {
			venta.setEstado(EstadoVentaEnum.ACTIVA);
		}

		calcularTotales(venta);

		TransaccionBD.ejecutar(conexion -> {
			ventaDAO.guardarVenta(conexion, venta);
			gestionInventario.registrarSalidaPorVenta(conexion, venta);
			gestionContabilidad.registrarIngresoPorVenta(conexion, venta);
		});

		ventas.add(0, venta);
	}

	@Override
	public List<Venta> obtenerVentas() {
		return new ArrayList<>(ventas);
	}

	@Override
	public Venta buscarVentaPorNumero(String numeroFactura) throws Exception {
		if (numeroFactura == null) {
			return null;
		}

		return ventaDAO.buscarVentaPorNumero(numeroFactura);
	}

	private void validarVenta(Venta venta) throws Exception {

		if (venta == null) {
			throw new Exception("La venta no puede ser nula");
		}

		if (venta.getNumeroFactura() == null || venta.getNumeroFactura().trim().isEmpty()) {

			throw new Exception("El numero de factura es obligatorio");
		}

		if (venta.getCliente() == null || venta.getCliente().trim().isEmpty()) {
			throw new Exception("El cliente es obligatorio");
		}

		if (venta.getFormaPago() == null || venta.getFormaPago().trim().isEmpty()) {
			throw new Exception("La forma de pago es obligatorio");
		}

		if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
			throw new Exception("La venta debe tener almenos un producto");
		}

		for (DetalleVenta detalle : venta.getDetalles()) {
			validarDetalleVenta(detalle);
		}
	}

	private void validarDetalleVenta(DetalleVenta detalle) throws Exception {

		if (detalle == null) {
			throw new Exception("El detalle de venta no puede ser nulo");
		}

		if (detalle.getProducto() == null) {
			throw new Exception("Cada detalle debe tener un producto");
		}

		if (detalle.getCantidad() <= 0) {
			throw new Exception("La cantidad vendida debe ser mayor a cero.");
		}
		if (detalle.getPrecioUnitario() <= 0) {
			throw new Exception("El precio unitario debe ser mayor a cero.");
		}

		if (detalle.getSubtotal() < 0) {
			throw new Exception("El subtotal del detalle no puede ser negativo");
		}
	}

	private void calcularTotales(Venta venta) {
		double subtotal = 0;

		for (DetalleVenta detalle : venta.getDetalles()) {
			double subtotalDetalle = detalle.getCantidad() * detalle.getPrecioUnitario();
			detalle.setSubtotal(subtotalDetalle);
			subtotal += subtotalDetalle;
		}

		double impuestos = subtotal * 0.19;
		double total = subtotal + impuestos;

		venta.setSubTotal(subtotal);
		venta.setImpuestos(impuestos);
		venta.setTotal(total);

	}

	@Override
	public void anularVenta(String numeroFactura, String motivo) throws Exception {
		Venta venta = buscarVentaPorNumero(numeroFactura);
		if (venta == null) {
			throw new Exception("No se encontro la venta a anular.");
		}

		if (venta.getEstado() == EstadoVentaEnum.ANULADA) {
			throw new Exception("La venta ya esta anulada.");
		}

		if (venta.getEstado() == EstadoVentaEnum.DEVUELTA) {
			throw new Exception("No se puede anular una venta que ya tiene devolucion registrada.");
		}

		if (motivo == null || motivo.trim().isEmpty()) {
			throw new Exception("Debe ingresar un motivo de anulacion");
		}

		venta.setEstado(EstadoVentaEnum.ANULADA);
		TransaccionBD.ejecutar(conexion -> {
			ventaDAO.actualizarVenta(conexion, venta);
			gestionInventario.registrarEntradaPorAnulacion(conexion, venta, motivo.trim());
			gestionContabilidad.registrarReversoPorAnulacionVenta(conexion, venta, motivo.trim());
		});
		recargarVentas();
	}

	@Override
	public String generarNumeroFactura() {
		return PREFIJO_FACTURA + String.format("%0" + DIGITOS_FACTURA + "d", obtenerSiguienteConsecutivoFactura());
	}

	private int obtenerSiguienteConsecutivoFactura() {
		int mayorConsecutivo = 0;

		for (Venta venta : ventas) {
			String numeroFactura = venta.getNumeroFactura();

			if (numeroFactura != null && numeroFactura.startsWith(PREFIJO_FACTURA)) {
				String consecutivo = numeroFactura.substring(PREFIJO_FACTURA.length());

				if (consecutivo.matches("\\d+")) {
					mayorConsecutivo = Math.max(mayorConsecutivo, Integer.parseInt(consecutivo));
				}
			}
		}

		return mayorConsecutivo + 1;
	}

}
