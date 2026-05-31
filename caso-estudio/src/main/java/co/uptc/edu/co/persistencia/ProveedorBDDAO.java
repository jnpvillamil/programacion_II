package co.uptc.edu.co.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.ProveedorDAO;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class ProveedorBDDAO implements ProveedorDAO {

	private static final String TABLA_PROVEEDORES = "proveedores";

	private static final String SQL_INSERTAR = "INSERT INTO " + TABLA_PROVEEDORES
			+ " (codigoProveedor, razonSocial, nit, direccion, telefono, correoElectronico, estado)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_BUSCAR_POR_CODIGO = "SELECT codigoProveedor, razonSocial, nit, direccion, telefono, correoElectronico, estado "
			+ "FROM " + TABLA_PROVEEDORES + " WHERE codigoProveedor = ?";

	private static final String SQL_LISTAR = "SELECT codigoProveedor, razonSocial, nit, direccion, telefono, correoElectronico, estado "
			+ "FROM " + TABLA_PROVEEDORES;

	private static final String SQL_ACTUALIZAR = "UPDATE " + TABLA_PROVEEDORES
			+ " SET razonSocial = ?, nit = ?, direccion = ?, telefono = ?, correoElectronico = ?, estado = ?"
			+ " WHERE codigoProveedor = ?";

	@Override
	public void guardarProveedor(Proveedor proveedor) throws Exception {
		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {

			prepararInsert(preparedStatement, proveedor);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("Error al guardar el proveedor en el servidor remoto: " + e.getMessage(), e);
		}
	}

	@Override
	public void actualizarProveedor(Proveedor proveedor) throws Exception {
		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_ACTUALIZAR)) {

			prepararActualizar(preparedStatement, proveedor);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error al actualizar el proveedor en el servidor remoto: " + e.getMessage(), e);
		}
	}

	@Override
	public Proveedor buscarProveedorPorCodigo(String codigo) throws Exception {
		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUSCAR_POR_CODIGO)) {

			preparedStatement.setString(1, codigo);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return construirProveedor(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new Exception("Error al buscar el proveedor solicitado: " + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<Proveedor> listarProveedor() throws Exception {
		List<Proveedor> lista = new ArrayList<>();
		try (Connection connection = ConexionBD.getConexion();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_LISTAR);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				lista.add(construirProveedor(resultSet));
			}
		} catch (SQLException e) {
			throw new Exception("Error al listar los proveedores: " + e.getMessage(), e);
		}
		return lista;
	}

	private void prepararInsert(PreparedStatement preparedStatement, Proveedor proveedor) throws SQLException {
		preparedStatement.setString(1, proveedor.getCodigoProveedor());
		preparedStatement.setString(2, proveedor.getRazonSocial());
		preparedStatement.setString(3, proveedor.getNit());
		preparedStatement.setString(4, proveedor.getDireccion());
		preparedStatement.setString(5, proveedor.getTelefono());
		preparedStatement.setString(6, proveedor.getCorreoElectronico());
		preparedStatement.setString(7, proveedor.getEstado() != null ? proveedor.getEstado().name() : null);
	}

	private void prepararActualizar(PreparedStatement preparedStatement, Proveedor proveedor) throws SQLException {
		preparedStatement.setString(1, proveedor.getRazonSocial());
		preparedStatement.setString(2, proveedor.getNit());
		preparedStatement.setString(3, proveedor.getDireccion());
		preparedStatement.setString(4, proveedor.getTelefono());
		preparedStatement.setString(5, proveedor.getCorreoElectronico());
		preparedStatement.setString(6, proveedor.getEstado() != null ? proveedor.getEstado().name() : null);
		preparedStatement.setString(7, proveedor.getCodigoProveedor());
	}

	private Proveedor construirProveedor(ResultSet resultSet) throws SQLException {
		Proveedor proveedor = new Proveedor();
		proveedor.setCodigoProveedor(resultSet.getString("codigoProveedor"));
		proveedor.setRazonSocial(resultSet.getString("razonSocial"));
		proveedor.setNit(resultSet.getString("nit"));
		proveedor.setDireccion(resultSet.getString("direccion"));
		proveedor.setTelefono(resultSet.getString("telefono"));
		proveedor.setCorreoElectronico(resultSet.getString("correoElectronico"));

		String estado = resultSet.getString("estado");
		if (estado != null && !estado.isBlank()) {
			proveedor.setEstado(EstadoEnum.valueOf(estado.trim().toUpperCase()));
		}

		return proveedor;
	}
}
