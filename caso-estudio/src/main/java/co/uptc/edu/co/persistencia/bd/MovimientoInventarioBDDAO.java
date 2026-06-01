package co.uptc.edu.co.persistencia.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.dao.MovimientoInventarioDAO;
import co.uptc.edu.co.modelo.MovimientoInventario;
import co.uptc.edu.co.util.LogUtil;

public class MovimientoInventarioBDDAO implements MovimientoInventarioDAO {

	private static final String SQL_INSERTAR_MOVIMIENTO =
			"INSERT INTO movimientos_inventario (codigoProducto, tipoMovimiento, cantidad, fecha, descripcion) "
					+ "VALUES (?, ?, ?, ?, ?)";

	@Override
	public void registrarMovimiento(MovimientoInventario movimiento) throws Exception {
        LogUtil.info("Entrando a registrarMovimiento. codigoProducto=" + (movimiento != null ? movimiento.getCodigoProducto() : "null"));
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_MOVIMIENTO)) {

			prepararInsert(sentencia, movimiento);
			sentencia.executeUpdate();

		} catch (SQLException e) {
            LogUtil.error("Error en registrarMovimiento: " + e.getMessage(), e);
			throw new Exception("Error al registrar movimiento de inventario: " + e.getMessage(), e);
		}
	}

	@Override
	public void registrarMovimiento(Connection conexion, MovimientoInventario movimiento) throws Exception {
        LogUtil.info("Entrando a registrarMovimiento(conn). codigoProducto=" + (movimiento != null ? movimiento.getCodigoProducto() : "null"));
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_MOVIMIENTO)) {
			prepararInsert(sentencia, movimiento);
			sentencia.executeUpdate();
		} catch (SQLException e) {
            LogUtil.error("Error en registrarMovimiento(conn): " + e.getMessage(), e);
			throw new Exception("Error al registrar movimiento de inventario: " + e.getMessage(), e);
		}
	}

	@Override
	public void registrarMovimientos(Connection conexion, List<MovimientoInventario> movimientos) throws Exception {
        LogUtil.info("Entrando a registrarMovimientos. count=" + (movimientos != null ? movimientos.size() : 0));
		if (movimientos == null || movimientos.isEmpty()) {
			return;
		}

		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_MOVIMIENTO)) {
			for (MovimientoInventario movimiento : movimientos) {
				prepararInsert(sentencia, movimiento);
				sentencia.addBatch();
			}

			sentencia.executeBatch();
		} catch (SQLException e) {
            LogUtil.error("Error en registrarMovimientos: " + e.getMessage(), e);
			throw new Exception("Error al registrar movimientos de inventario: " + e.getMessage(), e);
		}
	}

	private void prepararInsert(PreparedStatement sentencia, MovimientoInventario movimiento) throws SQLException {
		sentencia.setString(1, movimiento.getCodigoProducto());
		sentencia.setString(2, movimiento.getTipoMovimiento().name());
		sentencia.setInt(3, movimiento.getCantidad());
		sentencia.setDate(4, java.sql.Date.valueOf(movimiento.getFechaMovimiento()));
		sentencia.setString(5, movimiento.getDescripcion());
	}
}
