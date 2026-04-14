package co.edu.uptc.gui.negocio;

import java.util.List;

import co.edu.uptc.gui.modelo.Producto;
import co.edu.uptc.persistencia.LocalProducto;

public class GestionProducto {

    private LocalProducto localProducto;

    public GestionProducto() {
        localProducto = new LocalProducto();
    }

    public boolean registrarProducto(Producto producto) {
        return localProducto.guardarProducto(producto);
    }

    public boolean modificarProducto(Producto producto) {
        return localProducto.actualizarProducto(producto);
    }

    public boolean eliminarProducto(String codigoProducto) {
        return localProducto.eliminarProducto(codigoProducto);
    }

    public Producto buscarProducto(String codigoProducto) {
        return localProducto.buscarProducto(codigoProducto);
    }

    public List<Producto> listarProductos() {
        return localProducto.getProductos();
    }
}