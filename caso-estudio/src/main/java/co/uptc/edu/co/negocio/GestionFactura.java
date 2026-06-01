package co.uptc.edu.co.negocio;

import co.uptc.edu.co.interfaces.IGestionFactura;
import co.uptc.edu.co.interfaces.dao.FacturaDAO;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.negocio.factura.FormateadorFactura;
import co.uptc.edu.co.negocio.factura.GeneradorComprobanteCompra;
import co.uptc.edu.co.negocio.factura.GeneradorFacturaVenta;

public class GestionFactura implements IGestionFactura {

	private final FacturaDAO facturaDAO;
	private final GeneradorFacturaVenta generadorFacturaVenta;
	private final GeneradorComprobanteCompra generadorComprobanteCompra;

	public GestionFactura(FacturaDAO facturaDAO) {
		if (facturaDAO == null) {
			throw new IllegalArgumentException("El facturaDAO no puede ser nulo.");
		}

		FormateadorFactura formateadorFactura = new FormateadorFactura();
		this.facturaDAO = facturaDAO;
		this.generadorFacturaVenta = new GeneradorFacturaVenta(formateadorFactura);
		this.generadorComprobanteCompra = new GeneradorComprobanteCompra(formateadorFactura);
	}

	@Override
	public String generarFactura(Venta venta) throws Exception {
		String contenidoFactura = generadorFacturaVenta.generar(venta);
		return facturaDAO.guardarFactura(venta.getNumeroFactura(), contenidoFactura);
	}

	@Override
	public String generarFactura(Compra compra) throws Exception {
		String contenidoFactura = generadorComprobanteCompra.generar(compra);
		return facturaDAO.guardarFactura(compra.getNumeroFacturaProveedor(), contenidoFactura);
	}
}
