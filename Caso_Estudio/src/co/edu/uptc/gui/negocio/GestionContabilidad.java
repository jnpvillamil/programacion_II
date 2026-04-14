package co.edu.uptc.gui.negocio;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.MovimientoContable;
import co.edu.uptc.persistencia.LocalContabilidad;

public class GestionContabilidad {

    private List<MovimientoContable> movimientos;
    private LocalContabilidad localContabilidad;

    public GestionContabilidad() {
        this.movimientos = new ArrayList<>();
        this.localContabilidad = new LocalContabilidad();
    }


    public boolean registrarMovimiento(MovimientoContable movimiento) {
        return movimientos.add(movimiento);
    }


    public List<MovimientoContable> listarMovimientos() {
        return movimientos;
    }


    public boolean actualizarMovimiento(MovimientoContable movimiento) {

        for (int i = 0; i < movimientos.size(); i++) {

            if (movimientos.get(i).getCodigoTransaccion()
                    .equals(movimiento.getCodigoTransaccion())) {

                movimientos.set(i, movimiento);
                return true;
            }
        }

        return false;
    }


    public boolean eliminarMovimiento(String codigoTransaccion) {

        return movimientos.removeIf(
            m -> m.getCodigoTransaccion().equals(codigoTransaccion)
        );
    }


    public MovimientoContable buscarMovimiento(String codigoTransaccion) {

        for (MovimientoContable m : movimientos) {
            if (m.getCodigoTransaccion().equals(codigoTransaccion)) {
                return m;
            }
        }

        return null;
    }


}