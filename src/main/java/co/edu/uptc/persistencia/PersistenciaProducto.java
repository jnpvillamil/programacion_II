package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProducto implements Repositorio<Producto> {
    private List<Producto> productos;

    public PersistenciaProducto() {
        this.productos = new ArrayList<>();
    }

    @Override
    public void guardar(Producto producto) {
        this.productos.add(producto);
    }

    @Override
    public void eliminar(String id) {
        this.productos.removeIf(p -> p.getCodigoInterno().equals(id));
    }

    @Override
    public List<Producto> listar() {
        return new ArrayList<>(this.productos);
    }

    @Override
    public Producto buscarPorId(String id) {
        for (Producto p : this.productos) {
            if (p.getCodigoInterno().equals(id)) {
                return p;
            }
        }
        return null;
    }
}
