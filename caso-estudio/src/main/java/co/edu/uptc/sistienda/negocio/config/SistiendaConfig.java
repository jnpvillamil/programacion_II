package co.edu.uptc.sistienda.negocio.config;

import co.edu.uptc.sistienda.negocio.GestionCliente;
import co.edu.uptc.sistienda.negocio.GestionCompra;
import co.edu.uptc.sistienda.negocio.GestionContabilidad;
import co.edu.uptc.sistienda.negocio.GestionDeSeguridad;
import co.edu.uptc.sistienda.negocio.GestionEstudiante;
import co.edu.uptc.sistienda.negocio.GestionProducto;
import co.edu.uptc.sistienda.negocio.GestionProveedor;
import co.edu.uptc.sistienda.negocio.GestionReportes;
import co.edu.uptc.sistienda.negocio.GestionVenta;
import co.edu.uptc.sistienda.persistencia.ClienteBD;
import co.edu.uptc.sistienda.persistencia.ComprasBD;
import co.edu.uptc.sistienda.persistencia.ContabilidadDAO;
import co.edu.uptc.sistienda.persistencia.EstudianteBD;
import co.edu.uptc.sistienda.persistencia.ProductoBD;
import co.edu.uptc.sistienda.persistencia.ProveedorBD;
import co.edu.uptc.sistienda.persistencia.VentaBD;

public class SistiendaConfig {

	private GestionProducto gestionProducto;
	private GestionCliente gestionCliente;
	private GestionProveedor gestionProveedor;
	private GestionDeSeguridad gestionDeSeguridad;
	private GestionVenta gestionVenta;
	private GestionCompra gestionCompra;
	private GestionContabilidad gestionContabilidad;
	private GestionReportes gestionReportes;
	private GestionEstudiante gestionEstudiante;

	public SistiendaConfig() {
		gestionProducto = new GestionProducto(new ProductoBD());
		gestionCliente = new GestionCliente(new ClienteBD());
		gestionProveedor = new GestionProveedor(new ProveedorBD());
		gestionDeSeguridad = new GestionDeSeguridad();
		gestionContabilidad = new GestionContabilidad(new ContabilidadDAO());
		gestionReportes = new GestionReportes();
		gestionVenta = new GestionVenta(new VentaBD(), gestionProducto, gestionContabilidad);
		gestionCompra = new GestionCompra(new ComprasBD(), gestionProducto, gestionContabilidad);
		gestionEstudiante = new GestionEstudiante(new EstudianteBD());
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

	public GestionCompra getGestionCompra() {
		return gestionCompra;
	}

	public GestionContabilidad getGestionContabilidad() {
		return gestionContabilidad;
	}

	public GestionReportes getGestionReportes() {
		return gestionReportes;
	}
	public GestionEstudiante getGestionEstudiante() {
		return gestionEstudiante;
	}
}