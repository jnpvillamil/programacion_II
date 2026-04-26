package co.edu.uptc.negocio;

public class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private int stockMinimo;
    private double porcentajeIva;
    private boolean estado;
    
    public Producto(String codigo, String nombre, String categoria, double precioCompra, double precioVenta, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }
    public boolean tieneStockCritico() {
    	return this.stock <= this.stockMinimo; 
    }
    
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecioCompra() { return precioCompra; }
    public double getPrecioVenta() { return precioVenta; }
    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }
    public void reducirStock(int cantidad) { if (cantidad <= this.stock) this.stock -= cantidad; }
    public void aumentarStock(int cantidad) { this.stock += cantidad; }
}