package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.MovimientoContable;

public interface Contabilizable {
    void registrarMovimiento(MovimientoContable movimiento);
    double obtenerSaldo(String cuentaContable);
}