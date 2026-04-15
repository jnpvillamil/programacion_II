package modelo;

public class Producto {

    private String codigo;
    private String nombre;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int stockActual;
    private int stockMinimo;
    private boolean activo;

    public Producto(String codigo, String nombre, String categoria,
                    double precioCompra, double precioVenta,
                    int stockActual, int stockMinimo) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.activo = true;
    }

    

    public String getCodigo() {return codigo; }

    public String getNombre() {return nombre;}

    public String getCategoria() {return categoria;}

    public double getPrecioCompra() {return precioCompra;}

    public double getPrecioVenta() {return precioVenta;}

    public int getStockActual() {return stockActual;}

    public int getStockMinimo() {return stockMinimo;}

    public boolean isActivo() {return activo;}

   

    public void setNombre(String nombre) {this.nombre = nombre;}

    public void setCategoria(String categoria) {this.categoria = categoria;}

    public void setPrecioCompra(double precioCompra) {this.precioCompra = precioCompra;}

    public void setPrecioVenta(double precioVenta) {this.precioVenta = precioVenta;}

    public void setStockActual(int stockActual) {this.stockActual = stockActual;}

    public void setStockMinimo(int stockMinimo) {this.stockMinimo = stockMinimo;}

    public void setActivo(boolean activo) { this.activo = activo;}
}
