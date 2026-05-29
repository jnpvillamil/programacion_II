package co.edu.uptc.negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionProducto;
import co.edu.uptc.negocio.dto.productoDto;
import co.edu.uptc.persistencia.LocalProducto;

public class GestionProducto {

	private IGestionProducto iProducto;

	public GestionProducto() {
		this.iProducto = new LocalProducto();
	}

	public void registrar(productoDto miProducto) {
		// 1. Abrimos la conexión (El puente)
		Conexion conex = new Conexion();

		try {
			// 2. Preparamos el "vehículo" para enviar el mensaje SQL
			Statement estatuto = conex.getConnection().createStatement();

			// 3. Escribimos la orden en idioma SQL (Fíjate en las comillas simples para los
			// textos)
			String consulta = "INSERT INTO producto (codigo, nombre, categoria, precio_compra, precio_venta, stock_actual, stock_minimo, stock_maximo) VALUES ("
					+ miProducto.getCodigoProducto() + ", '" + miProducto.getNombre() + "', '"
					+ miProducto.getCategoria() + "', " + miProducto.getPrecioCompra() + ", "
					+ miProducto.getPrecioVenta() + ", " + miProducto.getStockActual() + ", "
					+ miProducto.getStockMinimo() + ", " + miProducto.getStockMaximo() + ")";

			// 4. Ejecutamos la orden en MySQL
			estatuto.executeUpdate(consulta);
			System.out.println("✅ Producto guardado exitosamente en MySQL: " + miProducto.getNombre());

			// 5. Cerramos y limpiamos
			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.err.println("❌ Error al guardar en la base de datos: " + e.getMessage());
		}
	}

	public void modificar(productoDto miProducto) {
		Conexion conex = new Conexion();

		try {
			Statement estatuto = conex.getConnection().createStatement();

			// Armamos la orden UPDATE
			String consulta = "UPDATE producto SET " + "nombre = '" + miProducto.getNombre() + "', " + "categoria = '"
					+ miProducto.getCategoria() + "', " + "precio_compra = " + miProducto.getPrecioCompra() + ", "
					+ "precio_venta = " + miProducto.getPrecioVenta() + ", " + "stock_actual = "
					+ miProducto.getStockActual() + ", " + "stock_minimo = " + miProducto.getStockMinimo() + ", "
					+ "stock_maximo = " + miProducto.getStockMaximo() + " " + "WHERE codigo = "
					+ miProducto.getCodigoProducto();

			// Ejecutamos la actualización
			estatuto.executeUpdate(consulta);
			System.out.println("✅ Producto actualizado en MySQL: " + miProducto.getNombre());

			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.err.println("❌ Error al modificar en BD: " + e.getMessage());
		}
	}

	public void inactivar(int codigoProducto) {
		Conexion conex = new Conexion();

		try {
			Statement estatuto = conex.getConnection().createStatement();

			String consulta = "DELETE FROM producto WHERE codigo = " + codigoProducto;

			estatuto.executeUpdate(consulta);
			System.out.println("🗑️ Producto eliminado de MySQL (Código: " + codigoProducto + ")");

			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.err.println("❌ Error al eliminar en BD: " + e.getMessage());
		}
	}

	public productoDto buscar(int codigo) throws Exception {
		return iProducto.buscar(codigo);
	}

	public List<productoDto> listar() {
		List<productoDto> listaDeMySQL = new ArrayList<>();
		Conexion conex = new Conexion();

		try {
			Statement estatuto = conex.getConnection().createStatement();

			// 1. Pedimos todos los productos a la base de datos
			String consulta = "SELECT * FROM producto";
			ResultSet rs = estatuto.executeQuery(consulta);

			// 2. Recorremos los resultados fila por fila (mientras haya un "siguiente")
			while (rs.next()) {
				productoDto p = new productoDto();

				// 3. Extraemos los datos de la fila de MySQL y los metemos al DTO
				p.setCodigoProducto(rs.getInt("codigo"));
				p.setNombre(rs.getString("nombre"));
				p.setCategoria(rs.getString("categoria"));
				p.setPrecioCompra(rs.getDouble("precio_compra"));
				p.setPrecioVenta(rs.getDouble("precio_venta"));
				p.setStockActual(rs.getInt("stock_actual"));
				p.setStockMinimo(rs.getInt("stock_minimo"));
				p.setStockMaximo(rs.getInt("stock_maximo"));

				// 4. Añadimos el producto listo a nuestra lista
				listaDeMySQL.add(p);
			}

			// 5. Cerramos todo
			rs.close();
			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.err.println("❌ Error al listar productos desde BD: " + e.getMessage());
		}

		return listaDeMySQL;
	}

	public List<productoDto> buscarMulticriterio(String texto) {
		List<productoDto> resultados = new ArrayList<>();

		// 1. Preparamos el texto de búsqueda (minúsculas para no discriminar)
		String textoMinuscula = texto.toLowerCase();

		// 2. Intentamos detectar si el usuario escribió un número (código)
		Integer codigoBuscado = null;
		try {
			codigoBuscado = Integer.parseInt(texto);
		} catch (NumberFormatException e) {
			// No es un número, así que se ignora y solo buscaremos por nombre
		}

		// 3. Recorremos todos los productos disponibles
		// Asumimos que tienes un método listar() que devuelve la lista completa
		for (productoDto p : listar()) {

			// --- Opción A: Buscar por ID exacto ---
			if (codigoBuscado != null && p.getCodigoProducto() == codigoBuscado) {
				resultados.add(p);
			}

			// --- Opción B: Buscar por nombre parcial ---
			// Usamos .contains() para encontrar el producto aunque escriban solo una parte
			else if (p.getNombre() != null && p.getNombre().toLowerCase().contains(textoMinuscula)) {
				resultados.add(p);
			}
		}

		return resultados;
	}
}
