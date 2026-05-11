package co.edu.uptc.modelo;

import co.edu.uptc.enums.FormaPago;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private String numeroFactura; 
    private LocalDateTime fechaHora; 
    private Cliente cliente; 
    private List<DetalleVenta> listaDetalles; 
    private double total; 
    private FormaPago formaPago;

    public Venta(String numeroFactura, LocalDateTime fechaHora, Cliente cliente, FormaPago formaPago) {
        this.numeroFactura = numeroFactura;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.listaDetalles = new ArrayList<>();
        this.formaPago = formaPago;
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

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }
}