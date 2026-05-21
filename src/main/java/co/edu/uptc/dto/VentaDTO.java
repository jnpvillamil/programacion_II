package co.edu.uptc.dto;

import java.time.LocalDateTime;

public class VentaDTO {
    private String numeroFactura;
    private LocalDateTime fecha;
    private String nombreCliente;
    private double total;

    public VentaDTO(String numeroFactura, LocalDateTime fecha, String nombreCliente, double total) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.total = total;
    }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}