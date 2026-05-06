package co.uptc.edu.co.interfaces;

import java.util.List;
import co.uptc.edu.co.modelo.Producto;

public interface ProductoDAO {
	
	void guardarProductos(List<Producto> productos) throws Exception;

    List<Producto> leerProductos() throws Exception;

}
