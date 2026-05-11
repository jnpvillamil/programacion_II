package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.GestionProducto;
import java.util.List;

public class ControladorProducto {
    private GestionProducto gestionProducto;

    public ControladorProducto(GestionProducto gestionProducto) {
        this.gestionProducto = gestionProducto;
    }

    public boolean guardarProducto(Producto producto) {
        return this.gestionProducto.guardarProducto(producto);
    }

    public void actualizarProducto(Producto producto) {
        this.gestionProducto.actualizarProducto(producto);
    }

    public void inactivarProducto(String codigoInterno) {
        this.gestionProducto.inactivarProducto(codigoInterno);
    }

    public Producto buscarProducto(String codigoInterno) {
        return this.gestionProducto.buscarProducto(codigoInterno);
    }

    public List<Producto> listarProductos() {
        return this.gestionProducto.listarProductos();
    }
}