package co.uptc.edu.co.negocio.factura;

import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.EstadoCompraEnum;

public class GeneradorComprobanteCompra {

	private final FormateadorFactura formateador;

	public GeneradorComprobanteCompra(FormateadorFactura formateador) {
		if (formateador == null) {
			throw new IllegalArgumentException("El formateador no puede ser nulo.");
		}
		this.formateador = formateador;
	}

	public String generar(Compra compra) throws Exception {
		validarCompra(compra);

		StringBuilder contenido = new StringBuilder();
		contenido.append(formateador.construirEncabezado());
		contenido.append(formateador.construirTitulo("FACTURA DE COMPRA"));
		escribirDatosCompra(contenido, compra);
		escribirDetalleProductosCompra(contenido, compra);
		escribirTotalesCompra(contenido, compra);
		escribirPie(contenido);
		return contenido.toString();
	}

	private void validarCompra(Compra compra) throws Exception {
		if (compra == null) {
			throw new Exception("La compra no puede ser nula.");
		}

		if (compra.getNumeroFacturaProveedor() == null || compra.getNumeroFacturaProveedor().trim().isEmpty()) {
			throw new Exception("La compra no tiene numero de factura.");
		}

		if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
			throw new Exception("La compra no tiene productos para facturar.");
		}

		if (compra.getFormaPago() == null) {
			throw new Exception("La compra no tiene forma de pago para facturar.");
		}

		if (compra.getEstado() == EstadoCompraEnum.ANULADA) {
			throw new Exception("No se puede generar factura de una compra anulada.");
		}
	}

	private void escribirDatosCompra(StringBuilder contenido, Compra compra) {
		contenido.append("DATOS DE LA COMPRA").append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Factura proveedor: ").append(compra.getNumeroFacturaProveedor())
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Fecha compra:      ").append(formateador.formatearFecha(compra.getFecha()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Forma de pago:     ").append(formateador.valorSeguro(compra.getFormaPago()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Estado:            ").append(formateador.valorSeguro(compra.getEstado()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append(FormateadorFactura.LINEA_CORTA).append(FormateadorFactura.SALTO_LINEA);
		contenido.append("DATOS DEL PROVEEDOR").append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Codigo proveedor:  ").append(formateador.valorSeguro(compra.getCodigoProveedor()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Proveedor:         ")
				.append(compra.getProveedor() != null ? compra.getProveedor()
						: formateador.valorSeguro(compra.getCodigoProveedor()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append(FormateadorFactura.LINEA_CORTA).append(FormateadorFactura.SALTO_LINEA);
	}

	private void escribirDetalleProductosCompra(StringBuilder contenido, Compra compra) {
		contenido.append("DETALLE DE PRODUCTOS").append(FormateadorFactura.SALTO_LINEA);
		contenido.append(String.format("%-3s %-9s %-18s %4s %12s %12s %12s %12s%n", "No", "Codigo", "Descripcion",
				"Cant", "Costo Unit.", "Subtotal", "IVA", "Total"));
		contenido.append(FormateadorFactura.LINEA_CORTA).append(FormateadorFactura.SALTO_LINEA);

		int numeroItem = 1;
		for (DetalleCompra detalle : compra.getDetalles()) {
			Producto producto = detalle.getProducto();
			String codigoProducto = producto != null ? formateador.valorSeguro(producto.getCodigoProducto()) : "";
			String nombreProducto = producto != null ? formateador.valorSeguro(producto.getNombreProducto()) : "Producto";

			contenido.append(String.format("%-3d %-9s %-18s %4d %12s %12s %12s %12s%n", numeroItem++,
					formateador.limitarTexto(codigoProducto, 9), formateador.limitarTexto(nombreProducto, 18),
					detalle.getCantidad(), formateador.formatearMoneda(detalle.getCostoUnitario()),
					formateador.formatearMoneda(detalle.getSubtotal()), formateador.formatearMoneda(detalle.getImpuestos()),
					formateador.formatearMoneda(detalle.getTotalCompra())));
		}

		contenido.append(FormateadorFactura.LINEA_CORTA).append(FormateadorFactura.SALTO_LINEA);
	}

	private void escribirTotalesCompra(StringBuilder contenido, Compra compra) {
		contenido.append(String.format("%60s %17s%n", "SUBTOTAL:",
				formateador.formatearMoneda(compra.getSubtotal())));
		contenido.append(String.format("%60s %17s%n", "IVA:", formateador.formatearMoneda(compra.getImpuestos())));
		contenido.append(String.format("%60s %17s%n", "TOTAL COMPRA:",
				formateador.formatearMoneda(compra.getTotalCompra())));
	}

	private void escribirPie(StringBuilder contenido) {
		contenido.append(FormateadorFactura.LINEA).append(FormateadorFactura.SALTO_LINEA);
		contenido.append(formateador.centrarTexto("Compra registrada a proveedor")).append(FormateadorFactura.SALTO_LINEA);
		contenido.append(FormateadorFactura.LINEA).append(FormateadorFactura.SALTO_LINEA);
	}
}
