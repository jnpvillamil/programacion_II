package co.edu.uptc.caso.estudio.dao;

import co.edu.uptc.negocio.dto.movimientoContableDto;
import java.util.List;

public interface MovimientoContableDAO {
    void guardarMovimientos(List<movimientoContableDto> movimientos, String rutaArchivo);
    List<movimientoContableDto> leerMovimientos(String rutaArchivo);
}