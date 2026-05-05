package co.edu.uptc.negocio;

import java.util.ArrayList;

public class Venta {
	private String numeroFactura;
	private String formaPago;
	private double porcentajeDescuento;
	private boolean aplicaIva;
    private ArrayList<DetalleVenta> detalles;
    private double total;
    private String estadoVenta;

    private Cliente cliente;
    public Venta() {
        this.detalles = new ArrayList<>();
        this.total = 0;
    }

    public void agregarDetalle(Producto p, int cantidad) {
        DetalleVenta dv = new DetalleVenta(p, cantidad);
        detalles.add(dv);
        total += dv.getSubtotal();
        p.reducirStock(cantidad);
    }

    public double getTotal() { return total; }
}