package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.AsientoContable;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.negocio.ReporteContableDTO;
import co.edu.uptc.enums.CuentaContable; 
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GestionContable {
    
    
    void registrarAsientoVenta(Venta venta);
    void registrarAsientoCompra(Compra compra);
    void registrarAsientoAjuste(String descripcion, Map<CuentaContable, Double> debitos,
                                 Map<CuentaContable, Double> creditos, String referencia);
    
    
    List<AsientoContable> listarAsientos();
    List<AsientoContable> listarAsientosPorFecha(LocalDate fecha);
    List<AsientoContable> listarAsientosPorPeriodo(LocalDate inicio, LocalDate fin);
    List<MovimientoContable> listarMovimientosPorCuenta(CuentaContable cuenta);
    
    
    double calcularSaldoCuenta(CuentaContable cuenta, LocalDate fechaCorte);
    Map<CuentaContable, Double> generarBalanceGeneral(LocalDate fechaCorte);
    Map<String, Double> generarEstadoResultados(LocalDate inicio, LocalDate fin);

    
    ReporteContableDTO generarReporteContablePeriodo(LocalDate inicio, LocalDate fin);
    
    
    Map<String, Double> obtenerResumenContable(LocalDate inicio, LocalDate fin);
}
