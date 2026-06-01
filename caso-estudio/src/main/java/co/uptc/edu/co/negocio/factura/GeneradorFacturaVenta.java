package co.uptc.edu.co.negocio.factura;

import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;

public class GeneradorFacturaVenta {

	private static final double IVA_VENTA = 0.19;

	private final FormateadorFactura formateador;

	public GeneradorFacturaVenta(FormateadorFactura formateador) {
		if (formateador == null) {
			throw new IllegalArgumentException("El formateador no puede ser nulo.");
		}
		this.formateador = formateador;
	}

	public String generar(Venta venta) throws Exception {
		validarVenta(venta);

		StringBuilder contenido = new StringBuilder();
		contenido.append(formateador.construirEncabezado());
		contenido.append(formateador.construirTitulo("FACTURA DE VENTA"));
		escribirDatosVenta(contenido, venta);
		escribirDetalleProductos(contenido, venta);
		escribirTotales(contenido, venta);
		escribirPie(contenido);
		return contenido.toString();
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

		if (venta.getEstado() == EstadoVentaEnum.ANULADA) {
			throw new Exception("No se puede generar factura de una venta anulada.");
		}
	}

	private void escribirDatosVenta(StringBuilder contenido, Venta venta) {
		contenido.append("Factura No:      ").append(venta.getNumeroFactura()).append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Fecha creacion:  ").append(formateador.formatearFechaHora(venta.getFechaHora()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Forma de pago:   ").append(formateador.valorSeguro(venta.getFormaPago()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Estado:          ").append(formateador.valorSeguro(venta.getEstado()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append(FormateadorFactura.LINEA_CORTA).append(FormateadorFactura.SALTO_LINEA);
		contenido.append("DATOS DEL CLIENTE").append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Codigo cliente:  ").append(formateador.valorSeguro(venta.getCodigoCliente()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append("Cliente:         ").append(formateador.valorSeguro(venta.getCliente()))
				.append(FormateadorFactura.SALTO_LINEA);
		contenido.append(FormateadorFactura.LINEA_CORTA).append(FormateadorFactura.SALTO_LINEA);
	}

	private void escribirDetalleProductos(StringBuilder contenido, Venta venta) {
		contenido.append("DETALLE DE PRODUCTOS").append(FormateadorFactura.SALTO_LINEA);
		contenido.append(String.format("%-3s %-10s %-22s %5s %13s %13s %13s%n", "No", "Codigo",
				"Descripcion", "Cant", "Precio Unit.", "IVA", "Subtotal"));
		contenido.append(FormateadorFactura.LINEA_CORTA).append(FormateadorFactura.SALTO_LINEA);

		int numeroItem = 1;
		for (DetalleVenta detalle : venta.getDetalles()) {
			Producto producto = detalle.getProducto();
			String codigoProducto = producto != null ? formateador.valorSeguro(producto.getCodigoProducto()) : "";
			String nombreProducto = producto != null ? formateador.valorSeguro(producto.getNombreProducto()) : "Producto";
			double ivaDetalle = calcularIvaDetalleVenta(detalle);

			contenido.append(String.format("%-3d %-10s %-22s %5d %13s %13s %13s%n", numeroItem++,
					formateador.limitarTexto(codigoProducto, 10), formateador.limitarTexto(nombreProducto, 22),
					detalle.getCantidad(), formateador.formatearMoneda(detalle.getPrecioUnitario()),
					formateador.formatearMoneda(ivaDetalle), formateador.formatearMoneda(detalle.getSubtotal())));
		}

		contenido.append(FormateadorFactura.LINEA_CORTA).append(FormateadorFactura.SALTO_LINEA);
	}

	private void escribirTotales(StringBuilder contenido, Venta venta) {
		contenido.append(String.format("%60s %17s%n", "SUBTOTAL:", formateador.formatearMoneda(venta.getSubTotal())));
		contenido.append(String.format("%60s %17s%n", "IVA:", formateador.formatearMoneda(venta.getImpuestos())));
		contenido.append(String.format("%60s %17s%n", "TOTAL:", formateador.formatearMoneda(venta.getTotal())));
	}

	private void escribirPie(StringBuilder contenido) {
		contenido.append(FormateadorFactura.LINEA).append(FormateadorFactura.SALTO_LINEA);
		contenido.append(formateador.centrarTexto("Gracias por su compra")).append(FormateadorFactura.SALTO_LINEA);
		contenido.append(FormateadorFactura.LINEA).append(FormateadorFactura.SALTO_LINEA);
	}

	private double calcularIvaDetalleVenta(DetalleVenta detalle) {
		if (detalle == null || detalle.getProducto() == null || !detalle.getProducto().isAplicaIva()) {
			return 0;
		}
		return detalle.getSubtotal() * IVA_VENTA;
	}
}
