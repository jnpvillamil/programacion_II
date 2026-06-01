package co.uptc.edu.co.negocio;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import co.uptc.edu.co.interfaces.IGestionFactura;
import co.uptc.edu.co.interfaces.dao.FacturaDAO;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;

public class GestionFactura implements IGestionFactura {

	private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();

	private FacturaDAO facturaDAO;

	public GestionFactura(FacturaDAO facturaDAO) {
		this.facturaDAO = facturaDAO;
	}

	private static DecimalFormat crearFormatoMoneda() {
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setGroupingSeparator('.');
		simbolos.setDecimalSeparator(',');

		DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
		formato.setGroupingUsed(true);
		return formato;
	}

	@Override
	public String generarFactura(Venta venta) throws Exception {
		validarVenta(venta);
		String contenidoFactura = construirContenidoFactura(venta);
		return facturaDAO.guardarFactura(venta.getNumeroFactura(), contenidoFactura);
	}

	@Override
	public String generarFactura(Compra compra) throws Exception {
		validarCompra(compra);
		String contenidoFactura = construirContenidoFactura(compra);
		return facturaDAO.guardarFactura(compra.getNumeroFacturaProveedor(), contenidoFactura);
	}

	private void validarVenta(Venta venta) throws Exception {
		if (venta == null) {
			throw new Exception("La venta no puede ser nula.");
		}

		if (venta.getNumeroFactura() == null || venta.getNumeroFactura().trim().isEmpty()) {
			throw new Exception("La venta no tiene numero de factura.");
		}

		if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
			throw new Exception("La venta no tiene productos para facturar.");
		}
	}

	private String construirContenidoFactura(Venta venta) {
		StringBuilder contenido = new StringBuilder();

		escribirEncabezado(contenido);
		escribirDatosVenta(contenido, venta);
		escribirDetalleProductos(contenido, venta);
		escribirTotales(contenido, venta);
		escribirPie(contenido);

		return contenido.toString();
	}

	private String construirContenidoFactura(Compra compra) {
		StringBuilder contenido = new StringBuilder();

		// Reutilizar encabezado y pie comunes con el mismo formato de venta
		escribirEncabezado(contenido);
		escribirDatosCompra(contenido, compra);
		escribirDetalleProductosCompra(contenido, compra);
		escribirTotalesCompra(contenido, compra);
		escribirPie(contenido);

		return contenido.toString();
	}

	private void escribirEncabezado(StringBuilder contenido) {
		contenido.append("========================================").append(System.lineSeparator());
		contenido.append("         TIENDA MINORISTA UPTC").append(System.lineSeparator());
		contenido.append("         NIT: 803.153.436-7").append(System.lineSeparator());
		contenido.append("         Calle 5 # 8-9, Bogota").append(System.lineSeparator());
		contenido.append("         Tel: 601-1234567").append(System.lineSeparator());
		contenido.append("========================================").append(System.lineSeparator());
	}

	private void escribirDatosVenta(StringBuilder contenido, Venta venta) {
		contenido.append("Factura No: ").append(venta.getNumeroFactura()).append(System.lineSeparator());
		contenido.append("Fecha:      ").append(venta.getFechaHora()).append(System.lineSeparator());
		contenido.append("Forma pago: ").append(venta.getFormaPago()).append(System.lineSeparator());
		contenido.append("----------------------------------------").append(System.lineSeparator());
		contenido.append("CLIENTE:").append(System.lineSeparator());
		contenido.append("  Nombre:    ").append(venta.getCliente()).append(System.lineSeparator());
		contenido.append("----------------------------------------").append(System.lineSeparator());
	}

	private void escribirDetalleProductos(StringBuilder contenido, Venta venta) {
		contenido.append(String.format("%-20s %5s %12s %12s%n", "Producto", "Cant.", "Precio", "Subtotal"));
		contenido.append("----------------------------------------").append(System.lineSeparator());

		for (DetalleVenta detalle : venta.getDetalles()) {
			Producto producto = detalle.getProducto();
			String nombreProducto = producto != null ? producto.getNombreProducto() : "Producto";

			contenido.append(String.format("%-20s %5d %12s %12s%n", nombreProducto, detalle.getCantidad(),
					formatearMoneda(detalle.getPrecioUnitario()), formatearMoneda(detalle.getSubtotal())));
		}

		contenido.append("----------------------------------------").append(System.lineSeparator());
	}

	private void escribirTotales(StringBuilder contenido, Venta venta) {
		contenido.append(String.format("%-30s %12s%n", "Subtotal:", formatearMoneda(venta.getSubTotal())));
		contenido.append(String.format("%-30s %12s%n", "IVA:", formatearMoneda(venta.getImpuestos())));
		contenido.append(String.format("%-30s %12s%n", "TOTAL:", formatearMoneda(venta.getTotal())));
	}

	private void escribirPie(StringBuilder contenido) {
		contenido.append("========================================").append(System.lineSeparator());
		contenido.append("       Gracias por su compra").append(System.lineSeparator());
		contenido.append("========================================").append(System.lineSeparator());
	}

	private void escribirDatosCompra(StringBuilder contenido, Compra compra) {
		contenido.append("Factura proveedor: ").append(compra.getNumeroFacturaProveedor())
				.append(System.lineSeparator());
		contenido.append("Fecha compra:      ").append(compra.getFecha()).append(System.lineSeparator());
		contenido.append("Proveedor:         ")
				.append(compra.getProveedor() != null ? compra.getProveedor() : compra.getCodigoProveedor())
				.append(System.lineSeparator());
		contenido.append("Forma de pago:     ").append(compra.getFormaPago()).append(System.lineSeparator());
		contenido.append("----------------------------------------").append(System.lineSeparator());
	}

	private void escribirDetalleProductosCompra(StringBuilder contenido, Compra compra) {
		contenido.append(String.format("%-20s %5s %12s %12s %12s%n", "Producto", "Cant.", "Costo", "IVA", "Subtotal"));
		contenido.append("----------------------------------------").append(System.lineSeparator());

		for (DetalleCompra detalle : compra.getDetalles()) {
			Producto producto = detalle.getProducto();
			String nombreProducto = producto != null ? producto.getNombreProducto() : "Producto";

			contenido.append(String.format("%-20s %5d %12s %12s %12s%n", nombreProducto, detalle.getCantidad(),
					formatearMoneda(detalle.getCostoUnitario()), formatearMoneda(detalle.getImpuestos()),
					formatearMoneda(detalle.getSubtotal())));
		}

		contenido.append("----------------------------------------").append(System.lineSeparator());
	}

	private void escribirTotalesCompra(StringBuilder contenido, Compra compra) {
		contenido.append(String.format("%-30s %12s%n", "Subtotal:", formatearMoneda(compra.getSubtotal())));
		contenido.append(String.format("%-30s %12s%n", "IVA:", formatearMoneda(compra.getImpuestos())));
		contenido.append(String.format("%-30s %12s%n", "TOTAL:", formatearMoneda(compra.getTotalCompra())));
	}

	private void validarCompra(Compra compra) throws Exception {
		if (compra == null) {
			throw new Exception("La compra no puede ser nula.");
		}

		if (compra.getNumeroFacturaProveedor() == null || compra.getNumeroFacturaProveedor().trim().isEmpty()) {
			throw new Exception("La compra no tiene número de factura.");
		}

		if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
			throw new Exception("La compra no tiene productos para facturar.");
		}

		if (compra.getFormaPago() == null) {
			throw new Exception("La compra no tiene forma de pago para facturar.");
		}
	}

	private String formatearMoneda(double valor) {
		return FORMATO_MONEDA.format(valor);
	}
}
