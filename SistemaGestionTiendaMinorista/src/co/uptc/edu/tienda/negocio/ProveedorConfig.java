package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.interfaces.IGestionProveedor;
import co.uptc.edu.tienda.persistencia.LocalProveedor;

public class ProveedorConfig {
	
	private GestionProveedor gestProveedor;
	
	private IGestionProveedor iProveedor;

	public ProveedorConfig() {
		super();
		// TODO Auto-generated constructor stub
		iProveedor = new LocalProveedor();
		gestProveedor = new GestionProveedor(iProveedor);
	}

	public GestionProveedor getGestProveedor() {
		return gestProveedor;
	}

	public void setGestProveedor(GestionProveedor gestProveedor) {
		this.gestProveedor = gestProveedor;
	}

	public IGestionProveedor getiProveedor() {
		return iProveedor;
	}

	public void setiProveedor(IGestionProveedor iProveedor) {
		this.iProveedor = iProveedor;
	}
	
	
	

}
