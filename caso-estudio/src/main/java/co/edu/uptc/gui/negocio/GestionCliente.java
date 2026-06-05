package co.edu.uptc.gui.negocio;

import co.edu.uptc.gui.interfaces.IGestionDeCliente;
import co.edu.uptc.gui.modelo.Cliente;
import co.edu.uptc.persistencia.LocalCliente;
import java.util.List;

public class GestionCliente implements IGestionDeCliente {

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
            throw new IllegalArgumentException("La cédula es obligatoria.");
        }

        if (existeCedula(cliente.getCedula())) {
            localCliente.actualizarCliente(cliente); 
        } else {
            localCliente.guardar(cliente); 
        }
    }


    public List<Cliente> listarClientes() {
        return localCliente.leer();
    }


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
        if (cliente == null) return false;
        localCliente.guardar(cliente);
        return true;
    }


    public boolean modificarCliente(Cliente cliente) {
        if (cliente == null) return false;
        localCliente.actualizarCliente(cliente);
        return true;
    }


    public boolean eliminarCliente(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) return false;
        return localCliente.eliminarCliente(codigo);
    }


    public Cliente buscarCliente(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) return null;
        return localCliente.buscarPorCodigo(codigo);
    }


    public void inactivarCliente(String codigoCliente) {
        Cliente c = buscarCliente(codigoCliente);
        if (c != null) {
            c.setActivo(false);
            localCliente.actualizarCliente(c);
        }
    }

    @Override
    public void activarCliente(String codigoCliente) {
        Cliente c = buscarCliente(codigoCliente);
        if (c != null) {
            c.setActivo(true);
            localCliente.actualizarCliente(c);
        }
    }

	@Override
	public void guardarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente buscarClientePorCodigo(String codigoCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> obtenerListaClientes() {
		// TODO Auto-generated method stub
		return null;
	}
}