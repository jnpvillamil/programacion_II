package co.edu.uptc.dto;

import co.edu.uptc.enums.EstadoVenta;
import co.edu.uptc.enums.FormaPago;

import java.time.LocalDateTime;

public class VentaDTO {
    private String numeroFactura;
    private LocalDateTime fecha;
    private String identificacionCliente;
    private String nombreCliente;
    private double subtotal;
    private double iva;
    private double total;
    private FormaPago formaPago;
    private EstadoVenta estado;

    public VentaDTO(String numeroFactura, LocalDateTime fecha, String identificacionCliente,
                    String nombreCliente, double subtotal, double iva, double total,
                    FormaPago formaPago, EstadoVenta estado) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.identificacionCliente = identificacionCliente;
        this.nombreCliente = nombreCliente;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.formaPago = formaPago;
        this.estado = estado;
    }

    public String getNumeroFactura() { return numeroFactura; }
    public LocalDateTime getFecha() { return fecha; }
    public String getIdentificacionCliente() { return identificacionCliente; }
    public String getNombreCliente() { return nombreCliente; }
    public double getSubtotal() { return subtotal; }
    public double getIva() { return iva; }
    public double getTotal() { return total; }
    public FormaPago getFormaPago() { return formaPago; }
    public EstadoVenta getEstado() { return estado; }
}
