package co.edu.uptc.tiendaminorista.interfaces;

import java.time.LocalDate;
import java.util.List;
import co.edu.uptc.tiendaminorista.modelo.MovimientoContable;

public interface IGestionContable {
    void guardar(MovimientoContable movimiento);
    List<MovimientoContable> listar();
    List<MovimientoContable> filtrarPorTipo(String tipo);
    List<MovimientoContable> filtrarPorRangoFechas(LocalDate desde, LocalDate hasta);
    List<MovimientoContable> buscarPorCuenta(String cuentaContable);
    double getTotalIngresos();
    double getTotalEgresos();
    double getSaldoActual();
}
