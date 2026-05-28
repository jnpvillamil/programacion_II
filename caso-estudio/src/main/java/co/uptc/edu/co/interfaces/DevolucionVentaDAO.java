package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.DevolucionVenta;

public interface DevolucionVentaDAO {

	void guardarDevolucion(DevolucionVenta devolucion) throws Exception;
	
	DevolucionVenta buscarPorCodigo(String codigoDevolucion) throws Exception;
	
	List<DevolucionVenta> buscarPorFactura(String numeroFactura) throws Exception;
	
	List<DevolucionVenta> listarDevoluciones() throws Exception;

}
