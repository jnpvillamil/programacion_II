package co.edu.uptc.gui.modelo;

import co.edu.uptc.gui.interfaces.Gestionable;
import co.edu.uptc.dao.ProductoDao;

public class Producto implements Gestionable {
    private String codigo;
    private String nombre;
    private double precio;
    private int cantidadInventario;

    public Producto() {}

    public Producto(String codigo, String nombre, double precio, int cantidadInventario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadInventario = cantidadInventario;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidadInventario() { return cantidadInventario; }
    public void setCantidadInventario(int cantidadInventario) { this.cantidadInventario = cantidadInventario; }

    @Override
    public void registrar() {
        ProductoDao dao = new ProductoDao();
        dao.registrarProducto(this);
    }

    @Override
    public void modificar() {
        ProductoDao dao = new ProductoDao();
        dao.actualizarProducto(this);
    }

    @Override
    public void inactivar() {
        System.out.println("Producto descatalogado");
    }
}