package co.uptc.edu.tienda.interfaces;

import java.util.List;

import co.uptc.edu.tienda.modelo.MovimientoInventario;


public interface IGestionInventario {
	
	public void guardar(MovimientoInventario movimientos);
		
	public List<MovimientoInventario> leerMovimientos();

}
