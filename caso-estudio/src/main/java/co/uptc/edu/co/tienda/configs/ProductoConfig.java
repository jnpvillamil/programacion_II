package co.uptc.edu.co.tienda.configs;

import co.uptc.edu.tienda.interfaces.IGestionProducto;
import co.uptc.edu.tienda.negocio.GestionProducto;
import co.uptc.edu.tienda.persistencia.LocalProducto;

public class ProductoConfig {

	private GestionProducto gestProducto;

	private IGestionProducto iProducto;

	public ProductoConfig() {
		super();
		// TODO Auto-generated constructor stub
		iProducto = new LocalProducto();
		gestProducto = new GestionProducto(iProducto);
	}

	public GestionProducto getGestProducto() {
		return gestProducto;
	}

	public void setGestProducto(GestionProducto gestProducto) {
		this.gestProducto = gestProducto;
	}

	public IGestionProducto getiProducto() {
		return iProducto;
	}

	public void setiProducto(IGestionProducto iProducto) {
		this.iProducto = iProducto;
	}
}

