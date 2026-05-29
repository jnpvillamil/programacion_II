package co.edu.uptc.negocio; // Mantenemos tu paquete original

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.negocio.dto.clienteDto;

public class GestionCliente {

	// 1. REGISTRAR con validación de duplicados
	public void registrar(clienteDto cliente) throws Exception {
		if (cliente == null) {
			throw new Exception("No se tiene información del cliente");
		}

		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();

			// --- NUEVA VALIDACIÓN: Buscar si el documento ya existe ---
			String sqlValidacion = "SELECT COUNT(*) AS total FROM cliente WHERE documento = '" + cliente.getDocumento()
					+ "'";
			ResultSet rsValidacion = estatuto.executeQuery(sqlValidacion);

			if (rsValidacion.next() && rsValidacion.getInt("total") > 0) {
				rsValidacion.close();
				estatuto.close();
				conex.desconectar();
				// Lanzamos el error antes de guardar
				throw new Exception("Operación cancelada: Ya existe un cliente registrado con el documento "
						+ cliente.getDocumento());
			}
			rsValidacion.close();
			// ---------------------------------------------------------

			// Si pasa la validación, insertamos normalmente
			String consulta = "INSERT INTO cliente (documento, nombre, telefono, direccion) VALUES ('"
					+ cliente.getDocumento() + "', '" + cliente.getNombre() + "', " + cliente.getTelefono() + ", '"
					+ cliente.getDireccion() + "')";

			estatuto.executeUpdate(consulta);
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			throw new Exception("Error al registrar cliente en BD: " + e.getMessage());
		}
	}

	// 2. MODIFICAR con tus validaciones originales
	public void modificar(clienteDto cliente) throws Exception {
		if (cliente == null) {
			throw new Exception("No se tiene información del cliente");
		}

		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();

			String consulta = "UPDATE cliente SET " + "documento = '" + cliente.getDocumento() + "', " + "nombre = '"
					+ cliente.getNombre() + "', " + "telefono = " + cliente.getTelefono() + ", " + "direccion = '"
					+ cliente.getDireccion() + "' " + "WHERE codigo = " + cliente.getCodigoCliente();

			estatuto.executeUpdate(consulta);
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			throw new Exception("Error al modificar cliente en BD: " + e.getMessage());
		}
	}

	// 3. INACTIVAR lanzando la excepción hacia la vista
	public void inactivar(int codigo) throws Exception {
		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			String consulta = "DELETE FROM cliente WHERE codigo = " + codigo;

			estatuto.executeUpdate(consulta);
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			throw new Exception("Error al eliminar cliente en BD: " + e.getMessage());
		}
	}

	// 4. EL MÉTODO QUE OMITÍ: Buscar un solo cliente exacto
	public clienteDto buscar(int codigo) throws Exception {
		clienteDto clienteEncontrado = null;
		Conexion conex = new Conexion();

		try {
			Statement estatuto = conex.getConnection().createStatement();
			String consulta = "SELECT * FROM cliente WHERE codigo = " + codigo;
			ResultSet rs = estatuto.executeQuery(consulta);

			if (rs.next()) {
				clienteEncontrado = new clienteDto();
				clienteEncontrado.setCodigoCliente(rs.getInt("codigo"));
				clienteEncontrado.setNombre(rs.getString("nombre"));
				clienteEncontrado.setTelefono(rs.getLong("telefono"));
				clienteEncontrado.setDireccion(rs.getString("direccion"));
			}

			rs.close();
			estatuto.close();
			conex.desconectar();

			if (clienteEncontrado == null) {
				throw new Exception("No se encontró ningún cliente con el código: " + codigo);
			}

		} catch (SQLException e) {
			throw new Exception("Error en la base de datos al buscar: " + e.getMessage());
		}
		return clienteEncontrado;
	}

	// 5. LISTAR TODOS
	public List<clienteDto> listar() {
		List<clienteDto> lista = new ArrayList<>();
		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			String consulta = "SELECT * FROM cliente";
			ResultSet rs = estatuto.executeQuery(consulta);

			while (rs.next()) {
				clienteDto c = new clienteDto();
				c.setCodigoCliente(rs.getInt("codigo"));
				c.setDocumento(rs.getString("documento"));
				c.setNombre(rs.getString("nombre"));
				c.setTelefono(rs.getLong("telefono"));
				c.setDireccion(rs.getString("direccion"));
				lista.add(c);
			}

			rs.close();
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			System.err.println("Error al listar: " + e.getMessage());
		}
		return lista;
	}

	// 6. BÚSQUEDA MULTICRITERIO FLEXIBLE (Directo en MySQL)
	public List<clienteDto> buscarMulticriterio(String texto) {
		List<clienteDto> resultados = new ArrayList<>();
		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			// Búsqueda inteligente directo en la base de datos que acomoda grandes
			// volúmenes de datos
			String consulta = "SELECT * FROM cliente WHERE nombre LIKE '%" + texto + "%' " + "OR direccion LIKE '%"
					+ texto + "%' " + "OR CAST(codigo AS CHAR) LIKE '%" + texto + "%'";

			ResultSet rs = estatuto.executeQuery(consulta);

			while (rs.next()) {
				clienteDto c = new clienteDto();
				c.setCodigoCliente(rs.getInt("codigo"));
				c.setDocumento(rs.getString("documento"));
				c.setNombre(rs.getString("nombre"));
				c.setTelefono(rs.getLong("telefono"));
				c.setDireccion(rs.getString("direccion"));
				resultados.add(c);
			}

			rs.close();
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			System.err.println("Error en búsqueda multicriterio: " + e.getMessage());
		}
		return resultados;
	}
}