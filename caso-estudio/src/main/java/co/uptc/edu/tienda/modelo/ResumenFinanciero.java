package co.uptc.edu.tienda.modelo;

import java.util.List;

public class ResumenFinanciero {

    private String fechaInicio;
    private String fechaFin;
    private double totalVentas;
    private double totalCompras;
    private double utilidadBruta;
    private List<VentaPorFormaPago> ventasPorFormaPago;
    private List<ProductoMasVendido> productosMasVendidos;
    private List<ClienteVolumen> clientesMayorVolumen;
    private double inventarioValorizado;
    private ResumenContable resumenContable;

    public ResumenFinanciero() {
    }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public double getTotalVentas() { return totalVentas; }
    public void setTotalVentas(double totalVentas) { this.totalVentas = totalVentas; }

    public double getTotalCompras() { return totalCompras; }
    public void setTotalCompras(double totalCompras) { this.totalCompras = totalCompras; }

    public double getUtilidadBruta() { return utilidadBruta; }
    public void setUtilidadBruta(double utilidadBruta) { this.utilidadBruta = utilidadBruta; }

    public List<VentaPorFormaPago> getVentasPorFormaPago() { return ventasPorFormaPago; }
    public void setVentasPorFormaPago(List<VentaPorFormaPago> ventasPorFormaPago) { this.ventasPorFormaPago = ventasPorFormaPago; }

    public List<ProductoMasVendido> getProductosMasVendidos() { return productosMasVendidos; }
    public void setProductosMasVendidos(List<ProductoMasVendido> productosMasVendidos) { this.productosMasVendidos = productosMasVendidos; }

    public List<ClienteVolumen> getClientesMayorVolumen() { return clientesMayorVolumen; }
    public void setClientesMayorVolumen(List<ClienteVolumen> clientesMayorVolumen) { this.clientesMayorVolumen = clientesMayorVolumen; }

    public double getInventarioValorizado() { return inventarioValorizado; }
    public void setInventarioValorizado(double inventarioValorizado) { this.inventarioValorizado = inventarioValorizado; }

    public ResumenContable getResumenContable() { return resumenContable; }
    public void setResumenContable(ResumenContable resumenContable) { this.resumenContable = resumenContable; }
}