package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionMovimientoContable;
import co.edu.uptc.negocio.dto.movimientoContableDto;

public class LocalMovimientoContable implements IGestionMovimientoContable {

	@Override
	public void guardar(movimientoContableDto mov) {
		// Nombres exactos de la tabla 'movimiento_contable'
		String sql = "INSERT INTO movimiento_contable (fecha, tipo_movimiento, cuenta_afectada, valor, descripcion) VALUES (?, ?, ?, ?, ?)";
		Conexion conexObj = new Conexion();

		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {

			ps.setString(1, mov.getFecha());
			ps.setString(2, mov.getTipoMovimiento());
			ps.setString(3, mov.getCuentaContable()); // Asegúrate de que tu DTO usa getCuentaContable() o cámbialo a
														// getCuentaAfectada()
			ps.setDouble(4, mov.getValor());
			ps.setString(5, mov.getDescripcion());

			ps.executeUpdate();

		} catch (Exception e) {
			System.err.println("Error al guardar movimiento contable en BD: " + e.getMessage());
		}
	}

	public void guardar(movimientoContableDto mov, Connection conex) throws Exception {
		String sql = "INSERT INTO movimiento_contable (fecha, tipo_movimiento, cuenta_afectada, valor, descripcion) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setString(1, mov.getFecha());
			ps.setString(2, mov.getTipoMovimiento());
			ps.setString(3, mov.getCuentaContable());
			ps.setDouble(4, mov.getValor());
			ps.setString(5, mov.getDescripcion());
			ps.executeUpdate();
		}
	}

	// --- Métodos obligatorios de la interfaz (puedes implementarlos luego si los
	// necesitas) ---
	@Override
	public List<movimientoContableDto> listar() {
		return new ArrayList<>();
	}

	@Override
	public movimientoContableDto buscar(int codigoTransaccion) {
		// TODO Auto-generated method stub
		return null;
	}
}