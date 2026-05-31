package co.uptc.edu.co.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.CompraDAO;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.EstadoCompraEnum;
import co.uptc.edu.co.modelo.enums.FormaPago;

public class ComprasBDDAO implements CompraDAO {
	private static final String TABLA_COMPRAS = "compras";
	private static final String TABLA_DETALLE_COMPRAS = "detalle_compras";

	private static final String SQL_INSERTAR_COMPRA = "INSERT INTO " + TABLA_COMPRAS
			+ " (numeroFacturaProveedor, fecha, codigo_proveedor, forma_pago, subtotal, impuestos, totalCompra, estado, motivoAnulacion)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_INSERTAR_DETALLE = "INSERT INTO " + TABLA_DETALLE_COMPRAS
			+ " (id_compra, codigoProducto, cantidad, costoUnitario, subtotal, impuestos, total)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_ACTUALIZAR_COMPRA = "UPDATE " + TABLA_COMPRAS
			+ " SET numeroFacturaProveedor = ?, fecha = ?, codigo_proveedor = ?, forma_pago = ?, subtotal = ?, impuestos = ?, totalCompra = ?, estado = ?, motivoAnulacion = ?"
			+ " WHERE numeroFacturaProveedor = ?";

	private static final String SQL_BUSCAR_COMPRA = "SELECT numeroFacturaProveedor, fecha, codigo_proveedor, forma_pago, subtotal, impuestos, totalCompra, estado, motivoAnulacion"
			+ " FROM " + TABLA_COMPRAS + " WHERE numeroFacturaProveedor = ?";

	private static final String SQL_BUSCAR_ID_COMPRA = "SELECT id_compra FROM " + TABLA_COMPRAS
			+ " WHERE numeroFacturaProveedor = ?";

	private static final String SQL_LISTAR_COMPRAS = "SELECT numeroFacturaProveedor, fecha, codigo_proveedor, forma_pago, subtotal, impuestos, totalCompra, estado, motivoAnulacion"
			+ " FROM " + TABLA_COMPRAS + " ORDER BY fecha DESC";

	private static final String SQL_LISTAR_DETALLES = "SELECT id_detalle, id_compra, codigoProducto, cantidad, costoUnitario, subtotal, impuestos, total"
			+ " FROM " + TABLA_DETALLE_COMPRAS + " WHERE id_compra = ?";

	private static final String SQL_ELIMINAR_DETALLES = "DELETE FROM " + TABLA_DETALLE_COMPRAS + " WHERE id_compra = ?";

	private static final String SQL_ELIMINAR_COMPRA = "DELETE FROM " + TABLA_COMPRAS
			+ " WHERE numeroFacturaProveedor = ?";

	@Override
	public void guardarComprar(Compra compra) throws Exception {
		try (Connection conexion = ConexionBD.getConexion()) {
			conexion.setAutoCommit(false);

			try {
				guardarCabeceraCompra(conexion, compra);
				int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
				guardarDetallesCompra(conexion, compra, idCompra);
				conexion.commit();

			} catch (Exception e) {
				conexion.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new Exception("Error al guardar la compra en el servidor remoto: " + e.getMessage(), e);
		}
	}

	@Override
	public void actualizarCompra(Compra compra) throws Exception {
		try (Connection conexion = ConexionBD.getConexion()) {
			conexion.setAutoCommit(false);

			try {
				int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
				actualizarCabeceraCompra(conexion, compra);
				eliminarDetallescompra(conexion, idCompra);
				guardarDetallesCompra(conexion, compra, idCompra);
				conexion.commit();
			} catch (Exception e) {
				conexion.rollback();
				throw e;
			}
		}

	}

	@Override
	public void eliminarCompra(String numeroFactura) throws Exception {
		try (Connection conexion = ConexionBD.getConexion()) {
			conexion.setAutoCommit(false);

			try {
				int idCompra = obtenerIdCompra(conexion, numeroFactura);
				eliminarDetallescompra(conexion, idCompra);

				try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR_COMPRA)) {
					sentencia.setString(1, numeroFactura);
					sentencia.executeUpdate();
				}

				conexion.commit();
			} catch (Exception e) {
				conexion.rollback();
				throw e;
			}
		}
	}

	@Override
	public Compra buscarComprarpornumero(String numeroFactura) throws Exception {
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentecia = conexion.prepareStatement(SQL_BUSCAR_COMPRA)) {

			sentecia.setString(1, numeroFactura);
			try (ResultSet resultado = sentecia.executeQuery()) {
				if (resultado.next()) {
					Compra compra = construirCompra(resultado);
					int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
					compra.setDetalles(listarDetallesCompra(conexion, idCompra));
					return compra;
				}

			}
			return null;

		} catch (SQLException e) {
			throw new Exception("Error al buscar la compra solicitada: " + e.getMessage(), e);
		}
	}

	private void guardarCabeceraCompra(Connection conexion, Compra compra) throws SQLException {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_COMPRA)) {
			prepararInsertCompra(sentencia, compra);
			sentencia.executeUpdate();
		}
	}

	private void actualizarCabeceraCompra(Connection conexion, Compra compra) throws SQLException {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR_COMPRA)) {
			prepararInsertCompra(sentencia, compra);
			sentencia.setString(10, compra.getNumeroFacturaProveedor());
			sentencia.executeUpdate();
		}
	}

	private void guardarDetallesCompra(Connection conexion, Compra compra, int idCompra) throws SQLException {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_DETALLE)) {
			for (DetalleCompra detalle : compra.getDetalles()) {
				prepararInsertDetalle(sentencia, idCompra, detalle);
				sentencia.addBatch();
			}

			sentencia.executeBatch();
		}
	}

	@Override
	public List<Compra> listarCompra() throws Exception {
		List<Compra> compras = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_COMPRAS);
				ResultSet resultado = sentencia.executeQuery()) {

			while (resultado.next()) {
				Compra compra = construirCompra(resultado);
				int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
				compra.setDetalles(listarDetallesCompra(conexion, idCompra));
				compras.add(compra);
			}

			return compras;

		} catch (SQLException e) {
			throw new Exception("Error al listar las compras: " + e.getMessage(), e);
		}
	}

	private void eliminarDetallescompra(Connection conexion, int idCompra) throws SQLException {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR_DETALLES)) {
			sentencia.setInt(1, idCompra);
			sentencia.executeUpdate();
		}
	}

	private List<DetalleCompra> listarDetallesCompra(Connection conexion, int idCompra) throws SQLException {
		List<DetalleCompra> detalles = new ArrayList<>();

		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_DETALLES)) {
			sentencia.setInt(1, idCompra);

			try (ResultSet resultado = sentencia.executeQuery()) {
				while (resultado.next()) {
					detalles.add(construirDetalleCompra(resultado));
				}
			}
		}

		return detalles;
	}

	private int obtenerIdCompra(Connection conexion, String numeroFacturaProveedor) throws SQLException {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_ID_COMPRA)) {
			sentencia.setString(1, numeroFacturaProveedor);

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return resultado.getInt("id_compra");
				}
			}
		}

		throw new SQLException("No se encontró la compra con número de factura " + numeroFacturaProveedor);
	}

	private Compra construirCompra(ResultSet resultado) throws SQLException {
		Compra compra = new Compra();

		compra.setNumeroFacturaProveedor(resultado.getString("numeroFacturaProveedor"));
		Date fechaSql = resultado.getDate("fecha");
		if (fechaSql != null) {
			compra.setFecha(fechaSql.toLocalDate());
		}
		compra.setCodigoProveedor(resultado.getString("codigo_proveedor"));
		compra.setFormaPago(FormaPago.valueOf(resultado.getString("forma_pago")));
		compra.setSubtotal(resultado.getDouble("subtotal"));
		compra.setImpuestos(resultado.getDouble("impuestos"));
		compra.setTotalCompra(resultado.getDouble("totalCompra"));
		String estadoStr = resultado.getString("estado");
		if (estadoStr != null && !estadoStr.isEmpty()) {
			compra.setEstado(EstadoCompraEnum.valueOf(estadoStr));
		}
		compra.setMotivoAnulacion(resultado.getString("motivoAnulacion"));

		return compra;
	}

	private DetalleCompra construirDetalleCompra(ResultSet resultado) throws SQLException {
		DetalleCompra detalle = new DetalleCompra();
		Producto producto = new Producto();
		producto.setCodigoProducto(resultado.getString("codigoProducto"));
		detalle.setProducto(producto);
		detalle.setCantidad(resultado.getInt("cantidad"));
		detalle.setCostoUnitario(resultado.getDouble("costoUnitario"));
		detalle.setSubtotal(resultado.getDouble("subtotal"));
		detalle.setImpuestos(resultado.getDouble("impuestos"));
		detalle.setTotalCompra(resultado.getDouble("total"));
		return detalle;
	}

	private void prepararInsertDetalle(PreparedStatement sentencia, int idCompra, DetalleCompra detalle)
			throws SQLException {
		sentencia.setInt(1, idCompra);
		sentencia.setString(2, detalle.getProducto() != null ? detalle.getProducto().getCodigoProducto() : null);
		sentencia.setInt(3, detalle.getCantidad());
		sentencia.setDouble(4, detalle.getCostoUnitario());
		sentencia.setDouble(5, detalle.getSubtotal());
		sentencia.setDouble(6, detalle.getImpuestos());
		sentencia.setDouble(7, detalle.getTotalCompra());
	}

	private void prepararInsertCompra(PreparedStatement sentencia, Compra compra) throws SQLException {
		sentencia.setString(1, compra.getNumeroFacturaProveedor());
		sentencia.setDate(2, compra.getFecha() != null ? Date.valueOf(compra.getFecha()) : null);
		sentencia.setString(3, compra.getCodigoProveedor());
		sentencia.setString(4, compra.getFormaPago().name());
		sentencia.setDouble(5, compra.getSubtotal());
		sentencia.setDouble(6, compra.getImpuestos());
		sentencia.setDouble(7, compra.getTotalCompra());
		sentencia.setString(8, compra.getEstado() != null ? compra.getEstado().name() : null);
		sentencia.setString(9, compra.getMotivoAnulacion());
	}
}
