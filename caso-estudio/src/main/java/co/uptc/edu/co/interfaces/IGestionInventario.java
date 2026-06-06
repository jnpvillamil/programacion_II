package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.MovimientoInventario;

public interface IGestionInventario {

    void guardar(MovimientoInventario movimiento) throws Exception;

    void guardar(Connection conexion, MovimientoInventario movimiento) throws Exception;

    void guardar(Connection conexion, List<MovimientoInventario> movimientos) throws Exception;

    List<MovimientoInventario> listar() throws Exception;
}