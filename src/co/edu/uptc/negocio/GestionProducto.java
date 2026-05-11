package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Producto;
import java.util.List;

public class GestionProducto {
    private Repositorio<Producto> persistenciaProducto;

    public GestionProducto(Repositorio<Producto> persistenciaProducto) {
        this.persistenciaProducto = persistenciaProducto;
    }

    public boolean guardarProducto(Producto producto) {
        if (producto.getStockMinimo() >= producto.getStockMaximo()) {
            return false;
        }
        this.persistenciaProducto.guardar(producto);
        return true;
    }

    public void actualizarProducto(Producto producto) {
        Producto existente = this.persistenciaProducto.buscarPorId(producto.getCodigoInterno());
        if (existente != null) {
            this.persistenciaProducto.eliminar(existente.getCodigoInterno());
            this.persistenciaProducto.guardar(producto);
        }
    }

    public void inactivarProducto(String codigoInterno) {
        Producto producto = this.persistenciaProducto.buscarPorId(codigoInterno);
        if (producto != null) {
            producto.setActivo(false);
            actualizarProducto(producto);
        }
    }

    public Producto buscarProducto(String codigoInterno) {
        return this.persistenciaProducto.buscarPorId(codigoInterno);
    }

    public List<Producto> listarProductos() {
        return this.persistenciaProducto.listar();
    }
}