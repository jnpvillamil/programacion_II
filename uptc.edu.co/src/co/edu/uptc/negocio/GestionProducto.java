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

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public boolean actualizarProducto(String codigo, Producto productoEditado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(codigo)) {
                productos.set(i, productoEditado);
                return true;
            }
        }
        return false;
    }

    public void eliminarProducto(String codigo) {
        productos.removeIf(p -> p.getCodigo().equals(codigo));
    }
}