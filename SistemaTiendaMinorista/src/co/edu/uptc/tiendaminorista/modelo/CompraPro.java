package co.edu.uptc.tiendaminorista.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompraPro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private LocalDate fecha;
    private String proveedor;
    private String producto;
    private int cantidad;
    private double precioUnitario;
    private double total;

    public CompraPro() {}

    public CompraPro(LocalDate fecha, String proveedor, String producto, int cantidad, double precioUnitario, double total) {
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public LocalDate getFecha() { return fecha; }
    public String getProveedor() { return proveedor; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getTotal() { return total; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public void setProducto(String producto) { this.producto = producto; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public void setTotal(double total) { this.total = total; }
    
    public String getFechaFormateada() {
        return fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }
}