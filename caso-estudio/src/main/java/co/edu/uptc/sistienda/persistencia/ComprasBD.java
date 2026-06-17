package co.edu.uptc.sistienda.persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.sistienda.compras.modelo.Compra;
import co.edu.uptc.sistienda.compras.modelo.DetalleCompra;
import co.edu.uptc.sistienda.interfaces.IGestionCompra;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.Proveedor;

// Esta clase guarda, anula, busca y lista compras en la base de datos MySQL.
public class ComprasBD implements IGestionCompra {

	// GUARDAR una compra completa (datos generales + productos comprados)
	@Override
	public void guardarCompra(Compra compra) {

		String sqlDatosGeneralesDeLaCompra = "INSERT INTO compras "
				+ "(numero_compra, fecha_compra, codigo_proveedor, total_compra, "
				+ " numero_factura_proveedor, medio_pago, valor_retencion, anulada) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, 0)";

		String sqlCadaProductoDeLaCompra = "INSERT INTO detalle_compra "
				+ "(numero_compra, codigo_producto, cantidad, precio_compra, subtotal) " + "VALUES (?, ?, ?, ?, ?)";

		try (Connection conexion = ConexionBD.conectar()) {

			// Inicia transacción: si algo falla, cancela todo (nada queda a medias)
			conexion.setAutoCommit(false);

			// Paso 1: guardar los datos generales de la compra
			try (PreparedStatement insertarDatosGenerales = conexion.prepareStatement(sqlDatosGeneralesDeLaCompra)) {

				insertarDatosGenerales.setString(1, compra.getNumeroCompra());
				insertarDatosGenerales.setDate(2, Date.valueOf(compra.getFechaCompra()));
				insertarDatosGenerales.setString(3, compra.getProveedor().getCodigoProveedor());
				insertarDatosGenerales.setDouble(4, compra.getTotalCompra());
				insertarDatosGenerales.setString(5, compra.getNumeroFacturaProveedor());
				insertarDatosGenerales.setString(6, compra.getMedioPago());
				insertarDatosGenerales.setDouble(7, compra.getValorRetencion());
				insertarDatosGenerales.executeUpdate();
			}

			// Paso 2: guardar cada producto comprado en esa orden
			try (PreparedStatement insertarProductoComprado = conexion.prepareStatement(sqlCadaProductoDeLaCompra)) {

				for (DetalleCompra productoComprado : compra.getDetalles()) {
					insertarProductoComprado.setString(1, compra.getNumeroCompra());
					insertarProductoComprado.setString(2, productoComprado.getProducto().getCodigoInterno());
					insertarProductoComprado.setInt(3, productoComprado.getCantidad());
					insertarProductoComprado.setDouble(4, productoComprado.getPrecioCompra());
					insertarProductoComprado.setDouble(5, productoComprado.getSubtotal());
					insertarProductoComprado.addBatch(); // Acumula cada producto
				}
				insertarProductoComprado.executeBatch(); // Inserta todos los productos de una vez
			}

			conexion.commit(); // Todo salió bien: confirma y guarda en la base de datos

			JOptionPane.showMessageDialog(null, "Compra registrada exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo registrar la compra", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ANULAR una compra (no se elimina, solo se marca como anulada)
	@Override
	public void anularCompra(String numeroCompra) {

		String sql = "UPDATE compras SET anulada = 1 WHERE numero_compra = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement anularCompra = conexion.prepareStatement(sql)) {

			anularCompra.setString(1, numeroCompra);
			anularCompra.executeUpdate();

			JOptionPane.showMessageDialog(null, "Compra anulada correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo anular la compra", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// BUSCAR una compra por su número
	@Override
	public Compra buscarCompra(String numeroCompra) {

		String sql = "SELECT * FROM compras WHERE numero_compra = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement buscarCompra = conexion.prepareStatement(sql)) {

			buscarCompra.setString(1, numeroCompra);
			ResultSet compraEncontrada = buscarCompra.executeQuery();

			if (compraEncontrada.next()) {
				Compra compra = convertirFilaEnCompra(compraEncontrada, conexion);
				cargarProductosDeLaCompra(compra, conexion);
				return compra;
			}

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo buscar la compra", "Error", JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}

	// LISTAR todas las compras registradas
	@Override
	public List<Compra> obtenerListaCompras() {

		List<Compra> listaCompras = new ArrayList<>();
		String sql = "SELECT * FROM compras ORDER BY fecha_compra DESC";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement traerCompras = conexion.prepareStatement(sql);
				ResultSet todasLasCompras = traerCompras.executeQuery()) {

			while (todasLasCompras.next()) {
				Compra compra = convertirFilaEnCompra(todasLasCompras, conexion);
				cargarProductosDeLaCompra(compra, conexion);
				listaCompras.add(compra);
			}

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo obtener la lista de compras", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return listaCompras;
	}

	// Método que convierte una fila de compras en un objeto Compra
	private Compra convertirFilaEnCompra(ResultSet filaDeLaCompra, Connection conexion) throws SQLException {
		Compra compra = new Compra();
		compra.setNumeroCompra(filaDeLaCompra.getString("numero_compra"));
		compra.setFechaCompra(filaDeLaCompra.getDate("fecha_compra").toLocalDate());
		compra.setTotalCompra(filaDeLaCompra.getDouble("total_compra"));
		compra.setNumeroFacturaProveedor(filaDeLaCompra.getString("numero_factura_proveedor"));
		compra.setMedioPago(filaDeLaCompra.getString("medio_pago"));
		compra.setValorRetencion(filaDeLaCompra.getDouble("valor_retencion"));
		compra.setAnulada(filaDeLaCompra.getInt("anulada") == 1);

		Proveedor proveedor = buscarProveedorDeLaCompra(filaDeLaCompra.getString("codigo_proveedor"), conexion);
		compra.setProveedor(proveedor);

		return compra;
	}

	// Método que trae el nombre y código del proveedor de la compra
	private Proveedor buscarProveedorDeLaCompra(String codigoProveedor, Connection conexion) throws SQLException {

		String sql = "SELECT codigo_proveedor, razon_social FROM proveedores WHERE codigo_proveedor = ?";

		try (PreparedStatement buscarProveedor = conexion.prepareStatement(sql)) {
			buscarProveedor.setString(1, codigoProveedor);
			ResultSet proveedorEncontrado = buscarProveedor.executeQuery();

			if (proveedorEncontrado.next()) {
				Proveedor proveedor = new Proveedor();
				proveedor.setCodigoProveedor(proveedorEncontrado.getString("codigo_proveedor"));
				proveedor.setRazonSocial(proveedorEncontrado.getString("razon_social"));
				return proveedor;
			}
		}

		return null;
	}

	// Método que carga todos los productos comprados en la orden de compra
	private void cargarProductosDeLaCompra(Compra compra, Connection conexion) throws SQLException {

		String sql = "SELECT dc.*, p.nombre_producto, p.precio_venta, p.stock_actual " + "FROM detalle_compra dc "
				+ "JOIN productos p ON dc.codigo_producto = p.codigo_interno " + "WHERE dc.numero_compra = ?";

		try (PreparedStatement buscarProductosDeLaCompra = conexion.prepareStatement(sql)) {
			buscarProductosDeLaCompra.setString(1, compra.getNumeroCompra());
			ResultSet productosComprados = buscarProductosDeLaCompra.executeQuery();

			while (productosComprados.next()) {
				Producto productoComprado = new Producto();
				productoComprado.setCodigoInterno(productosComprados.getString("codigo_producto"));
				productoComprado.setNombreProducto(productosComprados.getString("nombre_producto"));
				productoComprado.setPrecioVenta(productosComprados.getDouble("precio_venta"));
				productoComprado.setStockActual(productosComprados.getInt("stock_actual"));

				DetalleCompra itemComprado = new DetalleCompra(productoComprado, productosComprados.getInt("cantidad"),
						productosComprados.getDouble("precio_compra"));
				compra.agregarDetalle(itemComprado);
			}
		}
	}
}