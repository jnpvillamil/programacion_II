package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.interfaces.IGestionCliente;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.Proveedor;

import java.util.List;

public class GestionCliente {

    private final IGestionCliente gestionC;

    public GestionCliente(IGestionCliente gestionC) {
        this.gestionC = gestionC;
    }

    public void agregarCliente(Cliente nuevo) throws Exception {
        if (nuevo.getNombreCompleto().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }
        if (nuevo.getTelefonoC() <= 0) {
            throw new Exception("El teléfono debe ser un número positivo");
        }
        // BD asigna el ID automáticamente con AUTO_INCREMENT
        gestionC.guardar(nuevo);
    }

    public void modificarCliente(Cliente cliente) throws Exception {
        Cliente existente = gestionC.buscar(cliente.getIdCliente());
        if (existente == null) {
            throw new Exception("El cliente no existe");
        }
        gestionC.actualizar(cliente);
    }

    public void eliminarCliente(int codigoCliente) throws Exception {
        Cliente existente = gestionC.buscar(codigoCliente);
        if (existente == null) {
            throw new Exception("El cliente no existe");
        }
        if (existente.getEstado() == EstadoEnum.INACTIVO) {
            throw new Exception("El cliente ya está inactivo");
        }
        gestionC.eliminar(codigoCliente);
    }

    public List<Cliente> leerClientes() {
        return gestionC.leerClientes();
    }

    public Cliente buscarClientePorCodigo(int codigoCliente) {
        return gestionC.buscar(codigoCliente);
    }
  
    public void activarCliente (int codigo) throws Exception {
	    // 1. Lógica de negocio: ¿Existe el proveedor?
	    Cliente c = gestionC.buscar(codigo);
	    if (c == null) {
	        throw new Exception("El cliente no existe");
	    }
	    
	    // 2. ¿Ya está activo? 
	    if (c.getEstado() == EstadoEnum.ACTIVO) {
	        throw new Exception("El cliente ya se encuentra activo");
	    }

	    // 3. Mandar a guardar el cambio
	    gestionC.cambiarEstado(codigo, EstadoEnum.ACTIVO);
	}
}