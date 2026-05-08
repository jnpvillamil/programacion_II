package co.uptc.edu.co.interfaces;

import java.util.List;
import co.uptc.edu.co.modelo.Producto;

public interface ProductoDAO {
	
    void guardarProducto(Producto producto) throws Exception;

    void actualizarProducto(Producto producto) throws Exception;

    Producto buscarPorCodigo(String codigo) throws Exception;

    List<Producto> listarProducto() throws Exception;


}
