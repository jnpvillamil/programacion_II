package co.uptc.edu.co.persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.DevolucionVentaDAO;
import co.uptc.edu.co.modelo.DevolucionVenta;

public class DevolucionVentaBDDAO implements DevolucionVentaDAO {

	private static final String TABLA_DEVOLUCIONES = "devoluciones_venta";

	private static final String SQL_INSERTAR = "INSERT INTO " + TABLA_DEVOLUCIONES
			+ " (codigo_devolucion, numero_factura, codigo_producto, nombre_producto, cantidad_devuelta, valor_devuelto, fecha_hora, motivo)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_BUSCAR_POR_CODIGO = "SELECT codigo_devolucion, numero_factura, codigo_producto, nombre_producto,"
			+ " cantidad_devuelta, valor_devuelto, fecha_hora, motivo FROM " + TABLA_DEVOLUCIONES
			+ " WHERE codigo_devolucion = ?";

	private static final String SQL_BUSCAR_POR_FACTURA = "SELECT codigo_devolucion, numero_factura, codigo_producto, nombre_producto,"
			+ " cantidad_devuelta, valor_devuelto, fecha_hora, motivo FROM " + TABLA_DEVOLUCIONES
			+ " WHERE numero_factura = ? ORDER BY fecha_hora DESC";

	private static final String SQL_LISTAR = "SELECT codigo_devolucion, numero_factura, codigo_producto, nombre_producto,"
			+ " cantidad_devuelta, valor_devuelto, fecha_hora, motivo FROM " + TABLA_DEVOLUCIONES
			+ " ORDER BY fecha_hora DESC";

	@Override
	public void guardarDevolucion(DevolucionVenta devolucion) throws Exception {
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {

			prepararInsert(sentencia, devolucion);
			sentencia.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("Error al guardar la devolucion de venta: " + e.getMessage(), e);
		}
	}

	@Override
	public DevolucionVenta buscarPorCodigo(String codigoDevolucion) throws Exception {
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
			throw new Exception("Error al buscar la devolucion de venta: " + e.getMessage(), e);
		}
	}

	@Override
	public List<DevolucionVenta> buscarPorFactura(String numeroFactura) throws Exception {
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
			throw new Exception("Error al listar devoluciones por factura: " + e.getMessage(), e);
		}
	}

	@Override
	public List<DevolucionVenta> listarDevoluciones() throws Exception {
		List<DevolucionVenta> devoluciones = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
				ResultSet resultado = sentencia.executeQuery()) {

			while (resultado.next()) {
				devoluciones.add(construirDevolucion(resultado));
			}

			return devoluciones;

		} catch (SQLException e) {
			throw new Exception("Error al listar devoluciones de venta: " + e.getMessage(), e);
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

	private DevolucionVenta construirDevolucion(ResultSet resultado) throws SQLException {
		DevolucionVenta devolucion = new DevolucionVenta();
		devolucion.setCodigoDevolucion(resultado.getString("codigo_devolucion"));
		devolucion.setNumeroFactura(resultado.getString("numero_factura"));
		devolucion.setCodigoProducto(resultado.getString("codigo_producto"));
		devolucion.setNombreProducto(resultado.getString("nombre_producto"));
		devolucion.setCantidadDevuelta(resultado.getInt("cantidad_devuelta"));
		devolucion.setValorDevuelto(resultado.getDouble("valor_devuelto"));
		devolucion.setFechaHora(resultado.getTimestamp("fecha_hora").toLocalDateTime());
		devolucion.setMotivo(resultado.getString("motivo"));
		return devolucion;
	}
}
