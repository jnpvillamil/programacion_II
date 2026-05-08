package co.edu.uptc.sistienda.negocio.config;

import co.edu.uptc.sistienda.negocio.GestionCliente;
import co.edu.uptc.sistienda.negocio.GestionDeSeguridad;
import co.edu.uptc.sistienda.negocio.GestionProducto;
import co.edu.uptc.sistienda.negocio.GestionProveedor;
import co.edu.uptc.sistienda.negocio.GestionVenta;
import co.edu.uptc.sistienda.persistencia.LocalCliente;
import co.edu.uptc.sistienda.persistencia.LocalProducto;
import co.edu.uptc.sistienda.persistencia.LocalProveedor;
import co.edu.uptc.sistienda.persistencia.LocalVenta;

public class SistiendaConfig {

	private GestionProducto gestionProducto;
	private GestionCliente gestionCliente;
	private GestionProveedor gestionProveedor;
	private GestionDeSeguridad gestionDeSeguridad;
	private GestionVenta gestionVenta; 

	public SistiendaConfig() {
		gestionProducto  = new GestionProducto(new LocalProducto());
		gestionCliente   = new GestionCliente(new LocalCliente());
		gestionProveedor = new GestionProveedor(new LocalProveedor());
		gestionDeSeguridad = new GestionDeSeguridad();
		gestionVenta = new GestionVenta(new LocalVenta(), gestionProducto);
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
	
	public GestionVenta getGestionVenta() {
		return gestionVenta;
	}
}
