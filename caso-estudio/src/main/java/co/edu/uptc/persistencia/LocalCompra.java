package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionCompra;
import co.edu.uptc.negocio.dto.compraDto;
import co.edu.uptc.negocio.dto.itemCompraDto;

public class LocalCompra implements IGestionCompra {

	@Override
	public void guardar(compraDto compra) {
		// 1. SQL para el encabezado
		String sqlCompra = "INSERT INTO compra (numero_factura_prov, fecha, codigo_proveedor, total_compra, impuestos_aplicados) VALUES (?, ?, ?, ?, ?)";
		// 2. SQL para los detalles (¡OJO! Verifica que estas columnas se llamen así en
		// tu phpMyAdmin)
		String sqlDetalle = "INSERT INTO detalle_compra (id_compra, codigo_producto, cantidad, costo_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

		Conexion conexObj = new Conexion();

		try (Connection conex = conexObj.getConnection()) {

			// Iniciamos una transacción manual para asegurar que se guarde el encabezado Y
			// los detalles
			conex.setAutoCommit(false);

			// Preparamos el SQL del encabezado pidiendo que nos devuelva el ID generado
			try (PreparedStatement psCompra = conex.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS)) {

				psCompra.setString(1, compra.getNumeroFacturaProv());
				psCompra.setString(2, compra.getFecha());
				psCompra.setInt(3, compra.getCodigoProveedor());
				psCompra.setDouble(4, compra.getTotalCompra());
				psCompra.setDouble(5, compra.getImpuestos());

				psCompra.executeUpdate();

				// --- CAPTURAR EL ID DE LA COMPRA ---
				ResultSet rs = psCompra.getGeneratedKeys();
				int idCompraGenerado = 0;
				if (rs.next()) {
					idCompraGenerado = rs.getInt(1); // Obtenemos el ID auto-incremental que creó MySQL
				}

				// --- GUARDAR LOS DETALLES DE LA COMPRA ---
				if (idCompraGenerado > 0 && compra.getDetalles() != null && !compra.getDetalles().isEmpty()) {
					try (PreparedStatement psDetalle = conex.prepareStatement(sqlDetalle)) {

						for (itemCompraDto item : compra.getDetalles()) {
							psDetalle.setInt(1, idCompraGenerado); // El ID que acabamos de capturar
							psDetalle.setInt(2, item.getCodigoProducto());
							psDetalle.setInt(3, item.getCantidad());
							psDetalle.setDouble(4, item.getCostoUnitario());
							psDetalle.setDouble(5, item.getSubtotal());

							psDetalle.addBatch(); // Agrega a la cola en lugar de ejecutar uno por uno
						}
						psDetalle.executeBatch(); // Ejecuta todos los inserts de detalle de una sola vez (más rápido)
					}
				}

				// Si todo salió bien, guardamos definitivamente en la base de datos
				conex.commit();
				System.out.println("Compra y detalles guardados exitosamente.");

			} catch (Exception e) {
				// Si algo falla, deshacemos todo para evitar que quede la compra sin detalles
				conex.rollback();
				System.err.println("Error en la transacción de compra. Se ha hecho rollback: " + e.getMessage());
			}

		} catch (Exception e) {
			System.err.println("Error de conexión al guardar compra: " + e.getMessage());
		}
	}

	@Override
	public List<compraDto> listar() {
		List<compraDto> lista = new ArrayList<>();
		String sql = "SELECT * FROM compra";
		Conexion conexObj = new Conexion();

		try (Connection conex = conexObj.getConnection();
				PreparedStatement ps = conex.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				compraDto c = new compraDto();
				c.setIdCompra(rs.getInt("id_compra"));
				c.setNumeroFacturaProv(rs.getString("numero_factura_prov"));
				c.setFecha(rs.getString("fecha"));
				c.setCodigoProveedor(rs.getInt("codigo_proveedor"));
				c.setTotalCompra(rs.getDouble("total_compra"));
				c.setImpuestos(rs.getDouble("impuestos_aplicados"));
				lista.add(c);
			}
		} catch (Exception e) {
			System.err.println("Error al listar compras: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public void anular(int numeroFactura) {
		// TODO Auto-generated method stub

	}

	@Override
	public compraDto buscar(int numeroFactura) {
		// TODO Auto-generated method stub
		return null;
	}

	// Agrega este método dentro de tu clase LocalCompra
	public void guardar(compraDto compra, Connection conex) throws Exception {
		String sql = "INSERT INTO compra (fecha, codigo_proveedor, subtotal, impuestos, total) VALUES (?, ?, ?, ?, ?)";

		// Usamos la conexión que viene de GestionCompra
		try (PreparedStatement ps = conex.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, compra.getFecha());
			ps.setInt(2, compra.getCodigoProveedor());
			ps.setDouble(3, compra.getSubtotal());
			ps.setDouble(4, compra.getImpuestos());
			ps.setDouble(5, compra.getTotalCompra());
			ps.executeUpdate();

			// Obtenemos el ID generado para los detalles
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					int idCompra = rs.getInt(1);
					// Guardamos los detalles con la misma conexión
					String sqlDet = "INSERT INTO detalle_compra (id_compra, codigo_producto, cantidad, costo_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
					try (PreparedStatement psDet = conex.prepareStatement(sqlDet)) {
						for (co.edu.uptc.negocio.dto.itemCompraDto item : compra.getDetalles()) {
							psDet.setInt(1, idCompra);
							psDet.setInt(2, item.getCodigoProducto());
							psDet.setInt(3, item.getCantidad());
							psDet.setDouble(4, item.getCostoUnitario());
							psDet.setDouble(5, item.getSubtotal());
							psDet.executeUpdate();
						}
					}
				}
			}
		}
	}

}