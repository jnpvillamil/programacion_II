package modelo;

public class Factura {

    private String codigoFactura;
    private String codigoVenta;
    private String fecha;
    private double subtotal;
    private double iva;
    private double total;
    private String metodoPago;

    public Factura(String codigoFactura, String codigoVenta, String fecha,
                   double subtotal, double iva, double total, String metodoPago) {

        this.codigoFactura = codigoFactura;
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.metodoPago = metodoPago;
    }

    public String getCodigoFactura() {
        return codigoFactura;
    }

    public double getTotal() {
        return total;
    }
}