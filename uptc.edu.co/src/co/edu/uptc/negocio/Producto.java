package co.edu.uptc.negocio;

public class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private int stockMinimo; 
    private int stockMaximo;
    private double porcentajeIva;
    private boolean estado;
    
    public Producto(String codigo, String nombre, String categoria, double precioCompra, double precioVenta, int stock, int stockMinimo, int stockMaximo, double porcentajeIva) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.porcentajeIva = porcentajeIva;
        this.estado = true;
    }

    public boolean tieneStockCritico() {
        return this.stock <= this.stockMinimo; 
    }

    public boolean tieneExcesoStock() {
        return this.stock > this.stockMaximo;
    }

    public void configurarLimitesStock(int min, int max) {
        this.stockMinimo = min;
        this.stockMaximo = max;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecioCompra() { return precioCompra; }
    public double getPrecioVenta() { return precioVenta; }
    public int getStock() { return stock; }
    public int getStockMinimo() { return stockMinimo; }
    public int getStockMaximo() { return stockMaximo; }
    public double getPorcentajeIva() { return porcentajeIva; }
    public boolean isEstado() { return estado; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public void setStock(int stock) { this.stock = stock; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }
    public void setStockMaximo(int stockMaximo) { this.stockMaximo = stockMaximo; }
    public void setPorcentajeIva(double porcentajeIva) { this.porcentajeIva = porcentajeIva; }
    public void setEstado(boolean estado) { this.estado = estado; }
    
    public void reducirStock(int cantidad) { 
        if (cantidad <= this.stock) {
            this.stock -= cantidad; 
        }
    }
    
    public void aumentarStock(int cantidad) { 
        this.stock += cantidad; 
    }
}
//Cambio final de validación