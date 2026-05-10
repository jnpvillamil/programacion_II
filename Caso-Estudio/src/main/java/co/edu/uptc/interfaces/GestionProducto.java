package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Producto;
import java.util.List;

public interface GestionProducto {
    void crear(Producto p);
    void actualizar(Producto p);
    void eliminar(String codigo);  
    Producto buscar(String codigo);
    List<Producto> listar();
    List<Producto> listarActivos();
    List<Producto> listarPorStockMinimo();
    boolean existe(String codigo);
}