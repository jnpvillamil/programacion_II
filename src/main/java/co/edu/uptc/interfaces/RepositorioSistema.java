package co.edu.uptc.interfaces;

import co.edu.uptc.dto.MovimientoResumenDTO;
import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ReporteUtilidadDTO;
import co.edu.uptc.modelo.MovimientoContable;

import java.util.List;

/**
 * Contrato unificado de persistencia para contabilidad y reportes del sistema.
 */
public interface RepositorioSistema {

    void guardarMovimientoContable(MovimientoContable movimiento);

    List<MovimientoContable> listarMovimientoContable();

    List<MovimientoResumenDTO> listarResumenMovimiento();

    List<ReporteUtilidadDTO> listarReporteUtilidad();

    ReporteFinancieroDTO construirReporteFinanciero();
}
