package co.uptc.edu.co.interfaces;

import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;

public interface IGestionConsultas {

	List<Venta> obtenerVentasPorFecha(LocalDate fecha) throws Exception;

	List<Compra> obtenerComprasPorProveedor(String codigoProveedor, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception;

	List<Producto> obtenerProductosStockBajo() throws Exception;

	List<Venta> obtenerHistorialCliente(String codigoCliente) throws Exception;

	List<MovimientoContable> obtenerMovimientosContables(String cuenta, String tipoMovimiento,
			LocalDate fechaInicio, LocalDate fechaFin) throws Exception;
}
