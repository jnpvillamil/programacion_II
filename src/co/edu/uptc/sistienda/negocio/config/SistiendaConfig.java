package co.edu.uptc.sistienda.negocio.config;

import co.edu.uptc.sistienda.negocio.GestionCliente;
import co.edu.uptc.sistienda.negocio.GestionDeSeguridad;
import co.edu.uptc.sistienda.negocio.GestionProducto;
import co.edu.uptc.sistienda.negocio.GestionProveedor;
import co.edu.uptc.sistienda.persistencia.LocalCliente;
import co.edu.uptc.sistienda.persistencia.LocalProducto;
import co.edu.uptc.sistienda.persistencia.LocalProveedor;

public class SistiendaConfig {

	private GestionProducto gestionProducto;
	private GestionCliente gestionCliente;
	private GestionProveedor gestionProveedor;
	private GestionDeSeguridad gestionDeSeguridad;

	public SistiendaConfig() {
		gestionProducto  = new GestionProducto(new LocalProducto());
		gestionCliente   = new GestionCliente(new LocalCliente());
		gestionProveedor = new GestionProveedor(new LocalProveedor());
		gestionDeSeguridad = new GestionDeSeguridad();
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

	public GestionDeSeguridad getGestionDeSeguridad() {
		return gestionDeSeguridad;
	}
}
