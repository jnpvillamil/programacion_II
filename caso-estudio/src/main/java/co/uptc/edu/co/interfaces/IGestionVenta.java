package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.Venta;

public interface IGestionVenta {

	void guardar(Venta venta) throws Exception;

	void guardar(Connection conexion, Venta venta) throws Exception;

	void actualizar(Venta venta) throws Exception;

	void actualizar(Connection conexion, Venta venta) throws Exception;

	Venta buscar(String numeroFactura) throws Exception;

	Venta buscar(Connection conexion, String numeroFactura) throws Exception;

	List<Venta> listar() throws Exception;

	List<Venta> listarPorFecha(LocalDate fecha) throws Exception;
}