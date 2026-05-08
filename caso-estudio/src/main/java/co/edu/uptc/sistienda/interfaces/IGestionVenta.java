package co.edu.uptc.sistienda.interfaces;

import java.util.List;

import co.edu.uptc.sistienda.modelo.Venta;

public interface IGestionVenta {
	public void guardarVenta(Venta venta);
	public void anularVenta(String numeroFactura);
	public Venta buscarVentaPorNumeroFactura(String numeroFactura);
	public List<Venta> obtenerListaVentas();

}
