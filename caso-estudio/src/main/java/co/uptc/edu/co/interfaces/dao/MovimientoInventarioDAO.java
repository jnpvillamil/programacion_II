package co.uptc.edu.co.interfaces.dao;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.MovimientoInventario;

public interface MovimientoInventarioDAO {

	void registrarMovimiento(MovimientoInventario movimiento) throws Exception;

	void registrarMovimiento(Connection conexion, MovimientoInventario movimiento) throws Exception;

	void registrarMovimientos(Connection conexion, List<MovimientoInventario> movimientos) throws Exception;
}
