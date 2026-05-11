package co.edu.uptc.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private String numeroFacturaProveedor; 
    private LocalDateTime fecha; 
    private Proveedor proveedor; 
    private List<DetalleCompra> listaDetalles; 
    private double total;

    public Compra(String numeroFacturaProveedor, LocalDateTime fecha, Proveedor proveedor) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.listaDetalles = new ArrayList<>();
        this.total = 0;
    }

    public String getNumeroFacturaProveedor() { return numeroFacturaProveedor; }
    public void setNumeroFacturaProveedor(String numeroFacturaProveedor) { this.numeroFacturaProveedor = numeroFacturaProveedor; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public List<DetalleCompra> getListaDetalles() { return listaDetalles; }
    public void setListaDetalles(List<DetalleCompra> listaDetalles) { this.listaDetalles = listaDetalles; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}