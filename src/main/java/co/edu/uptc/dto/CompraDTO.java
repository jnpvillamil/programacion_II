package co.edu.uptc.dto;

import java.time.LocalDateTime;

public class CompraDTO {
    private String numeroFacturaProveedor;
    private LocalDateTime fecha;
    private double total;

    public CompraDTO(String numeroFacturaProveedor, LocalDateTime fecha, double total) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
        this.fecha = fecha;
        this.total = total;
    }

    public String getNumeroFacturaProveedor() { return numeroFacturaProveedor; }
    public void setNumeroFacturaProveedor(String numeroFacturaProveedor) { this.numeroFacturaProveedor = numeroFacturaProveedor; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}