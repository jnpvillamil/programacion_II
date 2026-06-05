package co.edu.uptc.config;

import co.edu.uptc.gui.negocio.GestionCliente;
import co.edu.uptc.gui.negocio.GestionProducto;
import co.edu.uptc.gui.negocio.GestionProveedor;
import co.edu.uptc.gui.negocio.GestionSeguridad;
import co.edu.uptc.persistencia.LocalCliente;
import co.edu.uptc.persistencia.LocalProducto;
import co.edu.uptc.persistencia.LocalUsuario;

public class Config {

	private GestionProducto gestionProducto;
	private GestionCliente gestionCliente;
	private GestionProveedor gestionProveedor;
	private GestionSeguridad gestionDeSeguridad;


	public Config() {
		gestionProducto = new GestionProducto(null);
		gestionCliente = new GestionCliente();
		gestionProveedor = new GestionProveedor();
		gestionDeSeguridad = new GestionSeguridad();
	}

	public GestionProducto getGestionProducto() {
		return gestionProducto;
	}

	public GestionCliente getGestionCliente() {
		return gestionCliente;
	}

	public GestionProveedor getGestionProveedor() {
		return gestionProveedor;
	}

	public GestionSeguridad getGestionDeSeguridad() {
		return gestionDeSeguridad;
	}

}