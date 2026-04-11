package co.edu.uptc.gui.negocio;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.MovimientoContable;

public class GestionContabilidad {

    private List<MovimientoContable> movimientos;

    public GestionContabilidad() {
        movimientos = new ArrayList<>();
    }

    public boolean registrarMovimiento(MovimientoContable movimiento) {
        movimientos.add(movimiento);
        return true;
    }

    public List<MovimientoContable> listarMovimientos() {
        return movimientos;
    }
}