package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.DevolucionVenta;

public interface IGestionDevolucionVenta {

	void devolverVenta(String numeroFactura, String codigoProducto, int cantidad, String motivo) throws Exception;

	DevolucionVenta buscarDevolucionPorCodigo(String codigoDevolucion) throws Exception;

	List<DevolucionVenta> obtenerDevolucionesPorFactura(String numeroFactura) throws Exception;

	List<DevolucionVenta> obtenerDevoluciones() throws Exception;

}
