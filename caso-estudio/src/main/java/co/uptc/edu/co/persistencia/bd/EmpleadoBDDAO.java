package co.uptc.edu.co.persistencia.bd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.IGestionEmpleado;
import co.uptc.edu.co.modelo.Empleado;

public class EmpleadoBDDAO implements IGestionEmpleado {
	private static final String TABLA = "Empleado";
	private static final String SQL_INSERTAR ="INSERT INTO " + TABLA + "Cargo "
			+ "VALUES (?) ";
	private static final String TABLA_EMPLEADOS = "empleados";
	private static final String SQL_CREAR_TABLA_SALARIOS = "CREATE TABLE IF NOT EXISTS " + TABLA_EMPLEADOS
			+ " (salarioEmpleado DECIMAL(12,2) NOT NULL DEFAULT 0)";
	private static final String SQL_INSERTAR_SALARIO = "INSERT INTO " + TABLA_EMPLEADOS
			+ " (salarioEmpleado) VALUES (?)";
	private static final String SQL_LISTAR_SALARIOS = "SELECT salarioEmpleado FROM " + TABLA_EMPLEADOS;
			
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
		asegurarTablaSalarios();

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR_SALARIO)) {

			preparedStatement.setBigDecimal(1, BigDecimal.valueOf(empleado.getSalarioEmpleado()));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("Error al guardar salario del empleado: " + e.getMessage(), e);
		}
		
	}

	@Override
	public void actualizar(Empleado empleado) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Empleado> listar() throws Exception {
		List<Empleado> empleados = new ArrayList<>();
		asegurarTablaSalarios();

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_LISTAR_SALARIOS);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Empleado empleado = new Empleado();
				empleado.setSalarioEmpleado(resultSet.getDouble("salarioEmpleado"));
				empleados.add(empleado);
			}

		} catch (SQLException e) {
			throw new Exception("Error al listar salarios de empleados: " + e.getMessage(), e);
		}

		return empleados;
	}

	private void asegurarTablaSalarios() throws Exception {
		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREAR_TABLA_SALARIOS)) {

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("Error al preparar tabla de salarios de empleados: " + e.getMessage(), e);
		}
	}

	private void prepararInsert(PreparedStatement preparedStatement, Empleado empleado) throws SQLException {
		// TODO Auto-generated method stub
	preparedStatement.setString(1, empleado.getCargoEmpleado());
	}

	
}
