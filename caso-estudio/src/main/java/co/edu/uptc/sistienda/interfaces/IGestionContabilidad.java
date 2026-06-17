package co.edu.uptc.sistienda.interfaces;

import java.time.LocalDate;
import java.util.List;

import co.edu.uptc.sistienda.contabilidad.modelo.MovimientoContable;

public interface IGestionContabilidad {

	void guardarMovimiento(MovimientoContable movimiento);

	void anularMovimientoPorDocumento(String documentoOrigen);

	List<MovimientoContable> obtenerListaMovimientos();

	List<MovimientoContable> consultarMovimientos(String cuentaContable, LocalDate fechaInicio, LocalDate fechaFin);
}