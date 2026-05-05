package co.edu.uptc.negocio;

import java.util.ArrayList;

public class GestionProducto {
    private ArrayList<Producto> productos;

    public GestionProducto() {
        this.productos = new ArrayList<>();
    }

    public void registrarProducto(Producto p) {
        productos.add(p);
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) return p;
        }
        return null;
    }

    public void eliminarProducto(String codigo) {
        productos.removeIf(p -> p.getCodigo().equals(codigo));
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}