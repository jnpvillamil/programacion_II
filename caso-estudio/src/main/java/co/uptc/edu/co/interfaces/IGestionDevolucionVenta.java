package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.DetalleVentaDevolucionDTO;
import co.uptc.edu.co.modelo.DevolucionVenta;
import co.uptc.edu.co.modelo.Venta;

public interface IGestionDevolucionVenta {

	void devolverVenta(String numeroFactura, String codigoProducto, int cantidad, String motivo) throws Exception;

	void devolverVenta(Venta venta, String codigoProducto, int cantidad, String motivo) throws Exception;

	double calcularValorDevolucion(Venta venta, String codigoProducto, int cantidad) throws Exception;

	DevolucionVenta buscarDevolucionPorCodigo(String codigoDevolucion) throws Exception;

	List<DetalleVentaDevolucionDTO> obtenerResumenDetalleVenta(Venta venta) throws Exception;

	List<DevolucionVenta> obtenerDevolucionesPorFactura(String numeroFactura) throws Exception;

	List<DevolucionVenta> obtenerDevoluciones() throws Exception;

}
