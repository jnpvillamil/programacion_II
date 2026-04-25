package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.interfaces.IGestionProveedor;
import java.util.List;

public class GestionProveedor {
	
	private IGestionProveedor gestionP;
	
	


	public GestionProveedor(IGestionProveedor gestionP) {
		super();
		this.gestionP = gestionP;
	}



	public void agregarProveedor(Proveedor proveedor) {
		gestionP.guardar(proveedor);
	}
	
	public void modificarProveedor(Proveedor proveedor) {
		gestionP.actualizar(proveedor);
	}
	
	public void eliminarProveedor(int proveedor) {
		gestionP.eliminar(proveedor);
	}
	
	public List<Proveedor> listar() {
		return gestionP.listar();
	}
	
	public Proveedor buscarProveedorPorCodigo(int codigoProveedor) {
		return gestionP.buscar(codigoProveedor);
	}
}
