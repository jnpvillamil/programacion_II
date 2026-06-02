package co.edu.uptc.dto;

import java.time.LocalDateTime;

public class CompraDTO {

    private String numeroFacturaProveedor;
    private String nitProveedor;
    private LocalDateTime fecha;
    private double total;

    public CompraDTO(String numeroFacturaProveedor, String nitProveedor, LocalDateTime fecha, double total) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
        this.nitProveedor = nitProveedor;
        this.fecha = fecha;
        this.total = total;
    }

    public String getNumeroFacturaProveedor() {
        return numeroFacturaProveedor;
    }

    public String getNitProveedor() {
        return nitProveedor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }
}
