package co.edu.uptc.interfaces;

import java.util.List;
import co.edu.uptc.negocio.dto.movimientoContableDto;

public interface IGestionMovimientoContable {
    public void guardar(movimientoContableDto movimiento);
    public movimientoContableDto buscar(int codigoTransaccion);
    public List<movimientoContableDto> listar();
}