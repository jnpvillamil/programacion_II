package co.uptc.edu.co.negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.TransaccionBD;
import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.VentaDAO;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;

public class GestionVenta implements IGestionVenta {

	private static final String PREFIJO_FACTURA = "factura_";
	private static final int DIGITOS_FACTURA = 6;
	private static final double IVA = 0.19;

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
		validarFacturaNoRegistrada(venta.getNumeroFactura());

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
	public List<Venta> obtenerVentasPorCliente(Cliente cliente) throws Exception {
		if (cliente == null) {
			throw new Exception("El cliente es obligatorio.");
		}

		List<Venta> ventasDelCliente = new ArrayList<>();
		String codigoCliente = cliente.getCodigo() != null ? cliente.getCodigo().trim() : "";
		String nombreCliente = cliente.getNombre() != null ? cliente.getNombre().trim() : "";
		String clienteRegistrado = cliente.toString();

		for (Venta venta : ventas) {
			String codigoVenta = venta.getCodigoCliente();
			if (codigoVenta != null && codigoVenta.trim().equalsIgnoreCase(codigoCliente)) {
				ventasDelCliente.add(venta);
				continue;
			}

			String clienteVenta = venta.getCliente();

			if (clienteVenta == null) {
				continue;
			}

			String clienteNormalizado = clienteVenta.trim();
			boolean coincidePorCodigo = !codigoCliente.isEmpty()
					&& clienteNormalizado.toLowerCase().contains(codigoCliente.toLowerCase());
			boolean coincidePorNombre = !nombreCliente.isEmpty()
					&& clienteNormalizado.toLowerCase().contains(nombreCliente.toLowerCase());

			if (clienteNormalizado.equalsIgnoreCase(clienteRegistrado) || coincidePorCodigo || coincidePorNombre) {
				ventasDelCliente.add(venta);
			}
		}

		return ventasDelCliente;
	}

	@Override
	public Venta buscarVentaPorNumero(String numeroFactura) throws Exception {
		if (numeroFactura == null) {
			return null;
		}

		return ventaDAO.buscarVentaPorNumero(numeroFactura);
	}

	private void validarFacturaNoRegistrada(String numeroFactura) throws Exception {
		for (Venta ventaRegistrada : ventas) {
			if (ventaRegistrada.getNumeroFactura() != null
					&& ventaRegistrada.getNumeroFactura().equalsIgnoreCase(numeroFactura)) {
				throw new Exception("Ya existe una venta con ese numero de factura.");
			}
		}

		if (ventaDAO.buscarVentaPorNumero(numeroFactura) != null) {
			throw new Exception("Ya existe una venta con ese numero de factura.");
		}
	}

	private void validarVenta(Venta venta) throws Exception {

		if (venta == null) {
			throw new Exception("La venta no puede ser nula");
		}

		if (venta.getNumeroFactura() == null || venta.getNumeroFactura().trim().isEmpty()) {

			throw new Exception("El numero de factura es obligatorio");
		}

		if (venta.getCodigoCliente() == null || venta.getCodigoCliente().trim().isEmpty()) {
			throw new Exception("El codigo del cliente es obligatorio");
		}

		if (venta.getCliente() == null || venta.getCliente().trim().isEmpty()) {
			throw new Exception("El cliente es obligatorio");
		}

		if (venta.getFormaPago() == null) {
		    throw new Exception("La forma de pago es obligatoria");
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
		double impuestos = 0;

		for (DetalleVenta detalle : venta.getDetalles()) {
			double subtotalDetalle = calcularSubtotalDetalle(detalle);
			detalle.setSubtotal(subtotalDetalle);
			subtotal += subtotalDetalle;
			impuestos += calcularIvaDetalle(detalle);
		}

		double total = subtotal + impuestos;

		venta.setSubTotal(subtotal);
		venta.setImpuestos(impuestos);
		venta.setTotal(total);

	}

	@Override
	public double calcularSubtotalDetalleVenta(Producto producto, int cantidad) throws Exception {
		validarProductoDetalle(producto);
		validarCantidadDetalle(cantidad);
		return producto.getPrecioVenta() * cantidad;
	}

	@Override
	public double calcularIvaDetalleVenta(Producto producto, int cantidad) throws Exception {
		validarProductoDetalle(producto);
		validarCantidadDetalle(cantidad);
		double subtotal = calcularSubtotalDetalleVenta(producto, cantidad);
		return producto.isAplicaIva() ? subtotal * IVA : 0;
	}

	@Override
	public double calcularSubtotalVenta(List<DetalleVenta> detalles) throws Exception {
		validarDetallesParaCalculo(detalles);
		double subtotal = 0;
		for (DetalleVenta detalle : detalles) {
			subtotal += calcularSubtotalDetalle(detalle);
		}
		return subtotal;
	}

	@Override
	public double calcularImpuestosVenta(List<DetalleVenta> detalles) throws Exception {
		validarDetallesParaCalculo(detalles);
		double impuestos = 0;
		for (DetalleVenta detalle : detalles) {
			impuestos += calcularIvaDetalle(detalle);
		}
		return impuestos;
	}

	@Override
	public double calcularTotalVenta(List<DetalleVenta> detalles) throws Exception {
		return calcularSubtotalVenta(detalles) + calcularImpuestosVenta(detalles);
	}

	private double calcularSubtotalDetalle(DetalleVenta detalle) {
		return detalle.getCantidad() * detalle.getPrecioUnitario();
	}

	private double calcularIvaDetalle(DetalleVenta detalle) {
		Producto producto = detalle.getProducto();
		if (producto == null || !producto.isAplicaIva()) {
			return 0;
		}
		return calcularSubtotalDetalle(detalle) * IVA;
	}

	private void validarDetallesParaCalculo(List<DetalleVenta> detalles) throws Exception {
		if (detalles == null) {
			throw new Exception("Los detalles de venta son obligatorios.");
		}
		for (DetalleVenta detalle : detalles) {
			validarDetalleVenta(detalle);
		}
	}

	private void validarProductoDetalle(Producto producto) throws Exception {
		if (producto == null) {
			throw new Exception("Cada detalle debe tener un producto.");
		}
		if (producto.getPrecioVenta() <= 0) {
			throw new Exception("El precio unitario debe ser mayor que cero.");
		}
	}

	private void validarCantidadDetalle(int cantidad) throws Exception {
		if (cantidad <= 0) {
			throw new Exception("La cantidad debe ser mayor que cero.");
		}
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

		if (venta.getEstado() == EstadoVentaEnum.DEVUELTA
				|| venta.getEstado() == EstadoVentaEnum.PARCIALMENTE_DEVUELTA) {
			throw new Exception("No se puede anular una venta que ya tiene devolucion registrada.");
		}

		if (motivo == null || motivo.trim().isEmpty()) {
			throw new Exception("Debe ingresar un motivo de anulacion");
		}

		venta.setEstado(EstadoVentaEnum.ANULADA);
		venta.setMotivoAnulacion(motivo.trim());
		venta.setFechaAnulacion(LocalDateTime.now());
		TransaccionBD.ejecutar(conexion -> {
			ventaDAO.actualizarVenta(conexion, venta);
			gestionInventario.registrarEntradaPorAnulacion(conexion, venta, motivo.trim());
			gestionContabilidad.registrarReversoPorAnulacionVenta(conexion, venta, motivo.trim());
		});
		actualizarVentaEnMemoria(venta);
	}

	private void actualizarVentaEnMemoria(Venta ventaActualizada) {
		for (int i = 0; i < ventas.size(); i++) {
			Venta venta = ventas.get(i);

			if (venta.getNumeroFactura() != null
					&& venta.getNumeroFactura().equalsIgnoreCase(ventaActualizada.getNumeroFactura())) {
				ventas.set(i, ventaActualizada);
				return;
			}
		}
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
