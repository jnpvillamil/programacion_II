package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.MovimientoContable;

public interface IContabilizable {
    void registrarMovimiento(MovimientoContable movimiento);
    double obtenerSaldo(String cuentaContable);
}