package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.Producto;

public interface IGestionProducto {

    void guardar(Producto producto) throws Exception;

    void guardar(Connection conexion, Producto producto) throws Exception;

    void actualizar(Producto producto) throws Exception;

    void actualizar(Connection conexion, Producto producto) throws Exception;

    Producto buscar(String codigo) throws Exception;

    Producto buscar(Connection conexion, String codigo) throws Exception;

    List<Producto> listar() throws Exception;

    void cambiarEstado(String codigo) throws Exception;
}