package co.uptc.edu.tienda.modelo;

import java.util.List;

import co.uptc.edu.tienda.enums.FormaPagoEnum;

public class Venta {

    // =====================================
    // ATRIBUTOS
    // =====================================

    private String numeroFactura;

    // CAMBIADO A STRING PARA JSON
    private String fechaHora;

    private Cliente cliente;

    private List<DetalleVenta> detalles;

    private FormaPagoEnum formaPago;

    private double impuestos;

    private double total;

    // =====================================
    // CONSTRUCTOR COMPLETO
    // =====================================

    public Venta(
            String numeroFactura,
            String fechaHora,
            Cliente cliente,
            List<DetalleVenta> detalles,
            FormaPagoEnum formaPago,
            double impuestos,
            double total) {

        this.numeroFactura = numeroFactura;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.detalles = detalles;
        this.formaPago = formaPago;
        this.impuestos = impuestos;
        this.total = total;
    }

    // =====================================
    // CONSTRUCTOR VACIO
    // =====================================

    public Venta() {

    }

    // =====================================
    // GETTERS Y SETTERS
    // =====================================

    public String getNumeroFactura() {

        return numeroFactura;
    }

    public void setNumeroFactura(
            String numeroFactura) {

        this.numeroFactura = numeroFactura;
    }

    public String getFechaHora() {

        return fechaHora;
    }

    public void setFechaHora(
            String fechaHora) {

        this.fechaHora = fechaHora;
    }

    public Cliente getCliente() {

        return cliente;
    }

    public void setCliente(
            Cliente cliente) {

        this.cliente = cliente;
    }

    public List<DetalleVenta> getDetalles() {

        return detalles;
    }

    public void setDetalles(
            List<DetalleVenta> detalles) {

        this.detalles = detalles;
    }

    public FormaPagoEnum getFormaPago() {

        return formaPago;
    }

    public void setFormaPago(
            FormaPagoEnum formaPago) {

        this.formaPago = formaPago;
    }

    public double getImpuestos() {

        return impuestos;
    }

    public void setImpuestos(
            double impuestos) {

        this.impuestos = impuestos;
    }

    public double getTotal() {

        return total;
    }

    public void setTotal(
            double total) {

        this.total = total;
    }
}