package co.edu.uptc.modelo;

import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.interfaces.Calculable;

import java.time.LocalDateTime;
import java.util.List;

public class Venta implements Calculable {
    private String numeroFactura;
    private LocalDateTime fechaHora;
    private Cliente cliente;
    private List<DetalleVenta> productosVendidos;
    private double subtotal;
    private double ivaAplicado;
    private double totalVenta;
    private FormaPago formaPago;

    public Venta(String numeroFactura, LocalDateTime fechaHora, Cliente cliente,
                 List<DetalleVenta> productosVendidos, double subtotal, double ivaAplicado,
                 double totalVenta, FormaPago formaPago) {
        this.numeroFactura = numeroFactura;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.productosVendidos = productosVendidos;
        this.subtotal = subtotal;
        this.ivaAplicado = ivaAplicado;
        this.totalVenta = totalVenta;
        this.formaPago = formaPago;
    }

    public Venta() {
    }

    @Override
    public double calcularSubtotal() {
        if (productosVendidos != null && !productosVendidos.isEmpty()) {
            return productosVendidos.stream()
                    .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                    .sum();
        }
        return subtotal;
    }

    @Override
    public double calcularIVA() {
        return calcularSubtotal() * 0.19;
    }

    @Override
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
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

    public List<DetalleVenta> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(List<DetalleVenta> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIvaAplicado() {
        return ivaAplicado;
    }

    public void setIvaAplicado(double ivaAplicado) {
        this.ivaAplicado = ivaAplicado;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }
}
