package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.CategoriaProductoEnum;
import co.edu.uptc.gui.interfaces.Gestionable;

public class Producto implements Gestionable {

    private String codigoProducto;
    private String nombreProducto;
    private CategoriaProductoEnum categoria;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private int stockMinimo;
    private boolean activo;

    public Producto() {
        this.activo = true;
    }

    public Producto(String codigoProducto, String nombreProducto, CategoriaProductoEnum categoria,
                    double precioCompra, double precioVenta, int stock, int stockMinimo, boolean activo) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public CategoriaProductoEnum getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProductoEnum categoria) {
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    @Override
    public void registrar() {
        this.activo = true;
    }

    @Override
    public void modificar() {
        System.out.println("Producto modificado");
    }

    @Override
    public void inactivar() {
        this.activo = false;
    }
}