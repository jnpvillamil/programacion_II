package co.edu.uptc.sistienda.modelo;

import java.time.LocalDateTime;
import java.util.List;


//Representa una devolución parcial o total asociada a una venta existente.

public class Devolucion {

    private String numeroDevolucion;
    private String numeroFacturaOrigen; // se guarda por si la venta se anula después
    private Venta ventaOrigen;
    private List<DetalleDevolucion> detalles;
    private String motivo;
    private LocalDateTime fechaHora;
    private double totalReembolso;

    public Devolucion() {
        this.fechaHora = LocalDateTime.now();
    }

    public Devolucion(String numeroDevolucion, Venta ventaOrigen,
            List<DetalleDevolucion> detalles, String motivo) {
        this();
        this.numeroDevolucion = numeroDevolucion;
        this.ventaOrigen = ventaOrigen;
        this.numeroFacturaOrigen = ventaOrigen.getNumeroFactura();
        this.detalles = detalles;
        this.motivo = motivo;
        this.totalReembolso = calcularTotalReembolso();
    }

    // Suma el subtotal de reembolso de cada producto devuelto
    private double calcularTotalReembolso() {
        double total = 0;
        for (DetalleDevolucion detalle : detalles)
            total += detalle.getSubtotalReembolso();
        return total;
    }

    public String getNumeroDevolucion() {
        return numeroDevolucion;
    }

    public void setNumeroDevolucion(String numeroDevolucion) {
        this.numeroDevolucion = numeroDevolucion;
    }

    public String getNumeroFacturaOrigen() {
        return numeroFacturaOrigen;
    }

    public void setNumeroFacturaOrigen(String numeroFacturaOrigen) {
        this.numeroFacturaOrigen = numeroFacturaOrigen;
    }

    public Venta getVentaOrigen() {
        return ventaOrigen;
    }

    public void setVentaOrigen(Venta ventaOrigen) {
        this.ventaOrigen = ventaOrigen;
    }

    public List<DetalleDevolucion> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleDevolucion> detalles) {
        this.detalles = detalles;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getTotalReembolso() {
        return totalReembolso;
    }

    public void setTotalReembolso(double totalReembolso) {
        this.totalReembolso = totalReembolso;
    }
}
