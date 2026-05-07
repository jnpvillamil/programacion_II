package co.uptc.edu.tienda.interfaces;

import java.util.List;

import co.uptc.edu.tienda.modelo.Producto;

public interface IGestionProducto {

    void guardar(Producto producto);

    void actualizar(Producto producto);

    void eliminar(int codigoProducto);

    Producto buscar(int codigoProducto);

    List<Producto> listar();
}