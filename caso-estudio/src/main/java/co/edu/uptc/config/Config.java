package co.edu.uptc.config;

import co.edu.uptc.gui.negocio.GestionCliente;
import co.edu.uptc.gui.negocio.GestionContador; 
import co.edu.uptc.gui.negocio.GestionProducto;
import co.edu.uptc.gui.negocio.GestionProveedor;
import co.edu.uptc.gui.negocio.GestionSeguridad; 
import co.edu.uptc.gui.interfaces.IGestionContador;
import co.edu.uptc.persistencia.LocalCliente;
import co.edu.uptc.persistencia.LocalContador; 
import co.edu.uptc.persistencia.LocalProducto;
import co.edu.uptc.persistencia.LocalUsuario;

@SuppressWarnings("unused")
public class Config {

	private GestionProducto gestionProducto;
	private GestionCliente gestionCliente;
	private GestionProveedor gestionProveedor;
	private GestionSeguridad gestionDeSeguridad; 
	private GestionContador gestionContador;

	public Config() {
		gestionProducto = new GestionProducto(null);
		gestionCliente = new GestionCliente();
		gestionProveedor = new GestionProveedor();
		gestionDeSeguridad = new GestionSeguridad();

		IGestionContador persistentContador = new LocalContador();
		this.gestionContador = new GestionContador(persistentContador);
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

	public GestionContador getGestionContador() {
		return gestionContador;
	}
}