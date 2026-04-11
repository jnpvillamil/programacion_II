package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Producto;

public class LocalProducto {

    private List<Producto> productos;

    public LocalProducto() {
        productos = new ArrayList<>();
    }

    public boolean guardarProducto(Producto producto) {
        if (buscarProducto(producto.getCodigoProducto()) != null) {
            return false;
        }
        productos.add(producto);
        return true;
    }

    public Producto buscarProducto(String codigoProducto) {
        for (Producto producto : productos) {
            if (producto.getCodigoProducto().equalsIgnoreCase(codigoProducto)) {
                return producto;
            }
        }
        return null;
    }

    public boolean actualizarProducto(Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigoProducto()
                    .equalsIgnoreCase(productoActualizado.getCodigoProducto())) {
                productos.set(i, productoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProducto(String codigoProducto) {
        Producto producto = buscarProducto(codigoProducto);
        if (producto != null) {
            productos.remove(producto);
            return true;
        }
        return false;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}