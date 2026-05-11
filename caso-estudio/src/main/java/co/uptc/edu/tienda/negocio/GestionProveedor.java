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

	
	public void agregarProveedor(Proveedor nuevo) throws Exception{
        // 1. Leer los que ya existen
		
		if(nuevo.getRazonSocial().trim().isEmpty()) {
			throw new Exception("La razón social es obligatoria");
		}
		if(nuevo.getTelefonoP()<= 0) {
			throw new Exception("El teléfono debe ser un número positivo");
		}
		
        List<Proveedor> actuales = gestionP.leerProveedores();
        
        int maxId = 99;
        if (actuales.size() > 0) {
            for (int i = 0; i < actuales.size(); i++) {
                if (actuales.get(i).getCodigoProveedor() > maxId) {
                    maxId = actuales.get(i).getCodigoProveedor();
                }
            }
        }

        // 2. FORZAR el código consecutivo real basado en el archivo
        // Esto ignora si el contador de la RAM saltó números
        int idReal = maxId + 1;
        
        // Necesitas un setter en Proveedor para esta línea:
        nuevo.setCodigoProveedor(idReal); 
        
        // 3. Sincronizar el contador global para el futuro
        Proveedor.setContador(idReal);

        // 4. Agregar y Guardar
        actuales.add(nuevo);
        gestionP.guardar(actuales);
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
