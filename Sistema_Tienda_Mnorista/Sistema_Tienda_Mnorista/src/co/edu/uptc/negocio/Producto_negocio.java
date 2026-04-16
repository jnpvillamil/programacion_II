package co.edu.uptc.negocio;

import java.util.ArrayList;
import java.util.List;

public class Producto_negocio {
    
    private static Producto_negocio instance;
    private List<Producto> listaProductos;
    
    private Producto_negocio() {
        listaProductos = new ArrayList<>();
        cargarProductosIniciales();
    }
    
    public static Producto_negocio getInstance() {
        if (instance == null) {
            instance = new Producto_negocio();
        }
        return instance;
    }
    
    public void agregarProducto(Producto producto) {
        listaProductos.add(producto);
    }
    
    public List<Producto> getListaProductos() {
        return listaProductos;
    }
    
  
    public void eliminarProducto(String codigo) {
    }
    
    public Producto buscarProducto(String codigo) {
        return null;
    }
    
    private void cargarProductosIniciales() {
        Producto p1 = new Producto();
        p1.codigo = "001";
        p1.nombre = "Arroz";
        p1.categoria = "Granos";
        p1.precioCompra = 1000.0;
        p1.precioVenta = 2000.0;
        p1.stockActual = 20;
        p1.stockMinimo = 5;
        
        Producto p2 = new Producto();
        p2.codigo = "002";
        p2.nombre = "Leche";
        p2.categoria = "Lacteos";
        p2.precioCompra = 1500.0;
        p2.precioVenta = 2500.0;
        p2.stockActual = 15;
        p2.stockMinimo = 3;

        Producto p3 = new Producto();
        p3.codigo = "003";
        p3.nombre = "Pan";
        p3.categoria = "Panaderia";
        p3.precioCompra = 500.0;
        p3.precioVenta = 1000.0;
        p3.stockActual = 30;
        p3.stockMinimo = 10;

        listaProductos.add(p1);
        listaProductos.add(p2);
        listaProductos.add(p3);
    }
    
    public static class Producto {
        public String codigo;
        public String nombre;
        public String categoria;
        public double precioCompra;
        public double precioVenta;
        public int stockActual;
        public int stockMinimo;
        

        public Producto() {
            this.codigo = "";
            this.nombre = "";
            this.categoria = "";
            this.precioCompra = 0.0;
            this.precioVenta = 0.0;
            this.stockActual = 0;
            this.stockMinimo = 0;
        }
    }
}