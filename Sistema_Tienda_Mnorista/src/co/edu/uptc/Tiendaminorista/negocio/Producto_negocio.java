package co.edu.uptc.Tiendaminorista.negocio;

import co.edu.uptc.Datos.Productodt;
import co.edu.uptc.persistencia.ProductoPersistencia;

import java.util.ArrayList;
import java.util.List;

public class Producto_negocio {

    private static Producto_negocio instance;
    private List<Productodt> listaProductos;
    private ProductoPersistencia persistencia;

    private Producto_negocio() {
        persistencia = new ProductoPersistencia();
        listaProductos = persistencia.cargarProductos();
        if (listaProductos == null) {
            listaProductos = new ArrayList<>();
        }
    }

    public static Producto_negocio getInstance() {
        if (instance == null) {
            instance = new Producto_negocio();
        }
        return instance;
    }

    public void agregarProducto(Productodt producto) {
        listaProductos.add(producto);
        persistencia.guardarProductos(listaProductos);
    }

    public List<Productodt> getListaProductos() {
        return listaProductos;
    }

    public boolean actualizarProducto(Productodt producto) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getCodigo().equals(producto.getCodigo())) {
                listaProductos.set(i, producto);
                persistencia.guardarProductos(listaProductos);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProducto(String codigo) {
        boolean eliminado = listaProductos.removeIf(p -> p.getCodigo().equals(codigo));
        if (eliminado) {
            persistencia.guardarProductos(listaProductos);
        }
        return eliminado;
    }

    public Productodt buscarProducto(String codigo) {
        for (Productodt p : listaProductos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public boolean existeCodigo(String codigo) {
        return buscarProducto(codigo) != null;
    }
}