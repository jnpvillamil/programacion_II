package co.edu.uptc.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VentaDTO {
    
    private String numeroFactura;
    private LocalDateTime fecha;
    private String nombreCliente;
    private List<CarritoItemDTO> items;
    private double total;
    private String formaPago;

    public VentaDTO(String numeroFactura, LocalDateTime fecha, String nombreCliente, List<CarritoItemDTO> items, double total, String formaPago) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.items = items;
        this.total = total;
        this.formaPago = formaPago;
    }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public List<CarritoItemDTO> getItems() { return items; }
    public void setItems(List<CarritoItemDTO> items) { this.items = items; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getFormaPago() { return formaPago; }
    public void setFormaPago(String formaPago) { this.formaPago = formaPago; }
}