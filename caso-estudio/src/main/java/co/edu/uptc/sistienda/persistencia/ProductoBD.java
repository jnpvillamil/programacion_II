package co.edu.uptc.sistienda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import co.edu.uptc.sistienda.interfaces.IGestionProducto;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.enums.CategoriaProductoEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoImpuestoEnum;

// Esta clase guarda, actualiza, busca y lista productos en la base de datos MySQL
public class ProductoBD implements IGestionProducto {

	// GUARDAR un producto nuevo en la tabla productos
	@Override
	public void guardarProducto(Producto producto) {

		String sql = "INSERT INTO productos " + "(codigo_interno, nombre_producto, categoria, tipo_impuesto, "
				+ " precio_compra, precio_venta, stock_actual, stock_minimo, stock_maximo, activo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement insertarProducto = conexion.prepareStatement(sql)) {

			insertarProducto.setString(1, producto.getCodigoInterno());
			insertarProducto.setString(2, producto.getNombreProducto());
			insertarProducto.setString(3, producto.getCategoria().name());
			insertarProducto.setString(4, producto.getTipoImpuesto().name());
			insertarProducto.setDouble(5, producto.getPrecioCompra());
			insertarProducto.setDouble(6, producto.getPrecioVenta());
			insertarProducto.setInt(7, producto.getStockActual());
			insertarProducto.setInt(8, producto.getStockMinimo());
			insertarProducto.setInt(9, producto.getStockMaximo()); 

			insertarProducto.executeUpdate();

			JOptionPane.showMessageDialog(null, "Producto guardado exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo guardar el producto", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ACTUALIZAR los datos de un producto que ya existe
	@Override
	public void actualizarProducto(Producto producto) {

		String sql = "UPDATE productos SET " + "nombre_producto = ?, categoria = ?, tipo_impuesto = ?, "
				+ "precio_compra = ?, precio_venta = ?, " + "stock_actual = ?, stock_minimo = ?, stock_maximo = ? " 
																													
																													
				+ "WHERE codigo_interno = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement actualizarProducto = conexion.prepareStatement(sql)) {

			actualizarProducto.setString(1, producto.getNombreProducto());
			actualizarProducto.setString(2, producto.getCategoria().name());
			actualizarProducto.setString(3, producto.getTipoImpuesto().name());
			actualizarProducto.setDouble(4, producto.getPrecioCompra());
			actualizarProducto.setDouble(5, producto.getPrecioVenta());
			actualizarProducto.setInt(6, producto.getStockActual());
			actualizarProducto.setInt(7, producto.getStockMinimo());
			actualizarProducto.setInt(8, producto.getStockMaximo()); 
			actualizarProducto.setString(9, producto.getCodigoInterno()); // ← condición WHERE

			actualizarProducto.executeUpdate();

			JOptionPane.showMessageDialog(null, "Producto actualizado correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo actualizar el producto", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// INACTIVAR un producto
	@Override
	public void inactivarProducto(String codigoProducto) {

		String sql = "UPDATE productos SET activo = 0 WHERE codigo_interno = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement inactivarProducto = conexion.prepareStatement(sql)) {

			inactivarProducto.setString(1, codigoProducto);
			inactivarProducto.executeUpdate();

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo inactivar el producto", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ACTIVAR un producto que estaba inactivo
	@Override
	public void activarProducto(String codigoProducto) {

		String sql = "UPDATE productos SET activo = 1 WHERE codigo_interno = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement activarProducto = conexion.prepareStatement(sql)) {

			activarProducto.setString(1, codigoProducto);
			activarProducto.executeUpdate();

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo activar el producto", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// BUSCAR un producto por su código interno
	@Override
	public Producto buscarProductoPorCodigo(String codigoProducto) {

		String sql = "SELECT * FROM productos WHERE codigo_interno = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement buscarProducto = conexion.prepareStatement(sql)) {

			buscarProducto.setString(1, codigoProducto);
			ResultSet productoEncontrado = buscarProducto.executeQuery();

			if (productoEncontrado.next()) {
				return convertirFilaEnProducto(productoEncontrado);
			}

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo buscar el producto", "Error", JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}

	// LISTAR todos los productos de la base de datos
	@Override
	public List<Producto> obtenerListaProductos() {

		List<Producto> listaProductos = new ArrayList<>();
		String sql = "SELECT * FROM productos ORDER BY nombre_producto";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement traerProductos = conexion.prepareStatement(sql);
				ResultSet todosLosProductos = traerProductos.executeQuery()) {

			while (todosLosProductos.next()) {
				listaProductos.add(convertirFilaEnProducto(todosLosProductos));
			}

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo obtener la lista de productos", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return listaProductos;
	}

	// LISTAR solo los productos con stock por debajo del mínimo
	@Override
	public List<Producto> obtenerProductosConStockBajoMinimo() {
		return obtenerListaProductos().stream().filter(p -> p.isActivo() && p.tieneStockBajoMinimo())
				.collect(Collectors.toList());
	}

	// Método que convierte una fila de la tabla en un objeto Producto
	private Producto convertirFilaEnProducto(ResultSet filaDelProducto) throws SQLException {
		Producto producto = new Producto();
		producto.setCodigoInterno(filaDelProducto.getString("codigo_interno"));
		producto.setNombreProducto(filaDelProducto.getString("nombre_producto"));
		producto.setCategoria(CategoriaProductoEnum.valueOf(filaDelProducto.getString("categoria")));
		producto.setTipoImpuesto(TipoImpuestoEnum.valueOf(filaDelProducto.getString("tipo_impuesto")));
		producto.setPrecioCompra(filaDelProducto.getDouble("precio_compra"));
		producto.setPrecioVenta(filaDelProducto.getDouble("precio_venta"));
		producto.setStockActual(filaDelProducto.getInt("stock_actual"));
		producto.setStockMinimo(filaDelProducto.getInt("stock_minimo"));
		producto.setStockMaximo(filaDelProducto.getInt("stock_maximo")); 
		producto.setActivo(filaDelProducto.getInt("activo") == 1);
		return producto;
	}
}