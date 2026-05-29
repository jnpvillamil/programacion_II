package co.uptc.edu.co.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.MovimientoInventarioDAO;
import co.uptc.edu.co.modelo.MovimientoInventario;

public class MovimientoInventarioBDDAO implements MovimientoInventarioDAO {

	private static final String SQL_INSERTAR_MOVIMIENTO =
			"INSERT INTO movimientos_inventario (codigoProducto, tipoMovimiento, cantidad, fecha, descripcion) "
					+ "VALUES (?, ?, ?, ?, ?)";

	@Override
	public void registrarMovimiento(MovimientoInventario movimiento) throws Exception {
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_MOVIMIENTO)) {

			prepararInsert(sentencia, movimiento);

		} catch (SQLException e) {
			throw new Exception("Error al registrar movimiento de inventario: " + e.getMessage(), e);
		}
	}

	@Override
	public void registrarMovimiento(Connection conexion, MovimientoInventario movimiento) throws Exception {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_MOVIMIENTO)) {
			prepararInsert(sentencia, movimiento);
		} catch (SQLException e) {
			throw new Exception("Error al registrar movimiento de inventario: " + e.getMessage(), e);
		}
	}

	private void prepararInsert(PreparedStatement sentencia, MovimientoInventario movimiento) throws SQLException {
		sentencia.setString(1, movimiento.getCodigoProducto());
		sentencia.setString(2, movimiento.getTipoMovimiento().name());
		sentencia.setInt(3, movimiento.getCantidad());
		sentencia.setDate(4, java.sql.Date.valueOf(movimiento.getFechaMovimiento()));
		sentencia.setString(5, movimiento.getDescripcion());
		sentencia.executeUpdate();
	}
}
