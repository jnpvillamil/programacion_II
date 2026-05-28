package co.edu.uptc.interfaces;

import co.edu.uptc.dto.ResumenDiarioJSONDTO;
import co.edu.uptc.modelo.MovimientoContable;

import java.util.List;

public interface IRepositorioContable extends Repositorio<MovimientoContable> {

    List<MovimientoContable> consultarPorTransaccion(String codigoTransaccion);

    java.util.Map<String, Double> resumenContablePorPeriodo(java.time.LocalDateTime inicio, java.time.LocalDateTime fin);

    ResumenDiarioJSONDTO.ResumenContable resumenFinancieroPorPeriodo(java.time.LocalDateTime inicio,
                                                                      java.time.LocalDateTime fin);
}