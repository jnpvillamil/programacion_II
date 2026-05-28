package co.edu.uptc.tiendaminorista.modelo;

import co.edu.uptc.tiendaminorista.enums.CategoriaProducto;

public class Producto {
    private String codigo;
    private String nombre;
    private CategoriaProducto categoria;
    private double precioCompra;
    private double precioVenta;
    private int stockActual;
    private int stockMinimo;
    private boolean activo;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public CategoriaProducto getCategoria() {
        return categoria != null ? categoria : CategoriaProducto.OTROS;
    }
    public void setCategoria(CategoriaProducto categoria) { this.categoria = categoria; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getStockActual() { return stockActual; }
    public void setStockActual(int stockActual) { this.stockActual = stockActual; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre + " (Stock: " + stockActual + ")";
    }
}
