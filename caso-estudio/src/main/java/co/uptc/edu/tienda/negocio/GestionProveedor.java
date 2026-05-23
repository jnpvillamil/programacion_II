package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.interfaces.IGestionProveedor;
import co.uptc.edu.tienda.modelo.Proveedor;

import java.util.List;

public class GestionProveedor {
	
	private final IGestionProveedor gestionP;
	
	
	


	public GestionProveedor(IGestionProveedor gestionP) {
		super();
		this.gestionP = gestionP;
	}

	
	public void agregarProveedor(Proveedor nuevo) throws Exception {
	    if (nuevo.getRazonSocial().trim().isEmpty()) {
	        throw new Exception("La razón social es obligatoria");
	    }
	    if (nuevo.getTelefonoP() <= 0) {
	        throw new Exception("El teléfono debe ser un número positivo");
	    }

	    // Consecutivo sigue siendo necesario para JSON
	    // En SQL esto desaparece
	    List<Proveedor> actuales = gestionP.leerProveedores();
	    int maxId = 99;
	    for (Proveedor p : actuales) {
	        if (p.getCodigoProveedor() > maxId) maxId = p.getCodigoProveedor();
	    }
	    nuevo.setCodigoProveedor(maxId + 1);
	    Proveedor.setContador(maxId + 1);

	    gestionP.guardar(nuevo); //
	}
	
	public void modificarProveedor(Proveedor proveedor) {
		gestionP.actualizar(proveedor);
	}
	
	public void eliminarProveedor(int proveedor) {
		gestionP.eliminar(proveedor);
	}
	
	public List<Proveedor> leerProveedores() {
		return gestionP.leerProveedores();
	}
	
	public Proveedor buscarProveedorPorCodigo(int codigoProveedor) {
		return gestionP.buscar(codigoProveedor);
	}
	
	public void activarProveedor(int codigo) throws Exception {
	    // 1. Lógica de negocio: ¿Existe el proveedor?
	    Proveedor p = gestionP.buscar(codigo);
	    if (p == null) {
	        throw new Exception("El proveedor no existe");
	    }
	    
	    // 2. ¿Ya está activo? 
	    if (p.getEstado() == EstadoEnum.ACTIVO) {
	        throw new Exception("El proveedor ya se encuentra activo");
	    }

	    // 3. Mandar a guardar el cambio
	    gestionP.cambiarEstado(codigo, EstadoEnum.ACTIVO);
	}
}