package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.Venta;

public interface IGestionContabilidad {

	void registrarIngresoPorVenta(Venta venta) throws Exception;

	void registrarReversoPorAnulacionVenta(Venta venta, String motivo) throws Exception;

	void registrarReversoPorDevolucionVenta(Venta venta, double subtotalDevuelto, double ivaDevuelto, String motivo)
			throws Exception;

	List<MovimientoContable> obtenerMovimientos();

	MovimientoContable buscarMovimientoPorCodigo(String codigoTransaccion);
}
