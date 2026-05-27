package co.edu.uptc.dto;

import java.time.LocalDateTime;

public class CompraDTO {
    private String numeroFacturaProveedor;
    private LocalDateTime fecha;
    private String nombreProveedor;
    private double subtotal;
    private double iva;
    private double total;

    public CompraDTO(String numeroFacturaProveedor, LocalDateTime fecha, String nombreProveedor,
                     double subtotal, double iva, double total) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
        this.fecha = fecha;
        this.nombreProveedor = nombreProveedor;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
    }

    public String getNumeroFacturaProveedor() { return numeroFacturaProveedor; }
    public void setNumeroFacturaProveedor(String numeroFacturaProveedor) { this.numeroFacturaProveedor = numeroFacturaProveedor; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}