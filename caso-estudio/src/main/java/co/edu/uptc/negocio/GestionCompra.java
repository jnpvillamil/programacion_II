package co.edu.uptc.negocio;

import java.sql.Connection;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionCompra;
import co.edu.uptc.interfaces.IGestionMovimientoContable;
import co.edu.uptc.interfaces.IGestionProducto;
import co.edu.uptc.negocio.dto.compraDto;
import co.edu.uptc.negocio.dto.itemCompraDto;
import co.edu.uptc.negocio.dto.movimientoContableDto;
import co.edu.uptc.negocio.dto.productoDto;
import co.edu.uptc.persistencia.database.DatabaseCompra;
import co.edu.uptc.persistencia.database.DatabaseMovimientoContable;
import co.edu.uptc.persistencia.database.DatabaseProducto;
public class GestionCompra {

	private IGestionCompra iCompra;
	private IGestionMovimientoContable iMovimiento;
	private IGestionProducto iProducto;

	public GestionCompra() {
		this.iCompra = new DatabaseCompra();
		this.iMovimiento = new DatabaseMovimientoContable();
		this.iProducto = new DatabaseProducto();
	}

	public void registrar(compraDto compra) throws Exception {
		// 1. Validaciones
		if (compra == null)
			throw new Exception("No se tiene información de la compra");
		if (compra.getDetalles() == null || compra.getDetalles().isEmpty())
			throw new Exception("La compra debe tener al menos un producto");
		if (compra.getCodigoProveedor() <= 0)
			throw new Exception("Debe seleccionar un proveedor válido");

		calcularTotales(compra);

		// 2. Conexión única para la transacción
		Conexion conexObj = new Conexion();
		Connection conex = conexObj.getConnection();

		try {
			conex.setAutoCommit(false); // Iniciamos transacción

			// 3. Ejecución secuencial usando la misma conexión
			((DatabaseCompra) iCompra).guardar(compra, conex);
			actualizarInventario(compra, conex);
			registrarEgresoContable(compra, conex);

			conex.commit(); // Confirmamos todo
		} catch (Exception e) {
			conex.rollback(); // Si falla algo, revertimos todo
			throw new Exception("Error crítico: Compra cancelada. Detalles: " + e.getMessage());
		} finally {
			conex.setAutoCommit(true);
			conex.close();
			conexObj.desconectar();
		}
	}

	public void anular(int numeroFactura) throws Exception {
		compraDto compra = iCompra.buscar(numeroFactura);
		if (compra == null)
			throw new Exception("No se encontró la compra con factura N° " + numeroFactura);
		iCompra.anular(numeroFactura);
	}

	public compraDto buscar(int numeroFactura) throws Exception {
		compraDto compra = iCompra.buscar(numeroFactura);
		if (compra == null)
			throw new Exception("No se encontró la compra con factura N° " + numeroFactura);
		return compra;
	}

	public List<compraDto> listar() {
		return iCompra.listar();
	}

	public List<movimientoContableDto> listarMovimientos() {
		return iMovimiento.listar();
	}

	// --- Métodos Privados de Lógica ---

	private void calcularTotales(compraDto compra) {
		double subtotal = 0;
		for (itemCompraDto item : compra.getDetalles()) {
			double subItem = item.getCantidad() * item.getCostoUnitario();
			item.setSubtotal(subItem);
			subtotal += subItem;
		}
		compra.setSubtotal(subtotal);
		compra.setTotalCompra(subtotal + compra.getImpuestos());
	}

	private void actualizarInventario(compraDto compra, Connection conex) throws Exception {
		for (itemCompraDto item : compra.getDetalles()) {
			productoDto producto = iProducto.buscar(item.getCodigoProducto());
			if (producto != null) {
				producto.setStockActual(producto.getStockActual() + item.getCantidad());
				producto.setPrecioCompra(item.getCostoUnitario());
				// AQUÍ USAMOS LA CONEXIÓN DE LA TRANSACCIÓN
				((DatabaseProducto) iProducto).actualizarConConexion(producto, conex);
			}
		}
	}

	private void registrarEgresoContable(compraDto compra, Connection conex) throws Exception {
		movimientoContableDto movimiento = new movimientoContableDto();
		movimiento.setFecha(compra.getFecha());
		movimiento.setTipoMovimiento("EGRESO");
		movimiento.setCuentaContable("Cuentas por pagar - Proveedores");
		movimiento.setValor(compra.getTotalCompra());
		movimiento.setDescripcion("Compra factura N° " + compra.getNumeroFacturaProv());

		// AQUÍ USAMOS LA CONEXIÓN DE LA TRANSACCIÓN
		((DatabaseMovimientoContable) iMovimiento).guardar(movimiento, conex);
	}
}