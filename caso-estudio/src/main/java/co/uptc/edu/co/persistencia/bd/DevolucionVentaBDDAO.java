package co.uptc.edu.co.persistencia.bd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.dao.DevolucionVentaDAO;
import co.uptc.edu.co.modelo.DevolucionVenta;
import co.uptc.edu.co.util.LogUtil;

public class DevolucionVentaBDDAO implements DevolucionVentaDAO {

	private static final String TABLA_DEVOLUCIONES = "devoluciones_venta";

	private static final String SQL_INSERTAR = "INSERT INTO " + TABLA_DEVOLUCIONES
			+ " (codigoDevolucion, numeroFactura, codigoProducto, nombreProducto, cantidadDevuelta, valorDevuelto, fechaHora, motivo)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_BUSCAR_POR_CODIGO = "SELECT codigoDevolucion, numeroFactura, codigoProducto, nombreProducto,"
			+ " cantidadDevuelta, valorDevuelto, fechaHora, motivo FROM " + TABLA_DEVOLUCIONES
			+ " WHERE codigoDevolucion = ?";

	private static final String SQL_BUSCAR_POR_FACTURA = "SELECT codigoDevolucion, numeroFactura, codigoProducto, nombreProducto,"
			+ " cantidadDevuelta, valorDevuelto, fechaHora, motivo FROM " + TABLA_DEVOLUCIONES
			+ " WHERE numeroFactura = ? ORDER BY fechaHora DESC";

	private static final String SQL_LISTAR = "SELECT codigoDevolucion, numeroFactura, codigoProducto, nombreProducto,"
			+ " cantidadDevuelta, valorDevuelto, fechaHora, motivo FROM " + TABLA_DEVOLUCIONES
			+ " ORDER BY fechaHora DESC";
	private static final String SQL_ULTIMO_CODIGO = "SELECT MAX(codigoDevolucion) AS ultimoCodigo " + "FROM "
			+ TABLA_DEVOLUCIONES + " WHERE codigoDevolucion LIKE 'DEV%'";

	private static final String SQL_CANTIDAD_DEVUELTA = "SELECT COALESCE(SUM(cantidadDevuelta), 0) AS cantidadDevuelta "
			+ "FROM " + TABLA_DEVOLUCIONES + " WHERE numeroFactura = ? AND codigoProducto = ?";

	@Override
	public void guardarDevolucion(DevolucionVenta devolucion) throws Exception {
        LogUtil.info("Entrando a guardarDevolucion. codigoDevolucion=" + (devolucion != null ? devolucion.getCodigoDevolucion() : "null"));
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {

			prepararInsert(sentencia, devolucion);
			sentencia.executeUpdate();

		} catch (SQLException e) {
            LogUtil.error("Error en guardarDevolucion: " + e.getMessage(), e);
			throw new Exception("Error al guardar la devolucion de venta: " + e.getMessage(), e);
		}
	}

	@Override
	public void guardarDevolucion(Connection conexion, DevolucionVenta devolucion) throws Exception {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
			prepararInsert(sentencia, devolucion);
			sentencia.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error al guardar la devolucion de venta: " + e.getMessage(), e);
		}
	}

	private void prepararInsert(PreparedStatement sentencia, DevolucionVenta devolucion) throws SQLException {
		sentencia.setString(1, devolucion.getCodigoDevolucion());
		sentencia.setString(2, devolucion.getNumeroFactura());
		sentencia.setString(3, devolucion.getCodigoProducto());
		sentencia.setString(4, devolucion.getNombreProducto());
		sentencia.setInt(5, devolucion.getCantidadDevuelta());
		sentencia.setBigDecimal(6, BigDecimal.valueOf(devolucion.getValorDevuelto()));
		sentencia.setTimestamp(7, Timestamp.valueOf(devolucion.getFechaHora()));
		sentencia.setString(8, devolucion.getMotivo());
	}

	@Override
	public DevolucionVenta buscarPorCodigo(String codigoDevolucion) throws Exception {
        LogUtil.info("Entrando a buscarPorCodigo devolucion. codigoDevolucion=" + codigoDevolucion);
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_POR_CODIGO)) {

			sentencia.setString(1, codigoDevolucion);

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return construirDevolucion(resultado);
				}
			}

			return null;

		} catch (SQLException e) {
            LogUtil.error("Error en buscarPorCodigo devolucion: " + e.getMessage(), e);
			throw new Exception("Error al buscar la devolucion de venta: " + e.getMessage(), e);
		}
	}

	@Override
	public List<DevolucionVenta> buscarPorFactura(String numeroFactura) throws Exception {
        LogUtil.info("Entrando a buscarPorFactura devoluciones. numeroFactura=" + numeroFactura);
		List<DevolucionVenta> devoluciones = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_POR_FACTURA)) {

			sentencia.setString(1, numeroFactura);

			try (ResultSet resultado = sentencia.executeQuery()) {
				while (resultado.next()) {
					devoluciones.add(construirDevolucion(resultado));
				}
			}

			return devoluciones;

		} catch (SQLException e) {
            LogUtil.error("Error en buscarPorFactura devoluciones: " + e.getMessage(), e);
			throw new Exception("Error al listar devoluciones por factura: " + e.getMessage(), e);
		}
	}

	@Override
	public List<DevolucionVenta> listarDevoluciones() throws Exception {
        LogUtil.info("Entrando a listarDevoluciones");
		List<DevolucionVenta> devoluciones = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
				ResultSet resultado = sentencia.executeQuery()) {

			while (resultado.next()) {
				devoluciones.add(construirDevolucion(resultado));
			}

			return devoluciones;

		} catch (SQLException e) {
            LogUtil.error("Error en listarDevoluciones: " + e.getMessage(), e);
			throw new Exception("Error al listar devoluciones de venta: " + e.getMessage(), e);
		}
	}

	private DevolucionVenta construirDevolucion(ResultSet resultado) throws SQLException {
		DevolucionVenta devolucion = new DevolucionVenta();
		devolucion.setCodigoDevolucion(resultado.getString("codigoDevolucion"));
		devolucion.setNumeroFactura(resultado.getString("numeroFactura"));
		devolucion.setCodigoProducto(resultado.getString("codigoProducto"));
		devolucion.setNombreProducto(resultado.getString("nombreProducto"));
		devolucion.setCantidadDevuelta(resultado.getInt("cantidadDevuelta"));
		devolucion.setValorDevuelto(resultado.getDouble("valorDevuelto"));
		devolucion.setFechaHora(resultado.getTimestamp("fechaHora").toLocalDateTime());
		devolucion.setMotivo(resultado.getString("motivo"));
		return devolucion;
	}

	@Override
	public String obtenerUltimoCodigoDevolucion() throws Exception {
        LogUtil.info("Entrando a obtenerUltimoCodigoDevolucion");
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_ULTIMO_CODIGO);
				ResultSet resultado = sentencia.executeQuery()) {

			if (resultado.next()) {
				return resultado.getString("ultimoCodigo");
			}

			return null;

		} catch (SQLException e) {
            LogUtil.error("Error en obtenerUltimoCodigoDevolucion: " + e.getMessage(), e);
			throw new Exception("Error al obtener el ultimo codigo de devolucion: " + e.getMessage(), e);
		}
	}

	@Override
	public int obtenerCantidadDevuelta(String numeroFactura, String codigoProducto) throws Exception {
        LogUtil.info("Entrando a obtenerCantidadDevuelta. factura=" + numeroFactura + " codigoProducto=" + codigoProducto);
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_CANTIDAD_DEVUELTA)) {

			sentencia.setString(1, numeroFactura);
			sentencia.setString(2, codigoProducto);

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return resultado.getInt("cantidadDevuelta");
				}
			}

			return 0;

		} catch (SQLException e) {
            LogUtil.error("Error en obtenerCantidadDevuelta: " + e.getMessage(), e);
			throw new Exception("Error al obtener la cantidad devuelta: " + e.getMessage(), e);
		}
	}
}
