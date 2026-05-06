package co.edu.uptc.caso.estudio.dao;

import co.edu.uptc.negocio.dto.proveedorDto;
import java.util.List;

public interface ProveedorDAO {
    void guardarProveedores(List<proveedorDto> proveedores, String rutaArchivo);
    List<proveedorDto> leerProveedores(String rutaArchivo);
}