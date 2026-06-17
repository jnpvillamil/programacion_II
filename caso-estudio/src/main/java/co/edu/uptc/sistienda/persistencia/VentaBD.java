package co.edu.uptc.sistienda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.sistienda.interfaces.IGestionVenta;
import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.Venta;
import co.edu.uptc.sistienda.modelo.enums.FormaPagoEnum;

// Esta clase guarda, anula, busca y lista ventas en la base de datos MySQL.
public class VentaBD implements IGestionVenta {

	// GUARDAR una venta completa (datos generales + productos vendidos)
	@Override
	public void guardarVenta(Venta venta) {

		String sqlDatosGeneralesDeLaFactura = "INSERT INTO ventas "
				+ "(numero_factura, fecha_hora, fecha_vencimiento, codigo_cliente, " + " forma_pago, medio_pago, cufe "
				+ " valor_en_letras, " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";

		String sqlCadaProductoDeLaFactura = "INSERT INTO detalle_venta "
				+ "(numero_factura, codigo_producto, cantidad, precio_unitario, descuento_pct) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (Connection conexion = ConexionBD.conectar()) {

			// Inicia transacción: si algo falla, cancela todo (nada queda a medias)
			conexion.setAutoCommit(false);

			// Paso 1: guardar los datos generales de la factura
			try (PreparedStatement insertarDatosGenerales = conexion.prepareStatement(sqlDatosGeneralesDeLaFactura)) {

				insertarDatosGenerales.setString(1, venta.getNumeroFactura());
				insertarDatosGenerales.setTimestamp(2, Timestamp.valueOf(venta.getFechaHora()));
				insertarDatosGenerales.setTimestamp(3, Timestamp.valueOf(venta.getFechaVencimiento()));
				insertarDatosGenerales.setString(4, venta.getCliente().getCodigoCliente());
				insertarDatosGenerales.setString(5, venta.getFormaPago().name());
				insertarDatosGenerales.setString(6, venta.getMedioPago());
				insertarDatosGenerales.setString(7, venta.getCufe());
				insertarDatosGenerales.setString(9, venta.getValorEnLetras());
				insertarDatosGenerales.executeUpdate();
			}

			// Paso 2: guardar cada producto vendido en esa factura
			try (PreparedStatement insertarProductoVendido = conexion.prepareStatement(sqlCadaProductoDeLaFactura)) {

				for (DetalleVenta productoVendido : venta.getItems()) {
					insertarProductoVendido.setString(1, venta.getNumeroFactura());
					insertarProductoVendido.setString(2, productoVendido.getProducto().getCodigoInterno());
					insertarProductoVendido.setInt(3, productoVendido.getCantidad());
					insertarProductoVendido.setDouble(4, productoVendido.getPrecioUnitario());
					insertarProductoVendido.setDouble(5, productoVendido.getDescuentoDto());
					insertarProductoVendido.addBatch(); // Acumula cada producto
				}
				insertarProductoVendido.executeBatch(); // Inserta todos los productos de una vez
			}

			conexion.commit(); // Todo salió bien: confirma y guarda en la base de datos

			JOptionPane.showMessageDialog(null, "Venta registrada exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo registrar la venta", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ANULAR una venta (no se elimina, solo se marca como anulada)
	@Override
	public void anularVenta(String numeroFactura) {

		String sql = "UPDATE ventas SET anulada = 1 WHERE numero_factura = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement anularVenta = conexion.prepareStatement(sql)) {

			anularVenta.setString(1, numeroFactura);
			anularVenta.executeUpdate();

			JOptionPane.showMessageDialog(null, "Venta anulada correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo anular la venta", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// BUSCAR una venta por su número de factura
	@Override
	public Venta buscarVentaPorNumeroFactura(String numeroFactura) {

		String sql = "SELECT * FROM ventas WHERE numero_factura = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement buscarVenta = conexion.prepareStatement(sql)) {

			buscarVenta.setString(1, numeroFactura);
			ResultSet ventaEncontrada = buscarVenta.executeQuery();

			if (ventaEncontrada.next()) {
				Venta venta = convertirFilaEnVenta(ventaEncontrada, conexion);
				cargarProductosDeLaVenta(venta, conexion);
				return venta;
			}

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo buscar la venta", "Error", JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}

	// LISTAR todas las ventas registradas
	@Override
	public List<Venta> obtenerListaVentas() {

		List<Venta> listaVentas = new ArrayList<>();
		String sql = "SELECT * FROM ventas ORDER BY fecha_hora DESC";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement traerVentas = conexion.prepareStatement(sql);
				ResultSet todasLasVentas = traerVentas.executeQuery()) {

			while (todasLasVentas.next()) {
				Venta venta = convertirFilaEnVenta(todasLasVentas, conexion);
				cargarProductosDeLaVenta(venta, conexion);
				listaVentas.add(venta);
			}

		} catch (SQLException error) {
			System.out.println(error.getMessage());
			JOptionPane.showMessageDialog(null, "No se pudo obtener la lista de ventas", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return listaVentas;
	}

	// Método que convierte una fila de ventas en un objeto Venta
	private Venta convertirFilaEnVenta(ResultSet filaDeLaVenta, Connection conexion) throws SQLException {
		Venta venta = new Venta();
		venta.setNumeroFactura(filaDeLaVenta.getString("numero_factura"));
		venta.setFechaHora(filaDeLaVenta.getTimestamp("fecha_hora").toLocalDateTime());
		venta.setFechaVencimiento(filaDeLaVenta.getTimestamp("fecha_vencimiento").toLocalDateTime());
		venta.setFormaPago(FormaPagoEnum.valueOf(filaDeLaVenta.getString("forma_pago")));
		venta.setMedioPago(filaDeLaVenta.getString("medio_pago"));
		venta.setCufe(filaDeLaVenta.getString("cufe")); 
		venta.setValorEnLetras(filaDeLaVenta.getString("valor_en_letras")); 
		venta.setAnulada(filaDeLaVenta.getInt("anulada") == 1);

		Cliente clienteQueCompro = buscarClienteDeLaVenta(filaDeLaVenta.getString("codigo_cliente"), conexion);
		venta.setCliente(clienteQueCompro);

		return venta;
	}

	// Método que trae el nombre y código del cliente de la venta
	private Cliente buscarClienteDeLaVenta(String codigoCliente, Connection conexion) throws SQLException {

		String sql = "SELECT codigo_cliente, nombre_razon_social FROM clientes WHERE codigo_cliente = ?";

		try (PreparedStatement buscarCliente = conexion.prepareStatement(sql)) {
			buscarCliente.setString(1, codigoCliente);
			ResultSet clienteEncontrado = buscarCliente.executeQuery();

			if (clienteEncontrado.next()) {
				Cliente cliente = new Cliente();
				cliente.setCodigoCliente(clienteEncontrado.getString("codigo_cliente"));
				cliente.setNombreCompletoORazonSocial(clienteEncontrado.getString("nombre_razon_social"));
				return cliente;
			}
		}

		return null;
	}

	// Método que carga todos los productos vendidos en la venta
	private void cargarProductosDeLaVenta(Venta venta, Connection conexion) throws SQLException {

		String sql = "SELECT dv.*, p.nombre_producto, p.precio_venta, p.stock_actual " + "FROM detalle_venta dv "
				+ "JOIN productos p ON dv.codigo_producto = p.codigo_interno " + "WHERE dv.numero_factura = ?";

		try (PreparedStatement buscarProductosDeLaVenta = conexion.prepareStatement(sql)) {
			buscarProductosDeLaVenta.setString(1, venta.getNumeroFactura());
			ResultSet productosVendidos = buscarProductosDeLaVenta.executeQuery();

			while (productosVendidos.next()) {
				Producto productoVendido = new Producto();
				productoVendido.setCodigoInterno(productosVendidos.getString("codigo_producto"));
				productoVendido.setNombreProducto(productosVendidos.getString("nombre_producto"));
				productoVendido.setPrecioVenta(productosVendidos.getDouble("precio_venta"));
				productoVendido.setStockActual(productosVendidos.getInt("stock_actual"));

				DetalleVenta itemVendido = new DetalleVenta(productoVendido, productosVendidos.getInt("cantidad"),
						productosVendidos.getDouble("precio_unitario"));
				itemVendido.setDescuentoDto(productosVendidos.getDouble("descuento_pct"));
				venta.agregarItem(itemVendido);
			}
		}
	}
}