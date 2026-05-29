package co.edu.uptc.negocio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.negocio.dto.detalleVentaDto;
import co.edu.uptc.negocio.dto.ventaDto;

public class GestionVenta {

	public int registrarVentaCompleta(ventaDto venta) throws Exception {
		if (venta == null || venta.getDetalles().isEmpty()) {
			throw new Exception("La venta no tiene productos (El carrito está vacío).");
		}

		Conexion conexObj = new Conexion();
		Connection conex = conexObj.getConnection(); // Extraemos la conexión directa
		int idFacturaGenerado = 0;
		Statement estatuto = null;

		try {
			// --- 1. INICIO DE TRANSACCIÓN ---
			// Apagamos el guardado automático. Ahora MySQL esperará nuestra orden final.
			conex.setAutoCommit(false);

			estatuto = conex.createStatement();

			// 2. GUARDAR LA CABECERA
			String sqlVenta = "INSERT INTO venta (codigo_cliente, forma_pago, aplica_iva, subtotal, total) VALUES ("
					+ venta.getCodigoCliente() + ", '" + venta.getFormaPago() + "', " + venta.isAplicaIva() + ", "
					+ venta.getSubtotal() + ", " + venta.getTotal() + ")";

			estatuto.executeUpdate(sqlVenta);

			// 3. OBTENER EL NÚMERO DE FACTURA
			ResultSet rs = estatuto.executeQuery("SELECT LAST_INSERT_ID() AS id");
			if (rs.next()) {
				idFacturaGenerado = rs.getInt("id");
			}
			rs.close();

			// 4. GUARDAR DETALLES Y DESCONTAR INVENTARIO
			for (detalleVentaDto detalle : venta.getDetalles()) {
				String sqlDetalle = "INSERT INTO detalle_venta (numero_factura, codigo_producto, cantidad, precio_unitario, subtotal) VALUES ("
						+ idFacturaGenerado + ", " + detalle.getCodigoProducto() + ", " + detalle.getCantidad() + ", "
						+ detalle.getPrecioUnitario() + ", " + detalle.getSubtotal() + ")";
				estatuto.executeUpdate(sqlDetalle);

				String sqlStock = "UPDATE producto SET stock_actual = stock_actual - " + detalle.getCantidad()
						+ " WHERE codigo = " + detalle.getCodigoProducto();
				estatuto.executeUpdate(sqlStock);
			}

			// --- 5. CONFIRMAR TRANSACCIÓN (COMMIT) ---
			// Si el código llegó hasta aquí, significa que todo salió perfecto. Guardamos
			// definitivamente.
			conex.commit();

		} catch (SQLException e) {
			// --- 6. REVERTIR TRANSACCIÓN (ROLLBACK) ---
			// Si hubo CUALQUIER error (falta de stock, error de red, caída de BD),
			// deshacemos todo.
			if (conex != null) {
				try {
					conex.rollback();
					System.err.println("Transacción revertida (Rollback) ejecutada por seguridad.");
				} catch (SQLException exRollback) {
					System.err.println("Error fatal al intentar revertir: " + exRollback.getMessage());
				}
			}
			throw new Exception("Error al registrar la venta. Operación cancelada por seguridad: " + e.getMessage());

		} finally {
			// 7. LIMPIEZA DE RECURSOS
			if (estatuto != null) {
				try {
					estatuto.close();
				} catch (SQLException e) {
					/* ignorar */ }
			}
			if (conex != null) {
				try {
					conex.setAutoCommit(true); // Restauramos la BD a su estado normal
					conexObj.desconectar();
				} catch (SQLException e) {
					/* ignorar */ }
			}
		}

		return idFacturaGenerado;
	}
}