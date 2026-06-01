package co.uptc.edu.co.config;

import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.CompraDAO;
import co.uptc.edu.co.interfaces.IGestionFactura;
import co.uptc.edu.co.interfaces.IGestionReporte;
import co.uptc.edu.co.interfaces.IGestionConsultas;
import co.uptc.edu.co.interfaces.IGestionDevolucionVenta;
import co.uptc.edu.co.interfaces.DevolucionVentaDAO;
import co.uptc.edu.co.interfaces.FacturaDAO;
import co.uptc.edu.co.interfaces.MovimientoContableDAO;
import co.uptc.edu.co.interfaces.MovimientoInventarioDAO;
import co.uptc.edu.co.interfaces.ProductoDAO;
import co.uptc.edu.co.interfaces.ProveedorDAO;
import co.uptc.edu.co.interfaces.ReporteDAO;
import co.uptc.edu.co.interfaces.ClienteDAO;
import co.uptc.edu.co.interfaces.VentaDAO;

import co.uptc.edu.co.negocio.GestionCliente;
import co.uptc.edu.co.negocio.GestionCompra;
import co.uptc.edu.co.negocio.GestionContabilidad;
import co.uptc.edu.co.negocio.GestionConsultas;
import co.uptc.edu.co.negocio.GestionDevolucionVenta;
import co.uptc.edu.co.negocio.GestionProducto;
import co.uptc.edu.co.negocio.GestionProveedor;
import co.uptc.edu.co.negocio.GestionVenta;
import co.uptc.edu.co.negocio.GestionInventario;
import co.uptc.edu.co.negocio.GestionFactura;
import co.uptc.edu.co.negocio.GestionReporte;
import co.uptc.edu.co.persistencia.ClienteBDDAO;
import co.uptc.edu.co.persistencia.ComprasBDDAO;
import co.uptc.edu.co.persistencia.DevolucionVentaBDDAO;
import co.uptc.edu.co.persistencia.FacturaTxtDAO;
import co.uptc.edu.co.persistencia.MovimientoContableBDDAO;
import co.uptc.edu.co.persistencia.MovimientoInventarioBDDAO;
import co.uptc.edu.co.persistencia.ProductoBDDAO;
import co.uptc.edu.co.persistencia.ProveedorBDDAO;
import co.uptc.edu.co.persistencia.ReporteJSONDAO;
import co.uptc.edu.co.persistencia.VentaBDDAO;

public class TiendaConfig {

	private IGestionProducto gestionProducto;
	private IGestionInventario gestionInventario;
	private IGestionCliente gestionCliente;
	private IGestionProveedor gestionProveedor;
	private IGestionVenta gestionVenta;
	private IGestionCompra gestionCompra;
	private IGestionDevolucionVenta gestionDevolucionVenta;
	private IGestionFactura gestionFactura;
	private IGestionReporte gestionReporte;
	private IGestionConsultas gestionConsultas;
	private IGestionContabilidad gestionContabilidad;

	public TiendaConfig() {
		inicializarGestiones();
	}

	private void inicializarGestiones() {

		ProductoDAO productoDAO = new ProductoBDDAO();
		MovimientoInventarioDAO movimientoInventarioDAO = new MovimientoInventarioBDDAO();
		gestionProducto = new GestionProducto(productoDAO);
		gestionInventario = new GestionInventario(productoDAO, movimientoInventarioDAO);

		ProveedorDAO proveedorDAO = new ProveedorBDDAO();
		gestionProveedor = new GestionProveedor(proveedorDAO);

		ClienteDAO clienteDAO = new  ClienteBDDAO();
		gestionCliente = new GestionCliente(clienteDAO);

		VentaDAO ventaDAO = new VentaBDDAO();
		DevolucionVentaDAO devolucionVentaDAO = new DevolucionVentaBDDAO();
		MovimientoContableDAO movimientoContableDAO = new MovimientoContableBDDAO();
		gestionContabilidad = new GestionContabilidad(movimientoContableDAO);
		
		gestionVenta = new GestionVenta(ventaDAO, gestionInventario, gestionContabilidad);
		ReporteDAO reporteDAO = new ReporteJSONDAO();
		gestionReporte = new GestionReporte(ventaDAO, reporteDAO);
		gestionConsultas = new GestionConsultas(ventaDAO);
		gestionDevolucionVenta = new GestionDevolucionVenta(ventaDAO, devolucionVentaDAO, gestionInventario,
				gestionContabilidad);

		FacturaDAO facturaDAO = new FacturaTxtDAO();
		gestionFactura = new GestionFactura(facturaDAO);

		CompraDAO compraDAO = new ComprasBDDAO();
		gestionCompra = new GestionCompra(compraDAO, gestionInventario, gestionContabilidad);
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

	public IGestionDevolucionVenta getGestionDevolucionVenta() {
		return gestionDevolucionVenta;
	}

	public IGestionFactura getGestionFactura() {
		return gestionFactura;
	}

	public IGestionReporte getGestionReporte() {
		return gestionReporte;
	}

	public IGestionConsultas getGestionConsultas() {
		return gestionConsultas;
	}

	public IGestionContabilidad getGestionContabilidad() {
		return gestionContabilidad;
	}

}
