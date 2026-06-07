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
	private static final String TABLA_EMPLEADOS = "empleados";
	private static final String SQL_INSERTAR_EMPLEADO = "INSERT INTO " + TABLA_EMPLEADOS
			+ " (cargoEmpleado, salarioEmpleado) VALUES (?, ?)";
	private static final String SQL_LISTAR_EMPLEADOS = "SELECT cargoEmpleado, salarioEmpleado FROM " + TABLA_EMPLEADOS;
	private static final String SQL_ACTUALIZAR_EMPLEADO = "UPDATE " + TABLA_EMPLEADOS
			+ " SET salarioEmpleado = ? WHERE cargoEmpleado = ?";

	@Override
	public boolean guardarCargo(Empleado empleado) throws Exception {
		guardar(empleado);
		return true;
	}

	@Override
	public void guardar(Empleado empleado) throws Exception {
		asegurarTablaEmpleados();

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR_EMPLEADO)) {

			preparedStatement.setString(1, empleado.getCargoEmpleado());
			preparedStatement.setBigDecimal(2, BigDecimal.valueOf(empleado.getSalarioEmpleado()));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("Error al guardar empleado: " + e.getMessage(), e);
		}
	}

	@Override
	public void actualizar(Empleado empleado) throws Exception {
		if (empleado == null || empleado.getCargoEmpleado() == null || empleado.getCargoEmpleado().trim().isEmpty()) {
			throw new Exception("Empleado inválido para actualizar.");
		}

		asegurarTablaEmpleados();

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_ACTUALIZAR_EMPLEADO)) {

			preparedStatement.setBigDecimal(1, BigDecimal.valueOf(empleado.getSalarioEmpleado()));
			preparedStatement.setString(2, empleado.getCargoEmpleado());

			int filasAfectadas = preparedStatement.executeUpdate();
			if (filasAfectadas == 0) {
				throw new Exception("No se encontró el empleado para actualizar.");
			}

		} catch (SQLException e) {
			throw new Exception("Error al actualizar empleado: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Empleado> listar() throws Exception {
		List<Empleado> empleados = new ArrayList<>();
		asegurarTablaEmpleados();

		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_LISTAR_EMPLEADOS);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Empleado empleado = new Empleado();
				empleado.setCargoEmpleado(resultSet.getString("cargoEmpleado"));
				empleado.setSalarioEmpleado(resultSet.getDouble("salarioEmpleado"));
				empleados.add(empleado);
			}

		} catch (SQLException e) {
			throw new Exception("Error al listar empleados: " + e.getMessage(), e);
		}

		return empleados;
	}

	private void asegurarTablaEmpleados() {
		
	}


}
