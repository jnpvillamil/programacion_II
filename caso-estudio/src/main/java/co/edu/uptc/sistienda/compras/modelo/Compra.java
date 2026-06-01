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

    private String numeroFacturaProveedor;

    private String medioPago;

    private double valorRetencion;

    private boolean anulada;

    public Compra() {
        detalles = new ArrayList<>();
        fechaCompra = LocalDate.now();
        anulada = false;
        medioPago = "Contado";
    }

    public Compra(String numeroCompra, Proveedor proveedor) {
        this();
        this.numeroCompra = numeroCompra;
        this.proveedor = proveedor;
    }

    public void agregarDetalle(DetalleCompra detalle) {
        detalles.add(detalle);
        calcularTotalCompra();
    }

    public void quitarDetalle(int posicion) {
        if (posicion >= 0 && posicion < detalles.size()) {
            detalles.remove(posicion);
            calcularTotalCompra();
        }
    }

    public void calcularTotalCompra() {
        totalCompra = 0;
        for (DetalleCompra detalle : detalles) {
            totalCompra += detalle.getSubtotal();
        }
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
        calcularTotalCompra();
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public String getNumeroFacturaProveedor() {
        return numeroFacturaProveedor;
    }

    public void setNumeroFacturaProveedor(String numeroFacturaProveedor) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public double getValorRetencion() {
        return valorRetencion;
    }

    public void setValorRetencion(double valorRetencion) {
        this.valorRetencion = valorRetencion;
    }

    public boolean isAnulada() {
        return anulada;
    }

    public void setAnulada(boolean anulada) {
        this.anulada = anulada;
    }
}
