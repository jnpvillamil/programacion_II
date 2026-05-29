package co.uptc.edu.co.interfaces;

import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.Compra;

public interface IGestionFactura {

	String generarFactura(Venta venta) throws Exception;

	String generarFactura(Compra compra) throws Exception;

}
