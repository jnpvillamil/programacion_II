package co.uptc.edu.tienda.modelo;

import java.io.Serializable;

public class DetalleCompra implements Serializable {

    private Producto producto;

    private int cantidad;

    private double precioCompra;

    private double subtotal;

    public DetalleCompra() {
    }

    public DetalleCompra(Producto producto,
                         int cantidad) {

        this.producto = producto;

        this.cantidad = cantidad;

        this.precioCompra =
                producto.getPrecioCompra();

        calcularSubtotal();
    }

    // =====================================
    // CALCULAR SUBTOTAL
    // =====================================

    public void calcularSubtotal() {

        subtotal =
                cantidad * precioCompra;
    }

    // =====================================
    // GETTERS Y SETTERS
    // =====================================

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

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {

        this.precioCompra = precioCompra;

        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {

        return "DetalleCompra{" +
                "producto=" + producto.getNombreProducto() +
                ", cantidad=" + cantidad +
                ", precioCompra=" + precioCompra +
                ", subtotal=" + subtotal +
                '}';
    }
}