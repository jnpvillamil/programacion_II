package co.uptc.edu.co.interfaces.dao;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.MovimientoContable;

public interface MovimientoContableDAO {

	void guardarMovimiento(MovimientoContable movimiento) throws Exception;

	void guardarMovimiento(Connection conexion, MovimientoContable movimiento) throws Exception;

	void guardarMovimientos(Connection conexion, List<MovimientoContable> movimientos) throws Exception;

	MovimientoContable buscarPorCodigo(String codigoTransaccion) throws Exception;

	List<MovimientoContable> listarMovimientos() throws Exception;
}
