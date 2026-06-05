package co.edu.uptc.gui.interfaces;

import java.util.List;

import co.edu.uptc.gui.modelo.Venta;

public interface IGestionDeVenta {
	public void guardarVenta(Venta venta);
	public void anularVenta(String numeroFactura);
	public Venta buscarVentaPorNumeroFactura(String numeroFactura);
	public List<Venta> obtenerListaVentas();

}