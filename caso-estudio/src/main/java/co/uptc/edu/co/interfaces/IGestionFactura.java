package co.uptc.edu.co.interfaces;

import co.uptc.edu.co.modelo.Venta;

public interface IGestionFactura {

	String generarFactura(Venta venta) throws Exception;

}
