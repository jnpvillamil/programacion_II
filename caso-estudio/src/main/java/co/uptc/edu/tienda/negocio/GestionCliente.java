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

        // ✅ nombre correcto (viene de Persona)
        if (nuevo.getNombreCompleto().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }

        if (nuevo.getTelefonoC() <= 0) {
            throw new Exception("El teléfono debe ser un número positivo");
        }

        List<Cliente> actuales = gestionC.leerClientes();

        int maxId = 99;

        if (!actuales.isEmpty()) {
            for (int i = 0; i < actuales.size(); i++) {
                if (actuales.get(i).getIdCliente() > maxId) {
                    maxId = actuales.get(i).getIdCliente();
                }
            }
        }

        int idReal = maxId + 1;

        nuevo.setIdCliente(idReal);

        Cliente.setContador(idReal);

        actuales.add(nuevo);

        gestionC.guardar(actuales);
    }

    public void modificarCliente(Cliente cliente) {
        gestionC.actualizar(cliente);
    }

    public void eliminarCliente(int codigoCliente) {
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
	        throw new Exception("El proveedor no existe");
	    }
	    
	    // 2. ¿Ya está activo? 
	    if (c.getEstado() == EstadoEnum.ACTIVO) {
	        throw new Exception("El proveedor ya se encuentra activo");
	    }

	    // 3. Mandar a guardar el cambio
	    gestionC.cambiarEstado(codigo, EstadoEnum.ACTIVO);
	}
}