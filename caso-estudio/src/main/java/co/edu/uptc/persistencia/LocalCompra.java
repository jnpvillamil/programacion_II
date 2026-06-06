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

	// --- MÉTODOS DE LA INTERFAZ ---

	@Override
	public void guardar(compraDto compra) {
		// Versión estándar: Crea su propia conexión (para uso fuera de transacciones)
		Conexion conexObj = new Conexion();
		try (Connection conex = conexObj.getConnection()) {
			guardar(compra, conex);
		} catch (Exception e) {
			System.err.println("Error al guardar compra (estándar): " + e.getMessage());
		}
	}

	@Override
	public void anular(int numeroFactura) {
		// Lógica de anulación (puedes implementar si la necesitas)
	}

	@Override
	public compraDto buscar(int numeroFactura) {
		// Lógica de búsqueda
		return null;
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
				c.setIdCompra(rs.getInt("id")); // Verifica si tu BD usa 'id' o 'id_compra'
				c.setFecha(rs.getString("fecha"));
				c.setTotalCompra(rs.getDouble("total"));
				lista.add(c);
			}
		} catch (Exception e) {
			System.err.println("Error al listar: " + e.getMessage());
		}
		return lista;
	}

	// --- MÉTODOS TRANSACCIONALES (Para GestiónCompra) ---

	public void guardar(compraDto compra, Connection conex) throws Exception {
		// SQL corregido para coincidir con tu GestionCompra
		String sql = "INSERT INTO compra (fecha, codigo_proveedor,  impuestos_aplicados, total_compra) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement ps = conex.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, compra.getFecha());
			ps.setInt(2, compra.getCodigoProveedor());
			ps.setDouble(3, compra.getSubtotal());
			ps.setDouble(4, compra.getImpuestos());
			ps.setDouble(5, compra.getTotalCompra());
			ps.executeUpdate();

			// Obtener ID generado
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					int idCompra = rs.getInt(1);

					// Guardar detalles con la MISMA conexión
					String sqlDet = "INSERT INTO detalle_compra (id_compra, codigo_producto, cantidad, costo_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
					try (PreparedStatement psDet = conex.prepareStatement(sqlDet)) {
						for (itemCompraDto item : compra.getDetalles()) {
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