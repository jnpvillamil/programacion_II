package co.uptc.edu.co.interfaces.dao;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.DevolucionVenta;

public interface DevolucionVentaDAO {

	void guardarDevolucion(DevolucionVenta devolucion) throws Exception;

	void guardarDevolucion(Connection conexion, DevolucionVenta devolucion) throws Exception;

	DevolucionVenta buscarPorCodigo(String codigoDevolucion) throws Exception;

	String obtenerUltimoCodigoDevolucion() throws Exception;

	int obtenerCantidadDevuelta(String numeroFactura, String codigoProducto) throws Exception;

	List<DevolucionVenta> buscarPorFactura(String numeroFactura) throws Exception;

	List<DevolucionVenta> listarDevoluciones() throws Exception;

}
