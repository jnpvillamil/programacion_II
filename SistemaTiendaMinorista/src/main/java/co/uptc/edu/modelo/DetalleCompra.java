package co.uptc.edu.modelo;

public class DetalleCompra {

    private String codigoProducto;
    private String nombreProducto;
    private int cantidad;
    private double costoUnitario;
    private double subtotal;

    public DetalleCompra(String codigoProducto, String nombreProducto, int cantidad, double costoUnitario) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.subtotal = cantidad * costoUnitario;
    }

    public String getCodigoProducto() { return codigoProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getCostoUnitario() { return costoUnitario; }
    public double getSubtotal() { return subtotal; }
}