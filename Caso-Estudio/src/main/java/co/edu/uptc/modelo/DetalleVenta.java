package co.edu.uptc.modelo;

public class DetalleVenta {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    
    public DetalleVenta(Producto producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }
    
  
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
    
    
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
        this.subtotal = this.cantidad * this.precioUnitario;
    }
}