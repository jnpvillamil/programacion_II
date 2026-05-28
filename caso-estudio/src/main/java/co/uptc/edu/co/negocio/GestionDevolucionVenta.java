package co.uptc.edu.co.negocio;

import java.time.LocalDateTime;
import java.util.List;

import co.uptc.edu.co.interfaces.DevolucionVentaDAO;
import co.uptc.edu.co.interfaces.IGestionDevolucionVenta;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.VentaDAO;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.DevolucionVenta;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;

public class GestionDevolucionVenta implements IGestionDevolucionVenta {

	private final VentaDAO ventaDAO;
	private final DevolucionVentaDAO devolucionVentaDAO;
	private final IGestionInventario gestionInventario;

	public GestionDevolucionVenta(VentaDAO ventaDAO, DevolucionVentaDAO devolucionVentaDAO,
			IGestionInventario gestionInventario) {

		if (ventaDAO == null) {
			throw new IllegalArgumentException("La ventaDAO no puede ser nula.");
		}

		if (devolucionVentaDAO == null) {
			throw new IllegalArgumentException("La devolucionVentaDAO no puede ser nula.");
		}

		if (gestionInventario == null) {
			throw new IllegalArgumentException("La gestionInventario no puede ser nula.");
		}

		this.ventaDAO = ventaDAO;
		this.devolucionVentaDAO = devolucionVentaDAO;
		this.gestionInventario = gestionInventario;
	}

	@Override
	public void devolverVenta(String numeroFactura, String codigoProducto, int cantidad, String motivo) throws Exception {
		Venta venta = ventaDAO.buscarVentaPorNumero(numeroFactura);

		if (venta == null) {
			throw new Exception("No se encontro la venta a devolver.");
		}

		validarDevolucion(venta, codigoProducto, cantidad, motivo);

		DetalleVenta detalleDevuelto = buscarDetalleVenta(venta, codigoProducto);

		if (detalleDevuelto == null) {
			throw new Exception("El producto seleccionado no pertenece a la venta.");
		}

		if (cantidad > detalleDevuelto.getCantidad()) {
			throw new Exception("La cantidad a devolver no puede superar la cantidad vendida.");
		}

		DevolucionVenta devolucion = crearDevolucion(venta, detalleDevuelto, cantidad, motivo.trim());

		gestionInventario.registrarEntrada(codigoProducto, cantidad,
				"Entrada por devolucion de venta " + venta.getNumeroFactura() + ". Motivo: " + motivo.trim());

		devolucionVentaDAO.guardarDevolucion(devolucion);

		venta.setEstado(EstadoVentaEnum.DEVUELTA);
		ventaDAO.actualizarVenta(venta);
	}

	@Override
	public DevolucionVenta buscarDevolucionPorCodigo(String codigoDevolucion) throws Exception {
		if (codigoDevolucion == null || codigoDevolucion.trim().isEmpty()) {
			throw new Exception("El codigo de devolucion es obligatorio.");
		}

		return devolucionVentaDAO.buscarPorCodigo(codigoDevolucion.trim());
	}

	@Override
	public List<DevolucionVenta> obtenerDevolucionesPorFactura(String numeroFactura) throws Exception {
		if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
			throw new Exception("El numero de factura es obligatorio.");
		}

		return devolucionVentaDAO.buscarPorFactura(numeroFactura.trim());
	}

	@Override
	public List<DevolucionVenta> obtenerDevoluciones() throws Exception {
		return devolucionVentaDAO.listarDevoluciones();
	}

	private void validarDevolucion(Venta venta, String codigoProducto, int cantidad, String motivo) throws Exception {
		if (venta.getEstado() == EstadoVentaEnum.ANULADA) {
			throw new Exception("No se puede devolver una venta anulada.");
		}

		if (venta.getEstado() == EstadoVentaEnum.DEVUELTA) {
			throw new Exception("La venta ya tiene una devolucion registrada.");
		}

		if (codigoProducto == null || codigoProducto.trim().isEmpty()) {
			throw new Exception("Debe seleccionar un producto para devolver.");
		}

		if (cantidad <= 0) {
			throw new Exception("La cantidad a devolver debe ser mayor que cero.");
		}

		if (motivo == null || motivo.trim().isEmpty()) {
			throw new Exception("Debe ingresar un motivo de devolucion.");
		}
	}

	private DetalleVenta buscarDetalleVenta(Venta venta, String codigoProducto) {
		if (venta.getDetalles() == null) {
			return null;
		}

		for (DetalleVenta detalle : venta.getDetalles()) {
			if (detalle.getProducto() != null && codigoProducto.equals(detalle.getProducto().getCodigoProducto())) {
				return detalle;
			}
		}

		return null;
	}

	private DevolucionVenta crearDevolucion(Venta venta, DetalleVenta detalleDevuelto, int cantidad, String motivo)
			throws Exception {

		String codigoProducto = detalleDevuelto.getProducto().getCodigoProducto();
		String nombreProducto = detalleDevuelto.getProducto().getNombreProducto();
		double valorDevuelto = cantidad * detalleDevuelto.getPrecioUnitario();

		return new DevolucionVenta(generarCodigoDevolucion(), venta.getNumeroFactura(), codigoProducto, nombreProducto,
				cantidad, valorDevuelto, LocalDateTime.now(), motivo);
	}

	private String generarCodigoDevolucion() throws Exception {
		int mayor = 0;

		for (DevolucionVenta devolucion : devolucionVentaDAO.listarDevoluciones()) {
			String codigo = devolucion.getCodigoDevolucion();

			if (codigo != null && codigo.matches("DEV\\d{5}")) {
				int numero = Integer.parseInt(codigo.substring(3));

				if (numero > mayor) {
					mayor = numero;
				}
			}
		}

		return String.format("DEV%05d", mayor + 1);
	}
}
