package co.uptc.edu.tienda.interfaces;

import java.util.List;
import co.uptc.edu.tienda.modelo.MovimientoContable;

public interface IGestionContable {

    void guardar(MovimientoContable movimiento);

    List<MovimientoContable> cargar();
}