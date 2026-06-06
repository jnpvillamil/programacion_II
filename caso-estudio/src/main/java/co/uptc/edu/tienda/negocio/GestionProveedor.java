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
	    if (nuevo.getNit().trim().isEmpty()) {
	        throw new Exception("El NIT es obligatorio");
	    }
	    if (nuevo.getTelefonoP() <= 0) {
	        throw new Exception("El teléfono debe ser un número positivo");
	    }
	    gestionP.guardar(nuevo);
	}

	public void modificarProveedor(Proveedor proveedor) throws Exception {
	    Proveedor existente = gestionP.buscar(proveedor.getCodigoProveedor());
	    if (existente == null) {
	        throw new Exception("El proveedor no existe");
	    }
	    gestionP.actualizar(proveedor);
	}

	public void eliminarProveedor(int codigo) throws Exception {
	    Proveedor existente = gestionP.buscar(codigo);
	    if (existente == null) {
	        throw new Exception("El proveedor no existe");
	    }
	    if (existente.getEstado() == EstadoEnum.INACTIVO) {
	        throw new Exception("El proveedor ya está inactivo");
	    }
	    gestionP.eliminar(codigo);
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