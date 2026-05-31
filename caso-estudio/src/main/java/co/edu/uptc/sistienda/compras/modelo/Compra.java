package co.edu.uptc.sistienda.compras.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.sistienda.modelo.Proveedor;

public class Compra {

    private String numeroCompra;

    private LocalDate fechaCompra;

    private Proveedor proveedor;

    private List<DetalleCompra> detalles;

    private double totalCompra;

    private boolean anulada;

    public Compra() {
        detalles = new ArrayList<>();
    }

    public String getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
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

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public boolean isAnulada() {
        return anulada;
    }

    public void setAnulada(boolean anulada) {
        this.anulada = anulada;
    }
}
