package co.uptc.edu.co.config;

import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.interfaces.IGestionCompra;

import co.uptc.edu.co.interfaces.ProductoDAO;
import co.uptc.edu.co.interfaces.ProveedorDAO;
import co.uptc.edu.co.interfaces.ClienteDAO;
import co.uptc.edu.co.negocio.GestionCliente;
import co.uptc.edu.co.negocio.GestionCompra;
import co.uptc.edu.co.negocio.GestionProducto;
import co.uptc.edu.co.negocio.GestionProveedor;
import co.uptc.edu.co.negocio.GestionVenta;

import co.uptc.edu.co.persistencia.ProductoJSONDAO;
import co.uptc.edu.co.persistencia.ProveedorJSONDAO;
import co.uptc.edu.co.persistencia.ClienteJSONDAO;

public class TiendaConfig {

	private IGestionProducto gestionProducto;
	private IGestionCliente gestionCliente;
	private IGestionProveedor gestionProveedor;
	private IGestionVenta gestionVenta;
	private IGestionCompra gestionCompra;

	public TiendaConfig() {
		inicializarGestiones();
	}

	private void inicializarGestiones() {

		ProductoDAO productoDAO = new ProductoJSONDAO();
		gestionProducto = new GestionProducto(productoDAO);

		ProveedorDAO proveedorDAO = new ProveedorJSONDAO();
		gestionProveedor = new GestionProveedor(proveedorDAO);

		ClienteDAO clienteDAO = new ClienteJSONDAO();
		gestionCliente = new GestionCliente(clienteDAO);

		gestionVenta = new GestionVenta();

		gestionCompra = new GestionCompra();
	} 

	public IGestionProducto getGestionProducto() {
		return gestionProducto;
	}

	public IGestionCliente getGestionCliente() {
		return gestionCliente;
	}

	public IGestionProveedor getGestionProveedor() {
		return gestionProveedor;
	}

	public IGestionVenta getGestionVenta() {
		return gestionVenta;
	}

	public IGestionCompra getGestionCompra() {
		return gestionCompra;
	}
}