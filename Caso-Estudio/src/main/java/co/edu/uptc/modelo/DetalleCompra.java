package co.edu.uptc.modelo;

public class DetalleCompra {
    private Producto producto;
    private int cantidad;
    private double precioCompra;
    private double subtotal;
    
    public DetalleCompra(Producto producto, int cantidad, double precioCompra) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.subtotal = cantidad * precioCompra;
    }
    
   
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioCompra() { return precioCompra; }
    public double getSubtotal() { return subtotal; }
    
    
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
        this.subtotal = this.cantidad * this.precioCompra;
    }
}