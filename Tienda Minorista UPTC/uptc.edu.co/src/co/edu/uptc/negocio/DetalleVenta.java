package co.edu.uptc.negocio;

public class DetalleVenta {
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecioVenta() * cantidad;
    }

    public double getSubtotal() { return subtotal; }
    public String getNombreProducto() { return producto.getNombre(); }
    public int getCantidad() { return cantidad; }
}