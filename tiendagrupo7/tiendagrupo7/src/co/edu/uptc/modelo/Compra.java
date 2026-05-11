package co.edu.uptc.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Compra {
    private String facturaProveedor;
    private LocalDateTime fecha;
    private Proveedor proveedor;
    private List<DetalleVenta> productosComprados;
    private double totalCompra;
    private double iva;

    public Compra(String facturaProveedor, LocalDateTime fecha, Proveedor proveedor, List<DetalleVenta> productosComprados, double totalCompra, double iva) {
        this.facturaProveedor = facturaProveedor;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.productosComprados = productosComprados;
        this.totalCompra = totalCompra;
        this.iva = iva;
    }

    public Compra() {
    }

    public String getFacturaProveedor() {
        return facturaProveedor;
    }

    public void setFacturaProveedor(String facturaProveedor) {
        this.facturaProveedor = facturaProveedor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<DetalleVenta> getProductosComprados() {
        return productosComprados;
    }

    public void setProductosComprados(List<DetalleVenta> productosComprados) {
        this.productosComprados = productosComprados;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
}