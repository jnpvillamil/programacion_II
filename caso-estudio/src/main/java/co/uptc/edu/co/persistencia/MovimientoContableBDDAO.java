package co.uptc.edu.co.persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.MovimientoContableDAO;
import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.enums.TipoMovimientoContable;

public class MovimientoContableBDDAO implements MovimientoContableDAO {

	private static final String TABLA_MOVIMIENTOS = "movimientos_contables";

	private static final String SQL_INSERTAR = "INSERT INTO " + TABLA_MOVIMIENTOS
			+ " (codigoTransaccion, fecha, tipoMovimiento, cuentaContable, valor, descripcion, origen, referencia)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_BUSCAR_POR_CODIGO = "SELECT codigoTransaccion, fecha, tipoMovimiento, "
			+ "cuentaContable, valor, descripcion, origen, referencia FROM " + TABLA_MOVIMIENTOS
			+ " WHERE codigoTransaccion = ?";

	private static final String SQL_LISTAR = "SELECT codigoTransaccion, fecha, tipoMovimiento, cuentaContable, "
			+ "valor, descripcion, origen, referencia FROM " + TABLA_MOVIMIENTOS + " ORDER BY fecha DESC";

	@Override
	public void guardarMovimiento(MovimientoContable movimiento) throws Exception {
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {

			prepararInsert(sentencia, movimiento);
			sentencia.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("Error al guardar el movimiento contable: " + e.getMessage(), e);
		}
	}

	@Override
	public void guardarMovimiento(Connection conexion, MovimientoContable movimiento) throws Exception {
		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
			prepararInsert(sentencia, movimiento);
			sentencia.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error al guardar el movimiento contable: " + e.getMessage(), e);
		}
	}

	@Override
	public void guardarMovimientos(Connection conexion, List<MovimientoContable> movimientos) throws Exception {
		if (movimientos == null || movimientos.isEmpty()) {
			return;
		}

		try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
			for (MovimientoContable movimiento : movimientos) {
				prepararInsert(sentencia, movimiento);
				sentencia.addBatch();
			}

			sentencia.executeBatch();
		} catch (SQLException e) {
			throw new Exception("Error al guardar los movimientos contables: " + e.getMessage(), e);
		}
	}

	@Override
	public MovimientoContable buscarPorCodigo(String codigoTransaccion) throws Exception {
		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_POR_CODIGO)) {

			sentencia.setString(1, codigoTransaccion);

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return construirMovimiento(resultado);
				}
			}

			return null;

		} catch (SQLException e) {
			throw new Exception("Error al buscar el movimiento contable: " + e.getMessage(), e);
		}
	}

	@Override
	public List<MovimientoContable> listarMovimientos() throws Exception {
		List<MovimientoContable> movimientos = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
				ResultSet resultado = sentencia.executeQuery()) {

			while (resultado.next()) {
				movimientos.add(construirMovimiento(resultado));
			}

			return movimientos;

		} catch (SQLException e) {
			throw new Exception("Error al listar los movimientos contables: " + e.getMessage(), e);
		}
	}

	private void prepararInsert(PreparedStatement sentencia, MovimientoContable movimiento) throws SQLException {
		sentencia.setString(1, movimiento.getCodigoTransaccion());
		sentencia.setDate(2, movimiento.getFecha() != null ? Date.valueOf(movimiento.getFecha()) : null);
		sentencia.setString(3,
				movimiento.getTipoMovimientoContable() != null ? movimiento.getTipoMovimientoContable().name() : null);
		sentencia.setString(4, movimiento.getCuentaContable());
		sentencia.setBigDecimal(5, BigDecimal.valueOf(movimiento.getValor()));
		sentencia.setString(6, movimiento.getDescripcion());
		sentencia.setString(7, movimiento.getOrigen());
		sentencia.setString(8, movimiento.getReferencia());
	}

	private MovimientoContable construirMovimiento(ResultSet resultado) throws SQLException {
		MovimientoContable movimiento = new MovimientoContable();

		movimiento.setCodigoTransaccion(resultado.getString("codigoTransaccion"));

		Date fecha = resultado.getDate("fecha");
		if (fecha != null) {
			movimiento.setFecha(fecha.toLocalDate());
		}

		String tipoMovimiento = resultado.getString("tipoMovimiento");
		if (tipoMovimiento != null && !tipoMovimiento.isBlank()) {
			movimiento.setTipoMovimientoContable(TipoMovimientoContable.valueOf(tipoMovimiento));
		}

		movimiento.setCuentaContable(resultado.getString("cuentaContable"));
		movimiento.setValor(resultado.getDouble("valor"));
		movimiento.setDescripcion(resultado.getString("descripcion"));
		movimiento.setOrigen(resultado.getString("origen"));
		movimiento.setReferencia(resultado.getString("referencia"));

		return movimiento;
	}
}
