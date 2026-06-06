package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.DevolucionVenta;

public interface IGestionDevolucionVenta {

	void guardar(DevolucionVenta devolucion) throws Exception;

	void guardar(Connection conexion, DevolucionVenta devolucion) throws Exception;

	DevolucionVenta buscar(String codigoDevolucion) throws Exception;

	List<DevolucionVenta> buscarPorFactura(String numeroFactura) throws Exception;

	List<DevolucionVenta> listar() throws Exception;

	int obtenerCantidadDevuelta(String numeroFactura, String codigoProducto) throws Exception;

	String obtenerUltimoCodigo() throws Exception;
}