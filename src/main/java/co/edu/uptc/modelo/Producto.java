package co.edu.uptc.modelo;

import co.edu.uptc.enums.Categoria;

public class Producto {
    private String nombre;
    private String codigoInterno;
    private double precioCompra;
    private double precioVenta;
    private int stockActual;
    private int stockMinimo;
    private int stockMaximo;
    private Categoria categoria;
    private boolean activo;

    public Producto(String nombre, String codigoInterno, double precioCompra, double precioVenta,
                    int stockActual, int stockMinimo, int stockMaximo, Categoria categoria) {
        this.nombre = nombre;
        this.codigoInterno = codigoInterno;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.categoria = categoria;
        this.activo = true;
    }

    public String getNombre() { return nombre; }
    public String getCodigoInterno() { return codigoInterno; }
    public double getPrecioCompra() { return precioCompra; }
    public double getPrecioVenta() { return precioVenta; }
    public int getStockActual() { return stockActual; }
    public int getStockMinimo() { return stockMinimo; }
    public int getStockMaximo() { return stockMaximo; }
    public Categoria getCategoria() { return categoria; }
    public boolean isActivo() { return activo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public void setStockActual(int stockActual) { this.stockActual = stockActual; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }
    public void setStockMaximo(int stockMaximo) { this.stockMaximo = stockMaximo; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
