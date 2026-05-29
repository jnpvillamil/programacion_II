package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionProducto;
import co.edu.uptc.negocio.dto.productoDto;

public class LocalProducto implements IGestionProducto {

	// 1. Guardar estándar (para el sistema normal)
	@Override
	public void guardar(productoDto producto) {
		String sql = "INSERT INTO producto (codigo, nombre, stock_actual, precio_compra) VALUES (?, ?, ?, ?)";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, producto.getCodigoProducto());
			ps.setString(2, producto.getNombre());
			ps.setInt(3, producto.getStockActual());
			ps.setDouble(4, producto.getPrecioCompra());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Error al guardar: " + e.getMessage());
		}
	}

	// 2. Actualizar estándar (Obligatorio por la interfaz IGestionProducto)
	@Override
	public void actualizar(productoDto producto) {
		String sql = "UPDATE producto SET stock_actual = ?, precio_compra = ? WHERE codigo = ?";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, producto.getStockActual());
			ps.setDouble(2, producto.getPrecioCompra());
			ps.setInt(3, producto.getCodigoProducto());
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Error al actualizar: " + e.getMessage());
		}
	}

	// 3. ACTUALIZACIÓN ESPECIAL (Solo para GestionCompra, usa conexión compartida)
	public void actualizarConConexion(productoDto producto, Connection conex) throws SQLException {
		String sql = "UPDATE producto SET stock_actual = ?, precio_compra = ? WHERE codigo = ?";
		try (PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, producto.getStockActual());
			ps.setDouble(2, producto.getPrecioCompra());
			ps.setInt(3, producto.getCodigoProducto());
			ps.executeUpdate();
		}
	}

	@Override
	public productoDto buscar(int codigoProducto) {
		String sql = "SELECT * FROM producto WHERE codigo = ?";
		Conexion conexObj = new Conexion();
		productoDto producto = null;
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, codigoProducto);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				producto = new productoDto();
				producto.setCodigoProducto(rs.getInt("codigo"));
				producto.setStockActual(rs.getInt("stock_actual"));
				producto.setPrecioCompra(rs.getDouble("precio_compra"));
			}
		} catch (Exception e) {
			System.err.println("Error al buscar: " + e.getMessage());
		}
		return producto;
	}

	@Override
	public List<productoDto> listar() {
		List<productoDto> lista = new ArrayList<>();
		String sql = "SELECT * FROM producto";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection();
				PreparedStatement ps = conex.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				productoDto producto = new productoDto();
				producto.setCodigoProducto(rs.getInt("codigo"));
				producto.setStockActual(rs.getInt("stock_actual"));
				producto.setPrecioCompra(rs.getDouble("precio_compra"));
				lista.add(producto);
			}
		} catch (Exception e) {
			System.err.println("Error al listar: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public void eliminar(int codigoProducto) {
		String sql = "DELETE FROM producto WHERE codigo = ?";
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, codigoProducto);
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Error al eliminar: " + e.getMessage());
		}
	}

	public void ajustarStock(int codigoProducto, int cantidad) {
		String sql = "UPDATE producto SET stock_actual = stock_actual + ? WHERE codigo = ?";
		try (Connection conex = new Conexion().getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
			ps.setInt(1, cantidad);
			ps.setInt(2, codigoProducto);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error stock: " + e.getMessage());
		}
	}
}