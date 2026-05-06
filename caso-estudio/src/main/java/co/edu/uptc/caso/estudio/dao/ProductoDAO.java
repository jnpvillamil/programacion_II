package co.edu.uptc.caso.estudio.dao;

import co.edu.uptc.negocio.dto.productoDto;
import java.util.List;

public interface ProductoDAO {
    void guardarProductos(List<productoDto> productos, String rutaArchivo);
    List<productoDto> leerProductos(String rutaArchivo);
}