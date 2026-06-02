package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.RepositorioAdministracion;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.util.List;

public class GestionProducto {

    private final RepositorioAdministracion persistenciaAdministracion;

    public GestionProducto(RepositorioAdministracion persistenciaAdministracion) {
        this.persistenciaAdministracion = persistenciaAdministracion;
    }

    public boolean registrarProducto(Producto producto) throws Exception {
        if (persistenciaAdministracion.buscarProductoPorCodigo(producto.getCodigoInterno()) != null) {
            throw new Exception("El código del producto ya existe.");
        }
        if (producto.getStockMinimo() >= producto.getStockMaximo()) {
            throw new Exception("El stock mínimo no puede ser mayor o igual al stock máximo.");
        }
        persistenciaAdministracion.guardarProducto(producto);
        return true;
    }

    public boolean actualizarProducto(Producto productoModificado) throws Exception {
        if (productoModificado.getStockMinimo() >= productoModificado.getStockMaximo()) {
            throw new Exception("El stock mínimo no puede ser mayor o igual al stock máximo.");
        }
        Producto existente = persistenciaAdministracion.buscarProductoPorCodigo(productoModificado.getCodigoInterno());
        if (existente != null) {
            persistenciaAdministracion.eliminarProducto(existente.getCodigoInterno());
            persistenciaAdministracion.guardarProducto(productoModificado);
            return true;
        }
        return false;
    }

    public boolean inactivarProducto(String codigoInterno) {
        if (ValidadorEntradas.esNuloOVacio(codigoInterno)) {
            return false;
        }
        Producto producto = persistenciaAdministracion.buscarProductoPorCodigo(codigoInterno.trim());
        if (producto == null || !producto.isActivo()) {
            return false;
        }
        persistenciaAdministracion.inactivarProductoPorCodigo(codigoInterno.trim());
        return true;
    }

    public boolean activarProducto(String codigoInterno) {
        if (ValidadorEntradas.esNuloOVacio(codigoInterno)) {
            return false;
        }
        Producto producto = persistenciaAdministracion.buscarProductoPorCodigo(codigoInterno.trim());
        if (producto == null || producto.isActivo()) {
            return false;
        }
        persistenciaAdministracion.activarProductoPorCodigo(codigoInterno.trim());
        return true;
    }

    public Producto buscarProducto(String codigoInterno) {
        return persistenciaAdministracion.buscarProductoPorCodigo(codigoInterno);
    }

    public List<Producto> listarTodos() {
        return persistenciaAdministracion.listarProducto();
    }
}
