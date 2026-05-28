package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.ProductoDAO;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.MovimientoInventario;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.TipoMovimientoInventarioEnum;

public class GestionInventario implements IGestionInventario {

	private final ProductoDAO productoDAO;
	private List<MovimientoInventario> movimientos;

	public GestionInventario(ProductoDAO productoDAO) {
		if (productoDAO == null) {
			throw new IllegalArgumentException("El ProductoDAO no puede ser nulo.");
		}

		this.productoDAO = productoDAO;
		this.movimientos = new ArrayList<>();
	}

	@Override
	public void validarStockDisponible(List<DetalleVenta> detalles) throws Exception {
		if (detalles == null || detalles.isEmpty()) {
			throw new Exception("Debe existir al menos un producto para validar stock.");
		}

		for (DetalleVenta detalle : detalles) {
			validarDetalle(detalle);

			String codigoProducto = detalle.getProducto().getCodigoProducto();
			Producto producto = productoDAO.buscarPorCodigo(codigoProducto);

			if (producto == null) {
				throw new Exception("No existe el producto " + codigoProducto + ".");
			}

			if (!producto.estaActivo()) {
				throw new Exception("El producto " + producto.getNombreProducto() + " esta inactivo.");
			}

			if (detalle.getCantidad() > producto.getStockActual()) {
				throw new Exception("Stock insuficiente para " + producto.getNombreProducto() + ". Disponible: "
						+ producto.getStockActual() + ", solicitado: " + detalle.getCantidad() + ".");
			}
		}
	}

	@Override
	public void registrarSalidaPorVenta(Venta venta) throws Exception {
		if (venta == null) {
			throw new Exception("La venta no puede ser nula.");
		}

		validarStockDisponible(venta.getDetalles());

		for (DetalleVenta detalle : venta.getDetalles()) {
			String codigoProducto = detalle.getProducto().getCodigoProducto();

			registrarSalida(codigoProducto, detalle.getCantidad(), "Salida por venta " + venta.getNumeroFactura());
		}
	}

	@Override
	public void registrarEntrada(String codigoProducto, int cantidad, String descripcion) throws Exception {
		registrarMovimiento(codigoProducto, cantidad, descripcion, TipoMovimientoInventarioEnum.ENTRADA);
	}

	@Override
	public void registrarSalida(String codigoProducto, int cantidad, String descripcion) throws Exception {
		registrarMovimiento(codigoProducto, cantidad, descripcion, TipoMovimientoInventarioEnum.SALIDA);
	}

	@Override
	public List<MovimientoInventario> obtenerMovimientos() {
		return new ArrayList<>(movimientos);
	}

	private void registrarMovimiento(String codigoProducto, int cantidad, String descripcion,
			TipoMovimientoInventarioEnum tipoMovimiento) throws Exception {

		if (codigoProducto == null || codigoProducto.trim().isEmpty()) {
			throw new Exception("El codigo del producto es obligatorio.");
		}

		if (cantidad <= 0) {
			throw new Exception("La cantidad debe ser mayor que cero.");
		}

		Producto producto = productoDAO.buscarPorCodigo(codigoProducto);

		if (producto == null) {
			throw new Exception("No se encontro el producto.");
		}

		if (tipoMovimiento == TipoMovimientoInventarioEnum.ENTRADA) {
			int nuevoStock = producto.getStockActual() + cantidad;

			if (nuevoStock > producto.getStockMaximo()) {
				throw new Exception("La entrada supera el stock maximo permitido.");
			}

			producto.setStockActual(nuevoStock);

		} else if (tipoMovimiento == TipoMovimientoInventarioEnum.SALIDA) {
			if (cantidad > producto.getStockActual()) {
				throw new Exception("No hay stock suficiente para realizar la salida.");
			}

			producto.setStockActual(producto.getStockActual() - cantidad);
		}

		MovimientoInventario movimiento = new MovimientoInventario(codigoProducto, tipoMovimiento, cantidad,
				LocalDate.now(), descripcion);

		movimientos.add(movimiento);
		productoDAO.actualizarProducto(producto);
		productoDAO.registrarMovimiento(movimiento);
	}

	private void validarDetalle(DetalleVenta detalle) throws Exception {
		if (detalle == null) {
			throw new Exception("El detalle de venta no puede ser nulo.");
		}

		if (detalle.getProducto() == null) {
			throw new Exception("Cada detalle debe tener producto.");
		}

		if (detalle.getProducto().getCodigoProducto() == null
				|| detalle.getProducto().getCodigoProducto().trim().isEmpty()) {
			throw new Exception("Cada producto debe tener codigo.");
		}

		if (detalle.getCantidad() <= 0) {
			throw new Exception("La cantidad debe ser mayor que cero.");
		}
	}

	@Override
	public void registrarEntradaPorAnulacion(Venta venta, String motivo) throws Exception {
		if (venta == null) {
			throw new Exception("La venta no puede ser nula");
		}

		if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
			throw new Exception("La venta no tiene productos para devolver el inventario");
		}

		for (DetalleVenta detalle : venta.getDetalles()) {
			String codigoProducto = detalle.getProducto().getCodigoProducto();

			registrarEntrada(codigoProducto, detalle.getCantidad(),
					"Entrada por anulacion de venta: " + venta.getNumeroFactura() + ".Motivo " + motivo);
		}

	}

}
