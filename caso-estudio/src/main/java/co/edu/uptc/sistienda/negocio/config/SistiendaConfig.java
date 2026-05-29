package co.edu.uptc.sistienda.negocio.config;

import co.edu.uptc.sistienda.negocio.GestionCliente;
import co.edu.uptc.sistienda.negocio.GestionDeSeguridad;
import co.edu.uptc.sistienda.negocio.GestionProducto;
import co.edu.uptc.sistienda.negocio.GestionProveedor;
import co.edu.uptc.sistienda.negocio.GestionVenta;
import co.edu.uptc.sistienda.persistencia.ClienteDAO;
import co.edu.uptc.sistienda.persistencia.ProductoDAO;
import co.edu.uptc.sistienda.persistencia.ProveedorDAO;
import co.edu.uptc.sistienda.persistencia.VentaDAO;

public class SistiendaConfig {

	private GestionProducto gestionProducto;
	private GestionCliente gestionCliente;
	private GestionProveedor gestionProveedor;
	private GestionDeSeguridad gestionDeSeguridad;
	private GestionVenta gestionVenta; 

	public SistiendaConfig() {
		gestionProducto  = new GestionProducto(new ProductoDAO());
		gestionCliente   = new GestionCliente(new ClienteDAO());
		gestionProveedor = new GestionProveedor(new ProveedorDAO());
		gestionDeSeguridad = new GestionDeSeguridad();
		gestionVenta = new GestionVenta(new VentaDAO(), gestionProducto);
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
