package co.edu.uptc.negocio;

public class DetalleTransaccion {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleTransaccion(Producto p, int cant, double precio) {
        this.producto = p; this.cantidad = cant; this.precioUnitario = precio;
        this.subtotal = calcularSubtotal();
    }
    public double calcularSubtotal() { return cantidad * precioUnitario; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return subtotal; }
}
