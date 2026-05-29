package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.Producto;

public interface ProductoDAO {
	
    void guardarProducto(Producto producto) throws Exception;

    void actualizarProducto(Producto producto) throws Exception;

    void actualizarProducto(Connection conexion, Producto producto) throws Exception;

    Producto buscarPorCodigo(String codigo) throws Exception;

    Producto buscarPorCodigo(Connection conexion, String codigo) throws Exception;

    List<Producto> listarProducto() throws Exception;
}
