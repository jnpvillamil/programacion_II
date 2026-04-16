package co.edu.uptc.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Transaccion implements IContabilizable {
    protected String numeroFactura;
    protected Date fecha;
    protected double total;
    protected double impuestos;
    protected List<DetalleTransaccion> detalles = new ArrayList<>();

    public Transaccion(String factura, Date fecha) { this.numeroFactura = factura; this.fecha = fecha; }
    
    public void agregarDetalle(DetalleTransaccion dt) { this.detalles.add(dt); }
    public List<DetalleTransaccion> getDetalles() { return detalles; }
    
    public double calcularTotal() {
        total = 0;
        for(DetalleTransaccion dt : detalles) total += dt.getSubtotal();
        return total;
    }
}