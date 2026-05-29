package co.uptc.edu.co.interfaces;

import java.sql.Connection;
import java.util.List;

import co.uptc.edu.co.modelo.Venta;

public interface VentaDAO {

	void guardarVenta(Venta venta) throws Exception;

	void guardarVenta(Connection conexion, Venta venta) throws Exception;

	void actualizarVenta(Venta venta) throws Exception;

	void actualizarVenta(Connection conexion, Venta venta) throws Exception;

	Venta buscarVentaPorNumero(String numeroFactura) throws Exception;

	List<Venta> listarVentas() throws Exception;

}
