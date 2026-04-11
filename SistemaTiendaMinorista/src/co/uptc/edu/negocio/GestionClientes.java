package co.uptc.edu.negocio;

import java.util.ArrayList;
import java.util.List;
import co.uptc.edu.modelo.Cliente;

public class GestionClientes {

    private List<Cliente> listaClientes;

    public GestionClientes() {
        listaClientes = new ArrayList<>();
    }

    // Registrar cliente
    public boolean registrarCliente(Cliente cliente) {

        if (buscarCliente(cliente.getCodigo()) != null) {
            return false; // ya existe
        }

        listaClientes.add(cliente);
        return true;
    }

    // Buscar cliente por código
    public Cliente buscarCliente(String codigo) {

        for (Cliente c : listaClientes) {
            if (c.getCodigo().equals(codigo)) {
                return c;
            }
        }
        return null;
    }

    // Modificar cliente
    public boolean modificarCliente(Cliente nuevoCliente){

        Cliente cliente = buscarCliente(nuevoCliente.getCodigo());

        if(cliente != null){

            cliente.setNombre(nuevoCliente.getNombre());
            cliente.setTipoIdentificacion(nuevoCliente.getTipoIdentificacion());
            cliente.setNumeroIdentificacion(nuevoCliente.getNumeroIdentificacion());
            cliente.setDireccion(nuevoCliente.getDireccion());
            cliente.setTelefono(nuevoCliente.getTelefono());
            cliente.setTipoCliente(nuevoCliente.getTipoCliente());

            return true;
        }

        return false;
    }

    // Inactivar cliente
    public boolean inactivarCliente(String codigo) {

        Cliente cliente = buscarCliente(codigo);

        if (cliente != null) {
            cliente.setActivo(false);
            return true;
        }
        return false;
    }

    // Listar clientes
    public List<Cliente> obtenerClientes() {
        return listaClientes;
    }
}