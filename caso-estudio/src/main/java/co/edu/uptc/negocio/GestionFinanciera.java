package co.edu.uptc.negocio;

import java.time.LocalDate;
import java.time.LocalDateTime;

import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.interfaces.IRepositorioFinanciero;
import co.edu.uptc.persistencia.PersistenciaFinanciera;

public class GestionFinanciera {
    
    private final IRepositorioFinanciero repoFinanciero;

    public GestionFinanciera(IRepositorioFinanciero repoFinanciero) {
        this.repoFinanciero = repoFinanciero;
    }

    public GestionFinanciera() {
        this(new PersistenciaFinanciera());
    }


    public ReporteFinancieroDTO generarReporteFinanciero(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null || inicio.isAfter(fin)) {
            return new ReporteFinancieroDTO("", 0, 0, 0);
        }
        LocalDateTime desde = inicio.atStartOfDay();
        LocalDateTime hasta = fin.atTime(23, 59, 59);
        double ingresos = repoFinanciero.calcularTotalIngresosVentas(desde, hasta);
        double egresos = repoFinanciero.calcularTotalCostosCompras(desde, hasta);
        return new ReporteFinancieroDTO(
                inicio + " a " + fin,
                ingresos,
                egresos,
                ingresos - egresos);
    }

    public double calcularUtilidad(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null || fin == null || inicio.isAfter(fin)) {
            return 0.0;
        }
        return generarReporteFinanciero(inicio.toLocalDate(), fin.toLocalDate()).getUtilidadBruta();
    }
}