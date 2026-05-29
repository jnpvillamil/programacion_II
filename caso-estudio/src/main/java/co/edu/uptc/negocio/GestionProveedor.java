package co.edu.uptc.negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.negocio.dto.proveedorDto;

public class GestionProveedor {

	// 1. REGISTRAR (Con validación de clones)
	public void registrar(proveedorDto proveedor) throws Exception {
		if (proveedor == null) {
			throw new Exception("No se tiene información del proveedor");
		}

		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();

			// Validar si el NIT ya existe
			String sqlValidacion = "SELECT COUNT(*) AS total FROM proveedor WHERE nit = '" + proveedor.getNit() + "'";
			ResultSet rsValidacion = estatuto.executeQuery(sqlValidacion);

			if (rsValidacion.next() && rsValidacion.getInt("total") > 0) {
				rsValidacion.close();
				estatuto.close();
				conex.desconectar();
				throw new Exception("Operación cancelada: Ya existe un proveedor con el NIT " + proveedor.getNit());
			}
			rsValidacion.close();

			// Insertar utilizando razon_social y correo
			String consulta = "INSERT INTO proveedor (nit, razon_social, direccion, telefono, correo) VALUES ('"
					+ proveedor.getNit() + "', '" + proveedor.getRazonSocial() + "', '" + proveedor.getDireccion()
					+ "', " + proveedor.getTelefono() + ", '" + proveedor.getCorreo() + "')";

			estatuto.executeUpdate(consulta);
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			throw new Exception("Error al registrar proveedor: " + e.getMessage());
		}
	}

	// 2. MODIFICAR
	public void modificar(proveedorDto proveedor) throws Exception {
		if (proveedor == null) {
			throw new Exception("No se tiene información del proveedor");
		}

		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			String consulta = "UPDATE proveedor SET " + "nit = '" + proveedor.getNit() + "', " + "razon_social = '"
					+ proveedor.getRazonSocial() + "', " + "direccion = '" + proveedor.getDireccion() + "', "
					+ "telefono = " + proveedor.getTelefono() + ", " + "correo = '" + proveedor.getCorreo() + "' "
					+ "WHERE codigo = " + proveedor.getCodigoProveedor();

			estatuto.executeUpdate(consulta);
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			throw new Exception("Error al modificar proveedor: " + e.getMessage());
		}
	}

	// 3. INACTIVAR
	public void inactivar(int codigo) throws Exception {
		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			String consulta = "DELETE FROM proveedor WHERE codigo = " + codigo;

			estatuto.executeUpdate(consulta);
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			throw new Exception("Error al eliminar proveedor: " + e.getMessage());
		}
	}

	// 4. LISTAR
	public List<proveedorDto> listar() {
		List<proveedorDto> lista = new ArrayList<>();
		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			String consulta = "SELECT * FROM proveedor";
			ResultSet rs = estatuto.executeQuery(consulta);

			while (rs.next()) {
				proveedorDto p = new proveedorDto();
				p.setCodigoProveedor(rs.getInt("codigo"));
				p.setNit(rs.getString("nit"));
				p.setRazonSocial(rs.getString("razon_social"));
				p.setDireccion(rs.getString("direccion"));
				p.setTelefono(rs.getLong("telefono"));
				p.setCorreo(rs.getString("correo"));
				lista.add(p);
			}
			rs.close();
			estatuto.close();
			conex.desconectar();
		} catch (SQLException e) {
			System.err.println("Error al listar proveedores: " + e.getMessage());
		}
		return lista;
	}

	// 5. BUSQUEDA MULTICRITERIO
	public List<proveedorDto> buscarMulticriterio(String texto) {
		List<proveedorDto> resultados = new ArrayList<>();
		Conexion conex = new Conexion();
		try {
			Statement estatuto = conex.getConnection().createStatement();
			// Búsqueda flexible por nit, razon social o codigo
			String consulta = "SELECT * FROM proveedor WHERE razon_social LIKE '%" + texto + "%' " + "OR nit LIKE '%"
					+ texto + "%' " + "OR CAST(codigo AS CHAR) LIKE '%" + texto + "%'";

			ResultSet rs = estatuto.executeQuery(consulta);

			while (rs.next()) {
				proveedorDto p = new proveedorDto();
				p.setCodigoProveedor(rs.getInt("codigo"));
				p.setNit(rs.getString("nit"));
				p.setRazonSocial(rs.getString("razon_social"));
				p.setDireccion(rs.getString("direccion"));
				p.setTelefono(rs.getLong("telefono"));
				p.setCorreo(rs.getString("correo"));
				resultados.add(p);
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