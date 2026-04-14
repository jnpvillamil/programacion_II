package co.edu.uptc.gui.modelo;

public class DetalleCompra {

    private Producto producto;
    private int cantidad;
    private double costoUnitario;
    private double subtotal;

    public DetalleCompra() {
    }

    public DetalleCompra(Producto producto, int cantidad, double costoUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.subtotal = cantidad * costoUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void calcularSubtotal() {
        this.subtotal = cantidad * costoUnitario;
    }
}