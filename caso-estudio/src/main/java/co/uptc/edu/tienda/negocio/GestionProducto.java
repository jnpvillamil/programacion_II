package co.uptc.edu.tienda.negocio;

import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionProducto;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.persistencia.LocalProducto;

public class GestionProducto implements IGestionProducto {

    private LocalProducto persistencia = new LocalProducto();

    @Override
    public void guardar(Producto producto) {

        if (producto.getNombreProducto().isEmpty()) {
            throw new IllegalArgumentException("Nombre obligatorio");
        }

        if (producto.getPrecioCompra() <= 0 || producto.getPrecioVenta() <= 0) {
            throw new IllegalArgumentException("Precios deben ser mayores a 0");
        }

        if (producto.getStockActual() < 0) {
            throw new IllegalArgumentException("Stock no puede ser negativo");
        }

        persistencia.guardar(producto);
    }

    @Override
    public void actualizar(Producto producto) {
        persistencia.actualizar(producto);
    }

    @Override
    public void eliminar(int codigoProducto) {
        persistencia.eliminar(codigoProducto);
    }

    @Override
    public Producto buscar(int codigoProducto) {
        return persistencia.buscar(codigoProducto);
    }

    @Override
    public List<Producto> listar() {
        return persistencia.listar();
    }
}