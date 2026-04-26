package co.edu.uptc.negocio;

import java.util.ArrayList;

public class Compra {
    private ArrayList<DetalleCompra> detalles;
    private double total;
    private String numFacturaProveedor;
    private double impuestosAplicados;
    
    public Compra() {
        this.detalles = new ArrayList<>();
        this.total = 0;
    }
    private Proveedor proveedor;
    public void agregarDetalle(Producto p, int cantidad) {
        DetalleCompra dc = new DetalleCompra(p, cantidad);
        detalles.add(dc);
        total += dc.getCostoSubtotal();
        p.aumentarStock(cantidad);
    }

    public double getTotal() { return total; }
}