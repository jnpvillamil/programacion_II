package co.edu.uptc.Tiendaminorista.interfaces;

import java.time.LocalDate;
import java.util.List;
import co.edu.uptc.Tiendaminorista.modelo.MovimientoContable;

public interface IGestionContable {

    public void guardar(MovimientoContable movimiento);

    public List<MovimientoContable> listar();

    public List<MovimientoContable> filtrarPorTipo(String tipo);

    public List<MovimientoContable> filtrarPorRangoFechas(LocalDate desde, LocalDate hasta);

    public List<MovimientoContable> buscarPorCuenta(String cuentaContable);

    public double getTotalIngresos();

    public double getTotalEgresos();

    public double getSaldoActual();
}