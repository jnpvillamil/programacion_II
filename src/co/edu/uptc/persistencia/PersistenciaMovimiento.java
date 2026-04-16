package co.edu.uptc.persistencia;

import co.edu.uptc.negocio.MovimientoContable;
import java.util.ArrayList;

public class PersistenciaMovimiento {
    private ArrayList<MovimientoContable> listaMovimientos;

    public PersistenciaMovimiento() {
        this.listaMovimientos = new ArrayList<>();
    }

    public void guardar(MovimientoContable movimiento) {
        this.listaMovimientos.add(movimiento);
    }

    public ArrayList<MovimientoContable> leerTodos() {
        return this.listaMovimientos;
    }
}