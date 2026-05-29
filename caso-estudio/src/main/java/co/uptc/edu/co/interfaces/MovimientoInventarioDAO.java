package co.uptc.edu.co.interfaces;

import java.sql.Connection;

import co.uptc.edu.co.modelo.MovimientoInventario;

public interface MovimientoInventarioDAO {

	void registrarMovimiento(MovimientoInventario movimiento) throws Exception;

	void registrarMovimiento(Connection conexion, MovimientoInventario movimiento) throws Exception;
}
