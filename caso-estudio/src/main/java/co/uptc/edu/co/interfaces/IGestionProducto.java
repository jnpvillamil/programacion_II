package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.MovimientoInventario;
import co.uptc.edu.co.modelo.Producto;

public interface IGestionProducto {

    Producto buscarProductoPorCodigo(String codigo);

    List<Producto> obtenerProductos();

    List<MovimientoInventario> obtenerMovimientos();

    void registrarProducto(Producto producto) throws Exception;

    void actualizarProducto(Producto productoActualizado) throws Exception;

    void cambiarEstadoProducto(String codigo) throws Exception;

    void actualizarPrecioProducto(String codigo, Double nuevoPrecioCompra, Double nuevoPrecioVenta) throws Exception;

    void registrarMovimientoInventario(String codigo, String tipoMovimiento, int cantidad) throws Exception;
    
  
    
    
}