package co.uptc.edu.co.config;

import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.interfaces.IGestionConsultas;
import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.IGestionDevolucionVenta;
import co.uptc.edu.co.interfaces.IGestionFactura;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.interfaces.IGestionReporte;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.interfaces.dao.ClienteDAO;
import co.uptc.edu.co.interfaces.dao.CompraDAO;
import co.uptc.edu.co.interfaces.dao.DevolucionVentaDAO;
import co.uptc.edu.co.interfaces.dao.FacturaDAO;
import co.uptc.edu.co.interfaces.dao.MovimientoContableDAO;
import co.uptc.edu.co.interfaces.dao.MovimientoInventarioDAO;
import co.uptc.edu.co.interfaces.dao.ProductoDAO;
import co.uptc.edu.co.interfaces.dao.ProveedorDAO;
import co.uptc.edu.co.interfaces.dao.ReporteDAO;
import co.uptc.edu.co.interfaces.dao.VentaDAO;
import co.uptc.edu.co.negocio.GestionCliente;
import co.uptc.edu.co.negocio.GestionCompra;
import co.uptc.edu.co.negocio.GestionConsultas;
import co.uptc.edu.co.negocio.GestionContabilidad;
import co.uptc.edu.co.negocio.GestionDevolucionVenta;
import co.uptc.edu.co.negocio.GestionFactura;
import co.uptc.edu.co.negocio.GestionInventario;
import co.uptc.edu.co.negocio.GestionProducto;
import co.uptc.edu.co.negocio.GestionProveedor;
import co.uptc.edu.co.negocio.GestionReporte;
import co.uptc.edu.co.negocio.GestionVenta;
import co.uptc.edu.co.persistencia.archivo.FacturaTxtDAO;
import co.uptc.edu.co.persistencia.archivo.ReporteJSONDAO;
import co.uptc.edu.co.persistencia.bd.ClienteBDDAO;
import co.uptc.edu.co.persistencia.bd.ComprasBDDAO;
import co.uptc.edu.co.persistencia.bd.DevolucionVentaBDDAO;
import co.uptc.edu.co.persistencia.bd.MovimientoContableBDDAO;
import co.uptc.edu.co.persistencia.bd.MovimientoInventarioBDDAO;
import co.uptc.edu.co.persistencia.bd.ProductoBDDAO;
import co.uptc.edu.co.persistencia.bd.ProveedorBDDAO;
import co.uptc.edu.co.persistencia.bd.VentaBDDAO;

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

		ClienteDAO clienteDAO = new ClienteBDDAO();
		gestionCliente = new GestionCliente(clienteDAO);

		VentaDAO ventaDAO = new VentaBDDAO();
		DevolucionVentaDAO devolucionVentaDAO = new DevolucionVentaBDDAO();
		MovimientoContableDAO movimientoContableDAO = new MovimientoContableBDDAO();
		gestionContabilidad = new GestionContabilidad(movimientoContableDAO);

		gestionVenta = new GestionVenta(ventaDAO, gestionInventario, gestionContabilidad);
		CompraDAO compraDAO = new ComprasBDDAO();
		ReporteDAO reporteDAO = new ReporteJSONDAO();
		gestionReporte = new GestionReporte(ventaDAO, reporteDAO, productoDAO, compraDAO);
		gestionConsultas = new GestionConsultas(ventaDAO);
		gestionDevolucionVenta = new GestionDevolucionVenta(ventaDAO, devolucionVentaDAO, gestionInventario,
				gestionContabilidad);

		FacturaDAO facturaDAO = new FacturaTxtDAO();
		gestionFactura = new GestionFactura(facturaDAO);

		
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
