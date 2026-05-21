package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.MovimientoContable;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaContable implements Repositorio<MovimientoContable> {
    private List<MovimientoContable> movimientos;

    public PersistenciaContable() {
        this.movimientos = new ArrayList<>();
    }

    @Override
    public void guardar(MovimientoContable movimiento) {
        this.movimientos.add(movimiento);
    }

    @Override
    public void eliminar(String id) {
        this.movimientos.removeIf(m -> m.getCodigoTransaccion().equals(id));
    }

    @Override
    public List<MovimientoContable> listar() {
        return new ArrayList<>(this.movimientos);
    }

    @Override
    public MovimientoContable buscarPorId(String id) {
        for (MovimientoContable m : this.movimientos) {
            if (m.getCodigoTransaccion().equals(id)) {
                return m;
            }
        }
        return null;
    }
}