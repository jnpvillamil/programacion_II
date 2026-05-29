package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.MovimientoInventario;
import co.uptc.edu.co.modelo.Venta;

public interface IGestionInventario {

	void validarStockDisponible(List<DetalleVenta> detalles) throws Exception;

	void validarStockDisponible(Connection conexion, List<DetalleVenta> detalles) throws Exception;

	void registrarSalidaPorVenta(Venta venta) throws Exception;

	void registrarSalidaPorVenta(Connection conexion, Venta venta) throws Exception;

	void registrarEntrada(String codigoProducto, int cantidad, String descripcion) throws Exception;

	void registrarEntrada(Connection conexion, String codigoProducto, int cantidad, String descripcion) throws Exception;

	void registrarSalida(String codigoProducto, int cantidad, String descripcion) throws Exception;

	void registrarSalida(Connection conexion, String codigoProducto, int cantidad, String descripcion) throws Exception;

	void registrarEntradaPorAnulacion(Venta venta, String motivo) throws Exception;

	void registrarEntradaPorAnulacion(Connection conexion, Venta venta, String motivo) throws Exception;

	List<MovimientoInventario> obtenerMovimientos();

}
