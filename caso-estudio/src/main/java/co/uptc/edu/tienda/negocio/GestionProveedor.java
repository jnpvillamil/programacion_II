package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.interfaces.IGestionProveedor;

import java.util.List;

public class GestionProveedor {
	
	private final IGestionProveedor gestionP;
	private final String RUTA = "proveedores.json";
	
	


	public GestionProveedor(IGestionProveedor gestionP) {
		super();
		this.gestionP = gestionP;
	}

	
	public void agregarProveedor(Proveedor nuevo) {
        // 1. Leer los que ya existen
        List<Proveedor> actuales = gestionP.leerProveedores(RUTA);
        
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
        gestionP.guardar(actuales, RUTA);
    }
	
	public void modificarProveedor(Proveedor proveedor) {
		gestionP.actualizar(proveedor);
	}
	
	public void eliminarProveedor(int proveedor) {
		gestionP.eliminar(proveedor);
	}
	
	public List<Proveedor> leerProveedores() {
		return gestionP.leerProveedores(RUTA);
	}
	
	public Proveedor buscarProveedorPorCodigo(int codigoProveedor) {
		return gestionP.buscar(codigoProveedor);
	}
}
