package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.Venta;

public interface IGestionContabilidad {

	void registrarIngresoPorVenta(Venta venta) throws Exception;

	void registrarEgresoPorCompra(Compra compra) throws Exception;

	void registrarReversoPorAnulacionCompra(Compra compra, String motivo) throws Exception;

	void registrarIngresoPorVenta(Connection conexion, Venta venta) throws Exception;

	void registrarReversoPorAnulacionVenta(Venta venta, String motivo) throws Exception;

	void registrarReversoPorAnulacionVenta(Connection conexion, Venta venta, String motivo) throws Exception;

	void registrarReversoPorDevolucionVenta(Venta venta, double subtotalDevuelto, double ivaDevuelto, String motivo)
			throws Exception;

	void registrarReversoPorDevolucionVenta(Connection conexion, Venta venta, double subtotalDevuelto,
			double ivaDevuelto, String motivo) throws Exception;

	List<MovimientoContable> obtenerMovimientos();

	MovimientoContable buscarMovimientoPorCodigo(String codigoTransaccion);
}
