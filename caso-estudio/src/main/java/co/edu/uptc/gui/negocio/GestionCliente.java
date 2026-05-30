package co.edu.uptc.gui.negocio;


import java.util.List;

import co.edu.uptc.gui.interfaces.ICrudCliente;
import co.edu.uptc.gui.modelo.Cliente;
import co.edu.uptc.persistencia.LocalCliente;

public class GestionCliente implements ICrudCliente {

    private LocalCliente localCliente;

    public GestionCliente() {
        localCliente = new LocalCliente();
    }

    public boolean registrarCliente(Cliente cliente) {
        return localCliente.guardarCliente(cliente);
    }

    public boolean modificarCliente(Cliente cliente) {
        return localCliente.actualizarCliente(cliente);
    }

    public boolean eliminarCliente(String codigoCliente) {
        return localCliente.eliminarCliente(codigoCliente);
    }

    public Cliente buscarCliente(String codigoCliente) {
        return localCliente.buscarCliente(codigoCliente);
    }

    public List<Cliente> listarClientes() {
        return localCliente.getClientes();
    }

	@Override
	public Cliente obtenerCliente(String idCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarDatosContacto(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

}
