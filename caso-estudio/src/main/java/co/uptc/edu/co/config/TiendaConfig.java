package co.uptc.edu.co.config;

import co.uptc.edu.co.interfaces.IGestionArchivoFactura;
import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.interfaces.IGestionDevolucionVenta;
import co.uptc.edu.co.interfaces.IGestionEmpleado;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.IGestionMovimientoContable;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.interfaces.IGestionReporte;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.negocio.GestionCliente;
import co.uptc.edu.co.negocio.GestionCompra;
import co.uptc.edu.co.negocio.GestionConsultas;
import co.uptc.edu.co.negocio.GestionContabilidad;
import co.uptc.edu.co.negocio.GestionDevolucionVenta;
import co.uptc.edu.co.negocio.GestionEmpleado;
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
import co.uptc.edu.co.persistencia.bd.EmpleadoBDDAO;
import co.uptc.edu.co.persistencia.bd.MovimientoContableBDDAO;
import co.uptc.edu.co.persistencia.bd.MovimientoInventarioBDDAO;
import co.uptc.edu.co.persistencia.bd.ProductoBDDAO;
import co.uptc.edu.co.persistencia.bd.ProveedorBDDAO;
import co.uptc.edu.co.persistencia.bd.VentaBDDAO;

public class TiendaConfig {

	private IGestionProducto iProducto;
	private GestionProducto gestionProducto;

	private IGestionCliente iCliente;
	private GestionCliente gestionCliente;

	private IGestionProveedor iProveedor;
	private GestionProveedor gestionProveedor;

	private IGestionEmpleado iEmpleado;
	private GestionEmpleado gestionEmpleado;

	private IGestionInventario iMovimientoInventario;
	private GestionInventario gestionInventario;

	private IGestionMovimientoContable iMovimientoContable;
	private GestionContabilidad gestionContabilidad;

	private IGestionVenta iVenta;
	private GestionVenta gestionVenta;

	private IGestionCompra iCompra;
	private GestionCompra gestionCompra;

	private IGestionDevolucionVenta iDevolucionVenta;
	private GestionDevolucionVenta gestionDevolucionVenta;

	private IGestionArchivoFactura iArchivoFactura;
	private GestionFactura gestionFactura;

	private IGestionReporte iReporte;
	private GestionReporte gestionReporte;

	private GestionConsultas gestionConsultas;

	public TiendaConfig() {
		inicializarGestiones();
	}

	private void inicializarGestiones() {

		iProducto = new ProductoBDDAO();
		gestionProducto = new GestionProducto(iProducto);

		iCliente = new ClienteBDDAO();
		gestionCliente = new GestionCliente(iCliente);

		iProveedor = new ProveedorBDDAO();
		gestionProveedor = new GestionProveedor(iProveedor);

		iEmpleado = new EmpleadoBDDAO();
		gestionEmpleado = new GestionEmpleado(iEmpleado);

		iMovimientoInventario = new MovimientoInventarioBDDAO();
		gestionInventario = new GestionInventario(iProducto, iMovimientoInventario);

		iMovimientoContable = new MovimientoContableBDDAO();
		gestionContabilidad = new GestionContabilidad(iMovimientoContable);

		iVenta = new VentaBDDAO();
		gestionVenta = new GestionVenta(iVenta, gestionInventario, gestionContabilidad);

		iCompra = new ComprasBDDAO();
		gestionCompra = new GestionCompra(iCompra, gestionInventario, gestionContabilidad);

		iDevolucionVenta = new DevolucionVentaBDDAO();
		gestionDevolucionVenta = new GestionDevolucionVenta(iVenta, iDevolucionVenta, gestionInventario,
				gestionContabilidad);

		iArchivoFactura = new FacturaTxtDAO();
		gestionFactura = new GestionFactura(iArchivoFactura);

		iReporte = new ReporteJSONDAO();
		gestionReporte = new GestionReporte(iVenta, iReporte, iProducto, iCompra);

		gestionConsultas = new GestionConsultas(iVenta, iCompra, iProducto, iMovimientoContable);
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

	public GestionEmpleado getGestionEmpleado() {
		return gestionEmpleado;
	}

	public GestionInventario getGestionInventario() {
		return gestionInventario;
	}

	public GestionVenta getGestionVenta() {
		return gestionVenta;
	}

	public GestionCompra getGestionCompra() {
		return gestionCompra;
	}

	public GestionDevolucionVenta getGestionDevolucionVenta() {
		return gestionDevolucionVenta;
	}

	public GestionFactura getGestionFactura() {
		return gestionFactura;
	}

	public GestionReporte getGestionReporte() {
		return gestionReporte;
	}

	public GestionConsultas getGestionConsultas() {
		return gestionConsultas;
	}

	public GestionContabilidad getGestionContabilidad() {
		return gestionContabilidad;
	}
}
