package co.uptc.edu.co.negocio;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uptc.edu.co.conexion.TransaccionBD;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.MovimientoInventario;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.TipoMovimientoInventarioEnum;

public class GestionInventario {

	private final IGestionProducto gestionProducto;
	private final IGestionInventario gestionMovimientoInventario;
	private List<MovimientoInventario> movimientos;

	public GestionInventario(IGestionProducto gestionProducto, IGestionInventario gestionMovimientoInventario) {
		if (gestionProducto == null) {
			throw new IllegalArgumentException("La gestionProducto no puede ser nula.");
		}
		if (gestionMovimientoInventario == null) {
			throw new IllegalArgumentException("La gestionMovimientoInventario no puede ser nula.");
		}

		this.gestionProducto = gestionProducto;
		this.gestionMovimientoInventario = gestionMovimientoInventario;
		this.movimientos = new ArrayList<>();
	}

	public void validarStockDisponible(List<DetalleVenta> detalles) throws Exception {
		TransaccionBD.ejecutar(conexion -> validarStockDisponible(conexion, detalles));
	}

	public void registrarSalidaPorVenta(Venta venta) throws Exception {
		TransaccionBD.ejecutar(conexion -> registrarSalidaPorVenta(conexion, venta));
	}

	public void registrarSalidaPorVenta(Connection conexion, Venta venta) throws Exception {
		if (venta == null) {
			throw new Exception("La venta no puede ser nula.");
		}

		Map<String, Integer> cantidadesPorProducto = agruparCantidadesPorProducto(venta.getDetalles());
		List<MovimientoInventario> movimientosAGuardar = new ArrayList<>();

		for (Map.Entry<String, Integer> entrada : cantidadesPorProducto.entrySet()) {
			String codigoProducto = entrada.getKey();
			int cantidad = entrada.getValue();

			Producto producto = gestionProducto.buscar(codigoProducto);

			if (producto == null) {
				throw new Exception("No existe el producto: " + codigoProducto + ".");
			}

			if (!producto.estaActivo()) {
				throw new Exception("El producto esta inactivo: " + codigoProducto + ".");
			}

			if (producto.getStockActual() < cantidad) {
				throw new Exception("No hay stock suficiente para el producto: " + codigoProducto + ".");
			}

			producto.setStockActual(producto.getStockActual() - cantidad);
			gestionProducto.actualizar(conexion, producto);

			MovimientoInventario movimiento = new MovimientoInventario(
					codigoProducto,
					TipoMovimientoInventarioEnum.SALIDA,
					cantidad,
					LocalDate.now(),
					"Salida por venta " + venta.getNumeroFactura());

			movimientosAGuardar.add(movimiento);
		}

		gestionMovimientoInventario.guardar(conexion, movimientosAGuardar);
		movimientos.addAll(movimientosAGuardar);
	}

	public void registrarEntrada(String codigoProducto, int cantidad, String descripcion) throws Exception {
		TransaccionBD.ejecutar(conexion -> registrarMovimiento(conexion, codigoProducto, cantidad, descripcion,
				TipoMovimientoInventarioEnum.ENTRADA));
	}

	public void registrarSalida(String codigoProducto, int cantidad, String descripcion) throws Exception {
		TransaccionBD.ejecutar(conexion -> registrarMovimiento(conexion, codigoProducto, cantidad, descripcion,
				TipoMovimientoInventarioEnum.SALIDA));
	}

	public void registrarEntrada(Connection conexion, String codigoProducto, int cantidad, String descripcion)
			throws Exception {
		registrarMovimiento(conexion, codigoProducto, cantidad, descripcion, TipoMovimientoInventarioEnum.ENTRADA);
	}

	public void registrarSalida(Connection conexion, String codigoProducto, int cantidad, String descripcion)
			throws Exception {
		registrarMovimiento(conexion, codigoProducto, cantidad, descripcion, TipoMovimientoInventarioEnum.SALIDA);
	}

	public List<MovimientoInventario> obtenerMovimientos() {
		return new ArrayList<>(movimientos);
	}

	private void registrarMovimiento(Connection conexion, String codigoProducto, int cantidad, String descripcion,
			TipoMovimientoInventarioEnum tipoMovimiento) throws Exception {

		if (codigoProducto == null || codigoProducto.trim().isEmpty()) {
			throw new Exception("El codigo del producto es obligatorio.");
		}

		if (cantidad <= 0) {
			throw new Exception("La cantidad debe ser mayor que cero.");
		}

		Producto producto = gestionProducto.buscar(codigoProducto);

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

		MovimientoInventario movimiento = new MovimientoInventario(
				codigoProducto,
				tipoMovimiento,
				cantidad,
				LocalDate.now(),
				descripcion);

		gestionProducto.actualizar(conexion, producto);
		gestionMovimientoInventario.guardar(conexion, movimiento);
		movimientos.add(movimiento);
	}

	public void validarStockDisponible(Connection conexion, List<DetalleVenta> detalles) throws Exception {
		validarStockDisponibleYObtenerProductos(conexion, detalles);
	}

	private Map<String, Producto> validarStockDisponibleYObtenerProductos(Connection conexion,
			List<DetalleVenta> detalles) throws Exception {
		if (detalles == null || detalles.isEmpty()) {
			throw new Exception("Debe existir al menos un producto para validar stock.");
		}

		Map<String, Producto> productosPorCodigo = new HashMap<>();

		for (DetalleVenta detalle : detalles) {
			validarDetalle(detalle);

			String codigoProducto = detalle.getProducto().getCodigoProducto();
			Producto producto = productosPorCodigo.get(codigoProducto);

			if (producto == null) {
				producto = gestionProducto.buscar(codigoProducto);
				productosPorCodigo.put(codigoProducto, producto);
			}

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

		return productosPorCodigo;
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

	private Map<String, Integer> agruparCantidadesPorProducto(List<DetalleVenta> detalles) throws Exception {
		if (detalles == null || detalles.isEmpty()) {
			throw new Exception("Debe existir al menos un producto para registrar salida de inventario.");
		}

		Map<String, Integer> cantidadesPorProducto = new HashMap<>();

		for (DetalleVenta detalle : detalles) {
			validarDetalle(detalle);
			String codigoProducto = detalle.getProducto().getCodigoProducto();
			cantidadesPorProducto.merge(codigoProducto, detalle.getCantidad(), Integer::sum);
		}

		return cantidadesPorProducto;
	}

	public void registrarEntradaPorAnulacion(Venta venta, String motivo) throws Exception {
		TransaccionBD.ejecutar(conexion -> registrarEntradaPorAnulacion(conexion, venta, motivo));
	}

	public void registrarEntradaPorAnulacion(Connection conexion, Venta venta, String motivo) throws Exception {
		if (venta == null) {
			throw new Exception("La venta no puede ser nula.");
		}

		if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
			throw new Exception("La venta no tiene productos para devolver el inventario.");
		}

		for (DetalleVenta detalle : venta.getDetalles()) {
			String codigoProducto = detalle.getProducto().getCodigoProducto();

			registrarEntrada(conexion, codigoProducto, detalle.getCantidad(),
					"Entrada por anulacion de venta: " + venta.getNumeroFactura() + ". Motivo " + motivo);
		}
	}
}