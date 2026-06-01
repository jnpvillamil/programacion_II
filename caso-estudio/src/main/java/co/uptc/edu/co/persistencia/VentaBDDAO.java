package co.uptc.edu.co.persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.conexion.TransaccionBD;
import co.uptc.edu.co.interfaces.VentaDAO;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;
import co.uptc.edu.co.modelo.enums.FormaPago;
import co.uptc.edu.co.util.LogUtil;

public class VentaBDDAO implements VentaDAO {

	private static final String TABLA_VENTAS = "ventas";
	private static final String TABLA_DETALLE_VENTAS = "detalle_ventas";

	private static final String SQL_INSERTAR_VENTA = "INSERT INTO " + TABLA_VENTAS
			+ " (numeroFactura, fechaHora, cliente, codigoCliente, formaPago, subtotal, impuestos, total, estado, motivoAnulacion, fechaAnulacion)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_INSERTAR_DETALLE = "INSERT INTO " + TABLA_DETALLE_VENTAS
			+ " (numeroFactura, codigoProducto, cantidad, precioUnitario, subtotal)" + " VALUES (?, ?, ?, ?, ?)";

	private static final String SQL_ACTUALIZAR_VENTA = "UPDATE " + TABLA_VENTAS
			+ " SET fechaHora = ?, cliente = ?, codigoCliente = ?, formaPago = ?, subtotal = ?, impuestos = ?, total = ?, estado = ?, motivoAnulacion = ?, fechaAnulacion = ?"
			+ " WHERE numeroFactura = ?";

	private static final String SQL_BUSCAR_VENTA = "SELECT numeroFactura, fechaHora, cliente, codigoCliente, formaPago, subtotal, impuestos, total, estado, motivoAnulacion, fechaAnulacion"
			+ " FROM " + TABLA_VENTAS + " WHERE numeroFactura = ?";

	private static final String SQL_LISTAR_VENTAS = "SELECT numeroFactura, fechaHora, cliente, codigoCliente, formaPago, subtotal, impuestos, total, estado, motivoAnulacion, fechaAnulacion"
			+ " FROM " + TABLA_VENTAS + " ORDER BY fechaHora DESC";

	private static final String SQL_LISTAR_VENTAS_POR_FECHA = "SELECT numeroFactura, fechaHora, cliente, codigoCliente, formaPago, subtotal, impuestos, total, estado, motivoAnulacion, fechaAnulacion"
			+ " FROM " + TABLA_VENTAS + " WHERE DATE(fechaHora) = ? ORDER BY fechaHora DESC";

	private static final String SQL_LISTAR_DETALLES = "SELECT dv.codigoProducto, p.nombreProducto, p.aplicaIva, dv.cantidad, dv.precioUnitario, dv.subtotal"
			+ " FROM " + TABLA_DETALLE_VENTAS + " dv"
			+ " LEFT JOIN productos p ON dv.codigoProducto = p.codigoProducto" + " WHERE dv.numeroFactura = ?";

	private static final String SQL_ELIMINAR_DETALLES = "DELETE FROM " + TABLA_DETALLE_VENTAS
			+ " WHERE numeroFactura = ?";

	private static final String SQL_ACTUALIZAR_ESTADO_VENTA = "UPDATE " + TABLA_VENTAS
			+ " SET estado = ? WHERE numeroFactura = ?";

	@Override
	public void guardarVenta(Venta venta) throws Exception {
 		LogUtil.info("Entrando a guardarVenta. numeroFactura=" + (venta != null ? venta.getNumeroFactura() : "null"));
 		TransaccionBD.ejecutar(conexion -> guardarVenta(conexion, venta));
	}

	@Override
	public void guardarVenta(Connection conexion, Venta venta) throws Exception {
 		LogUtil.info("Entrando a guardarVenta(conn). numeroFactura=" + (venta != null ? venta.getNumeroFactura() : "null"));
 		guardarCabeceraVenta(conexion, venta);
 		guardarDetallesVenta(conexion, venta);
	}

	@Override
	public void actualizarVenta(Venta venta) throws Exception {
 		LogUtil.info("Entrando a actualizarVenta. numeroFactura=" + (venta != null ? venta.getNumeroFactura() : "null"));
 		TransaccionBD.ejecutar(conexion -> actualizarVenta(conexion, venta));
	}

	@Override
	public void actualizarVenta(Connection conexion, Venta venta) throws Exception {
		actualizarCabeceraVenta(conexion, venta);
		eliminarDetallesVenta(conexion, venta.getNumeroFactura());
		guardarDetallesVenta(conexion, venta);
	}

	@Override
	public Venta buscarVentaPorNumero(String numeroFactura) throws Exception {
        LogUtil.info("Entrando a buscarVentaPorNumero. numeroFactura=" + numeroFactura);
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_VENTA)) {

			sentencia.setString(1, numeroFactura);

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					Venta venta = construirVenta(resultado);
					venta.setDetalles(listarDetallesVenta(conexion, venta.getNumeroFactura()));
					return venta;
				}
			}

			return null;

		} catch (SQLException e) {
			LogUtil.error("Error en buscarVentaPorNumero: " + e.getMessage(), e);
			throw new Exception("Error al buscar la venta solicitada: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Venta> listarVentas() throws Exception {
        LogUtil.info("Entrando a listarVentas");
		List<Venta> ventas = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_VENTAS);
				ResultSet resultado = sentencia.executeQuery()) {

			while (resultado.next()) {
				try {
					Venta venta = construirVenta(resultado);
					venta.setDetalles(listarDetallesVenta(conexion, venta.getNumeroFactura()));
					ventas.add(venta);
				} catch (SQLException e) {
					System.out.println("Se omitió una venta con datos inválidos: " + e.getMessage());
				}
			}

			return ventas;

		} catch (SQLException e) {
			LogUtil.error("Error en listarVentas: " + e.getMessage(), e);
			throw new Exception("Error al listar las ventas: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Venta> listarVentasPorFecha(LocalDate fecha) throws Exception {
        LogUtil.info("Entrando a listarVentasPorFecha fecha=" + fecha);
		List<Venta> ventas = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_VENTAS_POR_FECHA)) {

			sentencia.setDate(1, java.sql.Date.valueOf(fecha));

			try (ResultSet resultado = sentencia.executeQuery()) {
				while (resultado.next()) {
					try {
						Venta venta = construirVenta(resultado);
						venta.setDetalles(listarDetallesVenta(conexion, venta.getNumeroFactura()));
						ventas.add(venta);
					} catch (SQLException e) {
						System.out.println("Se omitió una venta por fecha con datos inválidos: " + e.getMessage());
					}
				}
			}

			return ventas;

		} catch (SQLException e) {
			LogUtil.error("Error en listarVentasPorFecha: " + e.getMessage(), e);
			throw new Exception("Error al listar ventas por fecha: " + e.getMessage(), e);
		}
	}

	private void guardarCabeceraVenta(Connection conexion, Venta venta) throws SQLException {
        LogUtil.info("Entrando a guardarCabeceraVenta. numeroFactura=" + (venta != null ? venta.getNumeroFactura() : "null"));
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_VENTA)) {
			prepararInsertVenta(sentencia, venta);
			sentencia.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLException("Ya existe una venta con ese numero de factura.", e);
		}
	}

	private void actualizarCabeceraVenta(Connection conexion, Venta venta) throws SQLException {
        LogUtil.info("Entrando a actualizarCabeceraVenta. numeroFactura=" + (venta != null ? venta.getNumeroFactura() : "null"));
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR_VENTA)) {
			prepararUpdateVenta(sentencia, venta);
			int filasActualizadas = sentencia.executeUpdate();

			if (filasActualizadas == 0) {
				throw new SQLException("No se encontro la venta a actualizar.");
			}
		}
	}

	private void guardarDetallesVenta(Connection conexion, Venta venta) throws SQLException {
        LogUtil.info("Entrando a guardarDetallesVenta. numeroFactura=" + (venta != null ? venta.getNumeroFactura() : "null"));
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_DETALLE)) {
			for (DetalleVenta detalle : venta.getDetalles()) {
				prepararInsertDetalle(sentencia, venta.getNumeroFactura(), detalle);
				sentencia.addBatch();
			}

			sentencia.executeBatch();
		}
	}

	private void eliminarDetallesVenta(Connection conexion, String numeroFactura) throws SQLException {
        LogUtil.info("Entrando a eliminarDetallesVenta. numeroFactura=" + numeroFactura);
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR_DETALLES)) {
			sentencia.setString(1, numeroFactura);
			sentencia.executeUpdate();
		}
	}

	private List<DetalleVenta> listarDetallesVenta(Connection conexion, String numeroFactura) throws SQLException {
        LogUtil.info("Entrando a listarDetallesVenta. numeroFactura=" + numeroFactura);
		List<DetalleVenta> detalles = new ArrayList<>();

		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_DETALLES)) {
			sentencia.setString(1, numeroFactura);

			try (ResultSet resultado = sentencia.executeQuery()) {
				while (resultado.next()) {
					detalles.add(construirDetalleVenta(resultado));
				}
			}
		}

		return detalles;
	}

	private Venta construirVenta(ResultSet resultado) throws SQLException {
		Venta venta = new Venta();

		venta.setNumeroFactura(resultado.getString("numeroFactura"));

		Timestamp fechaHora = resultado.getTimestamp("fechaHora");
		if (fechaHora != null) {
			venta.setFechaHora(fechaHora.toLocalDateTime());
		}

		venta.setCliente(resultado.getString("cliente"));
		venta.setCodigoCliente(resultado.getString("codigoCliente"));
		venta.setFormaPago(parseFormaPago(resultado.getString("formaPago")));
		venta.setSubTotal(resultado.getDouble("subtotal"));
		venta.setImpuestos(resultado.getDouble("impuestos"));
		venta.setTotal(resultado.getDouble("total"));
		venta.setEstado(EstadoVentaEnum.valueOf(resultado.getString("estado")));
		venta.setMotivoAnulacion(resultado.getString("motivoAnulacion"));

		Timestamp fechaAnulacion = resultado.getTimestamp("fechaAnulacion");
		if (fechaAnulacion != null) {
			venta.setFechaAnulacion(fechaAnulacion.toLocalDateTime());
		}

		return venta;
	}

	private FormaPago parseFormaPago(String valor) throws SQLException {
		if (valor == null || valor.isBlank()) {
			throw new SQLException("La forma de pago viene vacía en la base de datos.");
		}

		String normalizado = valor.trim();
		for (FormaPago formaPago : FormaPago.values()) {
			if (formaPago.name().equalsIgnoreCase(normalizado) || formaPago.toString().equalsIgnoreCase(normalizado)) {
				return formaPago;
			}
		}

		throw new SQLException("Forma de pago no reconocida: " + valor);
	}

	private DetalleVenta construirDetalleVenta(ResultSet resultado) throws SQLException {
		Producto producto = new Producto();
		producto.setCodigoProducto(resultado.getString("codigoProducto"));
		producto.setNombreProducto(resultado.getString("nombreProducto"));
		producto.setAplicaIva(resultado.getBoolean("aplicaIva"));

		DetalleVenta detalle = new DetalleVenta();
		detalle.setProducto(producto);
		detalle.setCantidad(resultado.getInt("cantidad"));
		detalle.setPrecioUnitario(resultado.getDouble("precioUnitario"));
		detalle.setSubtotal(resultado.getDouble("subtotal"));
		return detalle;
	}

	private void prepararInsertVenta(PreparedStatement sentencia, Venta venta) throws SQLException {
		sentencia.setString(1, venta.getNumeroFactura());
		sentencia.setTimestamp(2, Timestamp.valueOf(venta.getFechaHora()));
		sentencia.setString(3, venta.getCliente());
		sentencia.setString(4, venta.getCodigoCliente());
		sentencia.setString(5, venta.getFormaPago().name());
		sentencia.setBigDecimal(6, BigDecimal.valueOf(venta.getSubTotal()));
		sentencia.setBigDecimal(7, BigDecimal.valueOf(venta.getImpuestos()));
		sentencia.setBigDecimal(8, BigDecimal.valueOf(venta.getTotal()));
		sentencia.setString(9, venta.getEstado().name());
		sentencia.setString(10, venta.getMotivoAnulacion());
		sentencia.setTimestamp(11,
				venta.getFechaAnulacion() != null ? Timestamp.valueOf(venta.getFechaAnulacion()) : null);
	}

	private void prepararUpdateVenta(PreparedStatement sentencia, Venta venta) throws SQLException {
		sentencia.setTimestamp(1, Timestamp.valueOf(venta.getFechaHora()));
		sentencia.setString(2, venta.getCliente());
		sentencia.setString(3, venta.getCodigoCliente());
		sentencia.setString(4, venta.getFormaPago().name());
		sentencia.setBigDecimal(5, BigDecimal.valueOf(venta.getSubTotal()));
		sentencia.setBigDecimal(6, BigDecimal.valueOf(venta.getImpuestos()));
		sentencia.setBigDecimal(7, BigDecimal.valueOf(venta.getTotal()));
		sentencia.setString(8, venta.getEstado().name());
		sentencia.setString(9, venta.getMotivoAnulacion());
		sentencia.setTimestamp(10,
				venta.getFechaAnulacion() != null ? Timestamp.valueOf(venta.getFechaAnulacion()) : null);
		sentencia.setString(11, venta.getNumeroFactura());
	}

	private void prepararInsertDetalle(PreparedStatement sentencia, String numeroFactura, DetalleVenta detalle)
			throws SQLException {

		sentencia.setString(1, numeroFactura);
		sentencia.setString(2, detalle.getProducto().getCodigoProducto());
		sentencia.setInt(3, detalle.getCantidad());
		sentencia.setBigDecimal(4, BigDecimal.valueOf(detalle.getPrecioUnitario()));
		sentencia.setBigDecimal(5, BigDecimal.valueOf(detalle.getSubtotal()));
	}

	@Override
	public void actualizarEstadoVenta(Connection conexion, String numeroFactura, EstadoVentaEnum estado)
			throws Exception {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR_ESTADO_VENTA)) {
			sentencia.setString(1, estado.name());
			sentencia.setString(2, numeroFactura);

			int filasActualizadas = sentencia.executeUpdate();

			if (filasActualizadas == 0) {
				throw new SQLException("No se encontro la venta para actualizar el estado.");
			}

		} catch (SQLException e) {
			throw new Exception("Error al actualizar el estado de la venta: " + e.getMessage(), e);
		}
	}

}
