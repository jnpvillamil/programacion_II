package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionProveedor;
import co.edu.uptc.negocio.dto.proveedorDto;

public class LocalProveedor implements IGestionProveedor {

	private Conexion conexObj = new Conexion();

	@Override
	public void guardar(proveedorDto p) {
		// Mapeo: DTO (razonSocial) -> BD (razon_social)
		String sql = "INSERT INTO proveedor (codigo, razon_social, nit, direccion, telefono, correo) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, p.getCodigoProveedor());
			ps.setString(2, p.getRazonSocial());
			ps.setString(3, p.getNit());
			ps.setString(4, p.getDireccion());
			ps.setLong(5, p.getTelefono());
			ps.setString(6, p.getCorreo());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al guardar proveedor: " + e.getMessage());
		}
	}

	@Override
	public List<proveedorDto> listar() {
		List<proveedorDto> lista = new ArrayList<>();
		String sql = "SELECT * FROM proveedor";
		try (Connection conex = conexObj.getConnection();
				PreparedStatement ps = conex.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				lista.add(mapRow(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error al listar: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public proveedorDto buscar(int codigo) {
		String sql = "SELECT * FROM proveedor WHERE codigo = ?";
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, codigo);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return mapRow(rs);
		} catch (SQLException e) {
			System.err.println("Error al buscar: " + e.getMessage());
		}
		return null;
	}

	// Método especializado para tu PanelCompra (Autocompletado)
	public int buscarCodigoPorNombre(String razonSocial) {
		// Usamos LIKE con el comodín '%' para buscar coincidencias parciales
		// Ejemplo: 'Bavaria' coincidirá con 'Bavaria SAS', 'Cerveceria Bavaria', etc.
		String sql = "SELECT codigo FROM proveedor WHERE razon_social LIKE ?";

		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {

			// Agregamos '%' antes y después para buscar en cualquier parte del texto
			ps.setString(1, "%" + razonSocial + "%");

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("codigo");
			}
		} catch (SQLException e) {
			System.err.println("Error al buscar código por nombre: " + e.getMessage());
		}
		return -1; // Retorna -1 si no encuentra ninguna coincidencia
	}

	// Método auxiliar para no repetir código (El corazón del mapeo)
	private proveedorDto mapRow(ResultSet rs) throws SQLException {
		proveedorDto p = new proveedorDto();
		p.setCodigoProveedor(rs.getInt("codigo"));
		p.setRazonSocial(rs.getString("razon_social")); // Mapeo de BD a DTO
		p.setNit(rs.getString("nit"));
		p.setDireccion(rs.getString("direccion"));
		p.setTelefono(rs.getLong("telefono"));
		p.setCorreo(rs.getString("correo"));
		return p;
	}

	// Métodos que no implementamos pero la interfaz exige (puedes dejarlos vacíos o
	// throw UnsupportedOperationException)
	@Override
	public void actualizar(proveedorDto p) {
	}

	@Override
	public void eliminar(int codigo) {
	}
}