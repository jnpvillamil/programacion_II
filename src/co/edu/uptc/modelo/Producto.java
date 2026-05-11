package co.edu.uptc.modelo;

import co.edu.uptc.enums.CategoriaProducto;

public class Producto {
    private String codigoInterno; 
    private String nombreProducto; 
    private CategoriaProducto categoria; 
    private double precioCompra; 
    private double precioVenta; 
    private int stockActual; 
    private int stockMinimo;
    private int stockMaximo; 
    private boolean activo; 

    public Producto(String codigoInterno, String nombreProducto,
    		CategoriaProducto categoria, double precioCompra, double precioVenta, 
    		int stockActual, int stockMinimo, int stockMaximo, boolean activo) {
        this.codigoInterno = codigoInterno;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.activo = activo;
    }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public CategoriaProducto getCategoria() { return categoria; }
    public void setCategoria(CategoriaProducto categoria) { this.categoria = categoria; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getStockActual() { return stockActual; }
    public void setStockActual(int stockActual) { this.stockActual = stockActual; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public int getStockMaximo() { return stockMaximo; }
    public void setStockMaximo(int stockMaximo) { this.stockMaximo = stockMaximo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}