package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.interfaces.IGestionProducto;
import co.edu.uptc.negocio.dto.productoDto;

public class LocalProducto implements IGestionProducto {

    private List<productoDto> listaProductos = new ArrayList<>();

    @Override public void guardar(productoDto producto) { listaProductos.add(producto); }

    @Override
    public void actualizar(productoDto producto) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getCodigoProducto() == producto.getCodigoProducto()) {
                listaProductos.set(i, producto); return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        listaProductos.removeIf(p -> p.getCodigoProducto() == codigo);
    }

    @Override
    public productoDto buscar(int codigo) {
        for (productoDto p : listaProductos)
            if (p.getCodigoProducto() == codigo) return p;
        return null;
    }

    @Override public List<productoDto> listar() { return listaProductos; }
}
