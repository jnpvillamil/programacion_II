package co.uptc.edu.interfaces;

import java.sql.ResultSet;
import java.util.List;

import co.uptc.edu.modelo.Producto;

public interface IProductoDAO {

    boolean guardarProducto(Producto producto);

    List<Producto> obtenerProductos();

    boolean modificarProducto(Producto producto);

    boolean aumentarStock(String codigo, int cantidad);

    boolean descontarStock(String codigo, int cantidad);

    ResultSet obtenerStockBajoMinimo();
}