package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Venta;

public interface VentaDAO {
   
	void guardarVenta(Venta venta) throws  Exception;
	
	void actualizarVenta(Venta venta) throws  Exception;
	
	Venta buscarVentaPorNumero(String numeroFactura) throws Exception;
	
	List<Venta> listarVentas() throws  Exception;
	
}
