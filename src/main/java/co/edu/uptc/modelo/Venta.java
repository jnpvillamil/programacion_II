package co.edu.uptc.modelo;

import co.edu.uptc.enums.EstadoVenta;
import co.edu.uptc.enums.FormaPago;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private String numeroFactura;
    private LocalDateTime fechaHora;
    private Cliente cliente;
    private List<DetalleVenta> listaDetalles;
    private double subtotal;
    private double iva;
    private double total;
    private FormaPago formaPago;
    private EstadoVenta estado;

    public Venta(Cliente cliente, FormaPago formaPago) {
        this.cliente = cliente;
        this.formaPago = formaPago;
        this.listaDetalles = new ArrayList<>();
        this.estado = EstadoVenta.ACTIVA;
        this.subtotal = 0;
        this.iva = 0;
        this.total = 0;
    }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<DetalleVenta> getListaDetalles() { return listaDetalles; }
    public void setListaDetalles(List<DetalleVenta> listaDetalles) { this.listaDetalles = listaDetalles; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public EstadoVenta getEstado() { return estado; }
    public void setEstado(EstadoVenta estado) { this.estado = estado; }
}
