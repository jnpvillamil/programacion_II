package co.uptc.edu.tienda.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionProducto;
import co.uptc.edu.tienda.negocio.Producto;

public class LocalProducto implements IGestionProducto {

    private List<Producto> listaProductos;

    public LocalProducto() {
        this.listaProductos = new ArrayList<>();
    }

    @Override
    public void guardar(Producto producto) {
        listaProductos.add(producto);
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getCodigoProducto() == producto.getCodigoProducto()) {
                listaProductos.set(i, producto);
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigoProducto) {
        listaProductos.removeIf(p -> p.getCodigoProducto() == codigoProducto);
    }

    @Override
    public Producto buscar(int codigoProducto) {
        for (Producto p : listaProductos) {
            if (p.getCodigoProducto() == codigoProducto) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        return listaProductos;
    }
}
