package co.edu.uptc.interfaces;

import java.time.LocalDateTime;
import java.util.List;

public interface Consultable {
    List<Object> consultarPorRangoFecha(LocalDateTime inicio, LocalDateTime fin);
    List<Object> consultarPorCriterio(String criterio);
}
