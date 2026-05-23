package co.edu.uptc.caso.estudio.dao;

import co.edu.uptc.negocio.dto.compraDto;
import java.util.List;

public interface CompraDAO {
    void guardarCompras(List<compraDto> compras, String rutaArchivo);
    List<compraDto> leerCompras(String rutaArchivo);
}