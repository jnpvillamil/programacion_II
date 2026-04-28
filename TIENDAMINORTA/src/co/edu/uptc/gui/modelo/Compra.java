package co.edu.uptc.gui.modelo;

import java.time.LocalDateTime;

public class Compra {

    private String numeroFacturaProveedor;
    private Proveedor proveedor;
    private double total;
    private LocalDateTime fechaHora;

    public Compra() {
        this.fechaHora = LocalDateTime.now();
    }

    public String getNumeroFacturaProveedor() {
        return numeroFacturaProveedor;
    }

    public void setNumeroFacturaProveedor(String numeroFacturaProveedor) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public void calcularTotal() {
        this.total = this.total;
    }

}