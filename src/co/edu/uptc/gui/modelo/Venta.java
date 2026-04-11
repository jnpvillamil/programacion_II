package co.edu.uptc.gui.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.enums.EstadoVentaEnum;
import co.edu.uptc.enums.FormaPagoEnum;

public class Venta {

    private String numeroFactura;
    private LocalDateTime fechaHora;
    private Cliente cliente;
    private FormaPagoEnum formaPago;
    private EstadoVentaEnum estado;
    private List<DetalleVenta> detalles;
    private double total;
    private double impuestos;

    public Venta() {
        fechaHora = LocalDateTime.now();
        detalles = new ArrayList<>();
        estado = EstadoVentaEnum.REGISTRADA;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public FormaPagoEnum getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPagoEnum formaPago) {
        this.formaPago = formaPago;
    }

    public EstadoVentaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoVentaEnum estado) {
        this.estado = estado;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public double getTotal() {
        return total;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
    }

    public double calcularTotalVenta() {
        double suma = 0;
        for (DetalleVenta detalle : detalles) {
            suma += detalle.getSubtotal();
        }
        this.impuestos = suma * 0.19;
        this.total = suma + impuestos;
        return total;
    }

    public void anularVenta() {
        this.estado = EstadoVentaEnum.ANULADA;
    }
}