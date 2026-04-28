package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.MovimientoContable;

public class LocalContabilidad {

    private List<MovimientoContable> contabilidad;

    public LocalContabilidad() {
        this.contabilidad = new ArrayList<>();
    }


    public boolean registrarMovimiento(MovimientoContable movimiento) {
        return contabilidad.add(movimiento);
    }

    public boolean actualizarMovimiento(MovimientoContable movimientoActualizado) {

        for (int i = 0; i < contabilidad.size(); i++) {

            if (contabilidad.get(i)
                    .getCodigoTransaccion()
                    .equalsIgnoreCase(movimientoActualizado.getCodigoTransaccion())) {

                contabilidad.set(i, movimientoActualizado);
                return true;
            }
        }

        return false;
    }


    public MovimientoContable buscarMovimiento(String codigoTransaccion) {

        for (MovimientoContable m : contabilidad) {

            if (m.getCodigoTransaccion()
                    .equalsIgnoreCase(codigoTransaccion)) {
                return m;
            }
        }

        return null;
    }


    public boolean eliminarMovimiento(String codigoTransaccion) {

        return contabilidad.removeIf(
            m -> m.getCodigoTransaccion()
                 .equalsIgnoreCase(codigoTransaccion)
        );
    }


    public List<MovimientoContable> getContabilidad() {
        return contabilidad;
    }
}