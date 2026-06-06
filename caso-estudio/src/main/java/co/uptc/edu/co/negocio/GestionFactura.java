package co.uptc.edu.co.negocio;

import co.uptc.edu.co.interfaces.IGestionArchivoFactura;
import co.uptc.edu.co.interfaces.IGestionFactura;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.negocio.factura.FormateadorFactura;
import co.uptc.edu.co.negocio.factura.GeneradorComprobanteCompra;
import co.uptc.edu.co.negocio.factura.GeneradorFacturaVenta;

public class GestionFactura implements IGestionFactura {

    private final IGestionArchivoFactura gestionArchivoFactura;
    private final GeneradorFacturaVenta generadorFacturaVenta;
    private final GeneradorComprobanteCompra generadorComprobanteCompra;

    public GestionFactura(IGestionArchivoFactura gestionArchivoFactura) {
        if (gestionArchivoFactura == null) {
            throw new IllegalArgumentException("La gestionArchivoFactura no puede ser nula.");
        }

        FormateadorFactura formateadorFactura = new FormateadorFactura();
        this.gestionArchivoFactura = gestionArchivoFactura;
        this.generadorFacturaVenta = new GeneradorFacturaVenta(formateadorFactura);
        this.generadorComprobanteCompra = new GeneradorComprobanteCompra(formateadorFactura);
    }

    @Override
    public String generarFactura(Venta venta) throws Exception {
        String contenidoFactura = generadorFacturaVenta.generar(venta);
        return gestionArchivoFactura.guardarFactura(venta.getNumeroFactura(), contenidoFactura);
    }

    @Override
    public String generarFactura(Compra compra) throws Exception {
        String contenidoFactura = generadorComprobanteCompra.generar(compra);
        return gestionArchivoFactura.guardarFactura(compra.getNumeroFacturaProveedor(), contenidoFactura);
    }
}