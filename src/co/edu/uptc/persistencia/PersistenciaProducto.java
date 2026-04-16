package co.edu.uptc.persistencia;

import co.edu.uptc.negocio.Producto;
import java.util.ArrayList;

public class PersistenciaProducto {
    private ArrayList<Producto> listaProductos;

    public PersistenciaProducto() {
        this.listaProductos = new ArrayList<>();
    }

    public void crear(Producto producto) {
        this.listaProductos.add(producto);
    }

    public ArrayList<Producto> leerTodos() {
        return this.listaProductos;
    }

    public void actualizar(String codigo, Producto productoActualizado) {
        for (int i = 0; i < listaProductos.size(); i++) {
            // Asumiendo que Producto tiene getCodigoProducto()
            if (listaProductos.get(i).getCodigoProducto().equals(codigo)) {
                listaProductos.set(i, productoActualizado);
                break;
            }
        }
    }

    public void eliminar(String codigo) {
        listaProductos.removeIf(producto -> producto.getCodigoProducto().equals(codigo));
    }
}