package co.uptc.edu.co.config;

import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.ProductoDAO;
import co.uptc.edu.co.interfaces.ProveedorDAO;
import co.uptc.edu.co.interfaces.ClienteDAO;
import co.uptc.edu.co.interfaces.VentaDAO;

import co.uptc.edu.co.negocio.GestionCliente;
import co.uptc.edu.co.negocio.GestionCompra;
import co.uptc.edu.co.negocio.GestionProducto;
import co.uptc.edu.co.negocio.GestionProveedor;
import co.uptc.edu.co.negocio.GestionVenta;
import co.uptc.edu.co.negocio.GestionInventario;


import co.uptc.edu.co.persistencia.ClienteJSONDAO;
import co.uptc.edu.co.persistencia.ProductoBDDAO;
import co.uptc.edu.co.persistencia.ProveedorBDDAO;
import co.uptc.edu.co.persistencia.VentaBDDAO;


public class TiendaConfig {

	private IGestionProducto gestionProducto;
	private IGestionInventario gestionInventario;
	private IGestionCliente gestionCliente;
	private IGestionProveedor gestionProveedor;
	private IGestionVenta gestionVenta;
	private IGestionCompra gestionCompra;

	public TiendaConfig() {
		inicializarGestiones();
	}

	private void inicializarGestiones() {

		ProductoDAO productoDAO = new ProductoBDDAO();
		gestionProducto = new GestionProducto(productoDAO);
		gestionInventario = new GestionInventario(productoDAO);

		ProveedorDAO proveedorDAO = new ProveedorBDDAO();
		gestionProveedor = new GestionProveedor(proveedorDAO);

		ClienteDAO clienteDAO = new ClienteJSONDAO();
		gestionCliente = new GestionCliente(clienteDAO);

		VentaDAO ventaDAO = new VentaBDDAO();
		gestionVenta = new GestionVenta(ventaDAO, gestionInventario);

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

	public IGestionInventario getGestionInventario() {
		return gestionInventario;
	}
	
}
