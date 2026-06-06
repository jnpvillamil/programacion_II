
package co.edu.uptc.persistencia.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionEmpleado;
import co.edu.uptc.negocio.dto.empleadoDto;

public class DatabaseEmpleado implements IGestionEmpleado {

	@Override
	public void guardar(empleadoDto empleado) {
		String sql = "INSERT INTO empleado (codigo, nombre) VALUES (?, ?)";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, empleado.getCodigoEmpleado());
			ps.setString(2, empleado.getNombre());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al guardar empleado: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(empleadoDto empleado) {
		String sql = "UPDATE empleado SET nombre = ? WHERE codigo = ?";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setString(1, empleado.getNombre());
			ps.setInt(2, empleado.getCodigoEmpleado());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al actualizar empleado: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(int codigoEmpleado) {
		String sql = "DELETE FROM empleado WHERE codigo = ?";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, codigoEmpleado);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al eliminar empleado: " + e.getMessage());
		}
	}

	@Override
	public empleadoDto buscar(int codigoEmpleado) {
		String sql = "SELECT * FROM empleado WHERE codigo = ?";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, codigoEmpleado);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return mapRow(rs);
		} catch (SQLException e) {
			System.err.println("Error al buscar empleado: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<empleadoDto> listar() {
		List<empleadoDto> lista = new ArrayList<>();
		String sql = "SELECT * FROM empleado";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection();
				PreparedStatement ps = conex.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				lista.add(mapRow(rs));
		} catch (SQLException e) {
			System.err.println("Error al listar empleados: " + e.getMessage());
		}
		return lista;
	}

	private empleadoDto mapRow(ResultSet rs) throws SQLException {
		empleadoDto e = new empleadoDto();
		e.setCodigoEmpleado(rs.getInt("codigo"));
		e.setNombre(rs.getString("nombre"));
		return e;
	}
}