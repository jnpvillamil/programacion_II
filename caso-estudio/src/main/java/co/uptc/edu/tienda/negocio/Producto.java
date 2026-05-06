package co.uptc.edu.tienda.negocio;

public class Producto {

    private int codigoProducto;
    private String nombreProducto;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int stockActual;
    private int stockMinimo;
    private boolean activo;

    public Producto(int codigoProducto, String nombreProducto, String categoria,
                    double precioCompra, double precioVenta,
                    int stockActual, int stockMinimo) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.activo = true;
    }

    public Producto() {
        this.codigoProducto = (int)(Math.random() * 1000);
        this.activo = true;
    }

    public Producto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
        this.activo = true;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}