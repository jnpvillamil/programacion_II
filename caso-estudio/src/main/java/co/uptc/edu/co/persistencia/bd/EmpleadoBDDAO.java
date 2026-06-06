package co.uptc.edu.co.persistencia.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.IGestionEmpleado;
import co.uptc.edu.co.modelo.Empleado;

public class EmpleadoBDDAO implements IGestionEmpleado {
	private static final String TABLA = "Empleado";
	private static final String SQL_INSERTAR ="INSERT INTO " + TABLA + "Cargo "
			+ "VALUES (?) ";
			
	@Override
	public boolean guardarCargo(Empleado empleado) throws Exception {
		
		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {

			prepararInsert(preparedStatement, empleado);
			int filasAfectadas = preparedStatement.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException e) {
			
			throw new Exception("Error al guardar Empleado: " + e.getMessage(), e);
		}
	
	}

	@Override
	public void guardar(Empleado empleado) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar(Empleado empleado) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Empleado> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private void prepararInsert(PreparedStatement preparedStatement, Empleado empleado) throws SQLException {
		// TODO Auto-generated method stub
	preparedStatement.setString(1, empleado.getCargoEmpleado());
	}

	
}
