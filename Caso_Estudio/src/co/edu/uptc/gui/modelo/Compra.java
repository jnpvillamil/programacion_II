package co.edu.uptc.gui.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    private String numeroFacturaProveedor;
    private LocalDateTime fechaHora;
    private Proveedor proveedor;
    private List<DetalleCompra> detalles;
    private double total;
    private double impuestos;

    public Compra() {
        detalles = new ArrayList<>();
        fechaHora = LocalDateTime.now();
    }

    public String getNumeroFacturaProveedor() {
        return numeroFacturaProveedor;
    }

    public void setNumeroFacturaProveedor(String numeroFacturaProveedor) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompra> detalles) {
        this.detalles = detalles;
    }

    public double getTotal() {
        return total;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void agregarDetalle(DetalleCompra detalle) {
        detalles.add(detalle);
    }

    public double calcularTotalCompra() {
        double suma = 0;
        for (DetalleCompra detalle : detalles) {
            suma += detalle.getSubtotal();
        }
        this.impuestos = suma * 0.19;
        this.total = suma + impuestos;
        return total;
    }
}