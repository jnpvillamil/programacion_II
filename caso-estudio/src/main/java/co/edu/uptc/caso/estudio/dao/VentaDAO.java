package co.edu.uptc.caso.estudio.dao;

import co.edu.uptc.negocio.dto.ventaDto;
import java.util.List;

public interface VentaDAO {
    void guardarVentas(List<ventaDto> ventas, String rutaArchivo);
    List<ventaDto> leerVentas(String rutaArchivo);
}