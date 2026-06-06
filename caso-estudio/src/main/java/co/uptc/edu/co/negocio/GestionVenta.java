package co.uptc.edu.co.negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.TransaccionBD;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;

public class GestionVenta  {

	private static final String PREFIJO_FACTURA = "FV-";
	private static final String PREFIJO_FACTURA_ANTERIOR = "factura_";
	private static final int DIGITOS_FACTURA = 6;
	private static final double IVA = 0.19;

	private List<Venta> ventas;
	private final IGestionVenta gestionVenta;
	private final GestionInventario gestionInventario;
	private final GestionContabilidad gestionContabilidad;

	public GestionVenta(IGestionVenta gestionVenta, GestionInventario gestionInventario,
			GestionContabilidad gestionContabilidad) {

		if (gestionVenta == null) {
			throw new IllegalArgumentException("La gestionVenta no puede ser nula");
		}
		if (gestionInventario == null) {
			throw new IllegalArgumentException("La gestionInventario no puede ser nula");
		}
		if (gestionContabilidad == null) {
			throw new IllegalArgumentException("La gestionContabilidad no puede ser nula");
		}

		this.gestionVenta = gestionVenta;
		this.gestionInventario = gestionInventario;
		this.gestionContabilidad = gestionContabilidad;

		try {
			ventas = gestionVenta.listar();
		} catch (Exception e) {
			ventas = new ArrayList<>();
			throw new IllegalStateException("Error al cargar ventas.", e);
		}
	}
	private void recargarVentas() throws Exception {
		ventas = gestionVenta.listar();
	}


	public void recargar() throws Exception {
		recargarVentas();
	}

	public void registrarVenta(Venta venta) throws Exception {
		System.out.println("GV 1. Antes validarVenta");
		validarVenta(venta);

		System.out.println("GV 2. Antes validarFacturaNoRegistrada");
		validarFacturaNoRegistrada(venta.getNumeroFactura());

		if (venta.getFechaHora() == null) {
			venta.setFechaHora(LocalDateTime.now());
		}

		if (venta.getEstado() == null) {
			venta.setEstado(EstadoVentaEnum.ACTIVA);
		}

		System.out.println("GV 3. Antes calcularTotales");
		calcularTotales(venta);

		System.out.println("GV 4. Antes transaccion");

		TransaccionBD.ejecutar(conexion -> {
			System.out.println("GV 5. Antes guardar venta");
			gestionVenta.guardar(conexion, venta);

			System.out.println("GV 6. Antes salida inventario");
			gestionInventario.registrarSalidaPorVenta(conexion, venta);

			System.out.println("GV 7. Antes ingreso contabilidad");
			gestionContabilidad.registrarIngresoPorVenta(conexion, venta);

			System.out.println("GV 8. Fin transaccion");
		});

		System.out.println("GV 9. Despues transaccion");

		ventas.add(0, venta);

		System.out.println("GV 10. Venta agregada en memoria");
	}

	public List<Venta> obtenerVentas() {
		return new ArrayList<>(ventas);
	}


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


	public Venta buscarVentaPorNumero(String numeroFactura) throws Exception {
		if (numeroFactura == null) {
			return null;
		}

		return gestionVenta.buscar(numeroFactura);
	}

	private void validarFacturaNoRegistrada(String numeroFactura) throws Exception {
		for (Venta ventaRegistrada : ventas) {
			if (ventaRegistrada.getNumeroFactura() != null
					&& ventaRegistrada.getNumeroFactura().equalsIgnoreCase(numeroFactura)) {
				throw new Exception("Ya existe una venta con ese numero de factura.");
			}
		}

		if (gestionVenta.buscar(numeroFactura) != null) {
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

	public double calcularSubtotalDetalleVenta(Producto producto, int cantidad) throws Exception {
		validarProductoDetalle(producto);
		validarCantidadDetalle(cantidad);
		return producto.getPrecioVenta() * cantidad;
	}


	public double calcularIvaDetalleVenta(Producto producto, int cantidad) throws Exception {
		validarProductoDetalle(producto);
		validarCantidadDetalle(cantidad);
		double subtotal = calcularSubtotalDetalleVenta(producto, cantidad);
		return producto.isAplicaIva() ? subtotal * IVA : 0;
	}

	public double calcularSubtotalVenta(List<DetalleVenta> detalles) throws Exception {
		validarDetallesParaCalculo(detalles);
		double subtotal = 0;
		for (DetalleVenta detalle : detalles) {
			subtotal += calcularSubtotalDetalle(detalle);
		}
		return subtotal;
	}


	public double calcularImpuestosVenta(List<DetalleVenta> detalles) throws Exception {
		validarDetallesParaCalculo(detalles);
		double impuestos = 0;
		for (DetalleVenta detalle : detalles) {
			impuestos += calcularIvaDetalle(detalle);
		}
		return impuestos;
	}


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

		EstadoVentaEnum estadoAnterior = venta.getEstado();
		String motivoAnterior = venta.getMotivoAnulacion();
		LocalDateTime fechaAnulacionAnterior = venta.getFechaAnulacion();
		String motivoAnulacion = motivo.trim();

		try {
			venta.setEstado(EstadoVentaEnum.ANULADA);
			venta.setMotivoAnulacion(motivoAnulacion);
			venta.setFechaAnulacion(LocalDateTime.now());

			TransaccionBD.ejecutar(conexion -> {
				gestionVenta.actualizar(conexion, venta);
				gestionInventario.registrarEntradaPorAnulacion(conexion, venta, motivoAnulacion);
				gestionContabilidad.registrarReversoPorAnulacionVenta(conexion, venta, motivoAnulacion);
			});
		} catch (Exception e) {
			venta.setEstado(estadoAnterior);
			venta.setMotivoAnulacion(motivoAnterior);
			venta.setFechaAnulacion(fechaAnulacionAnterior);
			throw e;
		}

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


	public String generarNumeroFactura() {
		return PREFIJO_FACTURA + String.format("%0" + DIGITOS_FACTURA + "d", obtenerSiguienteConsecutivoFactura());
	}

	private int obtenerSiguienteConsecutivoFactura() {
		int mayorConsecutivo = 0;

		for (Venta venta : ventas) {
			String numeroFactura = venta.getNumeroFactura();

			if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
				continue;
			}

			Integer consecutivo = obtenerConsecutivoFactura(numeroFactura);
			if (consecutivo != null) {
				mayorConsecutivo = Math.max(mayorConsecutivo, consecutivo);
			}
		}

		return mayorConsecutivo + 1;
	}

	private Integer obtenerConsecutivoFactura(String numeroFactura) {
		String numeroNormalizado = numeroFactura.trim();
		String consecutivo = null;

		if (numeroNormalizado.toUpperCase().startsWith(PREFIJO_FACTURA)) {
			consecutivo = numeroNormalizado.substring(PREFIJO_FACTURA.length());
		} else if (numeroNormalizado.startsWith(PREFIJO_FACTURA_ANTERIOR)) {
			consecutivo = numeroNormalizado.substring(PREFIJO_FACTURA_ANTERIOR.length());
		}

		if (consecutivo == null || !consecutivo.matches("\\d+")) {
			return null;
		}

		return Integer.parseInt(consecutivo);
	}

}
