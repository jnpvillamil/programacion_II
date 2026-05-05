package co.edu.uptc.negocio;

public class DetalleCompra {
    private Producto producto;
    private int cantidad;
    private double costoSubtotal;

    public DetalleCompra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.costoSubtotal = producto.getPrecioCompra() * cantidad;
    }

    public double getCostoSubtotal() { return costoSubtotal; }
}