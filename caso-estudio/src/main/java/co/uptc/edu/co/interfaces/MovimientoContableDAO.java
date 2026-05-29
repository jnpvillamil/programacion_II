package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.MovimientoContable;

public interface MovimientoContableDAO {

	void guardarMovimiento(MovimientoContable movimiento) throws Exception;

	MovimientoContable buscarPorCodigo(String codigoTransaccion) throws Exception;

	List<MovimientoContable> listarMovimientos() throws Exception;
}
