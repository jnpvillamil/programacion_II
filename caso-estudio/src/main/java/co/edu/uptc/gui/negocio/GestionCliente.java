package co.edu.uptc.gui.negocio;

import co.edu.uptc.gui.interfaces.RF08_RegistrarCliente;
import co.edu.uptc.gui.interfaces.RF09_AsignarTipoCliente;
import co.edu.uptc.gui.interfaces.RF10_ActualizarDatosContacto;
import co.edu.uptc.gui.interfaces.RF11_ValidarDuplicidadCedula;
import co.edu.uptc.gui.interfaces.RF12_EliminarHistorialCliente;
import co.edu.uptc.gui.modelo.Cliente;
import co.edu.uptc.persistencia.LocalCliente; // O tu ClienteDao según uses local/remoto
import java.util.List;

public class GestionCliente implements 
    RF08_RegistrarCliente, 
    RF09_AsignarTipoCliente, 
    RF10_ActualizarDatosContacto, 
    RF11_ValidarDuplicidadCedula, 
    RF12_EliminarHistorialCliente {

    private LocalCliente localCliente; 

    public GestionCliente() {
        this.localCliente = new LocalCliente();
    }

    @Override
    public void ejecutarOperacionCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }

        if (cliente.getCedula() == null || cliente.getCedula().trim().isEmpty()) {
            throw new IllegalArgumentException("Error: La cédula es obligatoria.");
        }

        if (existeCedula(cliente.getCedula())) {
            localCliente.guardar(cliente); 
        } else {
            localCliente.guardar(cliente); 
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        return localCliente.leer();
    }

    @Override
    public boolean existeCedula(String cedula) {
        List<Cliente> clientes = listarClientes();
        if (clientes != null) {
            for (Cliente c : clientes) {
                if (c.getCedula().equals(cedula)) {
                    return true;
                }
            }
        }
        return false;
    }

	public boolean registrarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean modificarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean eliminarCliente(String codigo) {
		// TODO Auto-generated method stub
		return false;
	}

	public Cliente buscarCliente(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registrarClienteLocal(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}
}
