package co.edu.uptc.negocio.dto;

import java.util.List;

public class compraDto {

    public static int contadorCompra = 500;

    private int numeroFacturaProveedor;
    private String fecha;
    private int codigoProveedor;
    private String razonSocialProveedor;
    private List<itemCompraDto> productos;
    private double subtotal;
    private double impuestos;
    private double total;

    public compraDto() {
        this.numeroFacturaProveedor = contadorCompra++;
    }

    public compraDto(int numeroFacturaProveedor) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
    }

    public int getNumeroFacturaProveedor() {
        return numeroFacturaProveedor;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }
    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }
    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

    public List<itemCompraDto> getProductos() {
        return productos;
    }
    public void setProductos(List<itemCompraDto> productos) {
        this.productos = productos;
    }

    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getImpuestos() {
        return impuestos;
    }
    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "compraDto [numeroFacturaProveedor=" + numeroFacturaProveedor
                + ", fecha=" + fecha
                + ", codigoProveedor=" + codigoProveedor
                + ", razonSocialProveedor=" + razonSocialProveedor
                + ", subtotal=" + subtotal
                + ", impuestos=" + impuestos
                + ", total=" + total + "]";
    }
}