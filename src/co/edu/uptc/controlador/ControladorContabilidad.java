package co.edu.uptc.controlador;

import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.negocio.GestionContable;
import java.util.List;

public class ControladorContabilidad {
    private GestionContable gestionContable;

    public ControladorContabilidad(GestionContable gestionContable) {
        this.gestionContable = gestionContable;
    }

    public List<MovimientoContable> listarMovimientos() {
        return this.gestionContable.listarMovimientos();
    }
}