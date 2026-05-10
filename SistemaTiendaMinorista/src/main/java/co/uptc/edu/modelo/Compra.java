package co.uptc.edu.modelo;

import java.util.ArrayList;
import java.util.List;

public class Compra {

    private String numeroFactura;
    private String fecha;
    private String proveedor;
    private List<DetalleCompra> detalles;
    private double subtotal;
    private double iva;
    private double total;

    public Compra(String numeroFactura, String fecha, String proveedor) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleCompra detalle) {
        detalles.add(detalle);
        calcularTotales();
    }

    private void calcularTotales() {
        subtotal = 0;

        for (DetalleCompra d : detalles) {
            subtotal += d.getSubtotal();
        }

        iva = subtotal * 0.19;
        total = subtotal + iva;
    }

    public String getNumeroFactura() { return numeroFactura; }
    public String getFecha() { return fecha; }
    public String getProveedor() { return proveedor; }
    public List<DetalleCompra> getDetalles() { return detalles; }
    public double getSubtotal() { return subtotal; }
    public double getIva() { return iva; }
    public double getTotal() { return total; }
}