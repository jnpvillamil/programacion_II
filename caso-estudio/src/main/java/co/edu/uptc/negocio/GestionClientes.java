package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.persistencia.PersistenciaCliente;

import java.util.List;

public class GestionClientes {

    private Repositorio<Cliente> repositorioCliente;

    public GestionClientes() {
        this.repositorioCliente = new PersistenciaCliente();
    }

    public boolean registrarCliente(Cliente cliente) {
        if (repositorioCliente.buscarPorId(cliente.getIdentificacion()) != null) {
            return false; 
        }
        repositorioCliente.guardar(cliente);
        return true;
    }

    public boolean actualizarCliente(Cliente cliente) {
        if (repositorioCliente.buscarPorId(cliente.getCodigoCliente()) == null) {
            return false;
        }
        repositorioCliente.actualizar(cliente);
        return true;
    }

    public boolean inactivarCliente(String codigoCliente) {
        Cliente cliente = (Cliente) repositorioCliente.buscarPorId(codigoCliente);
        if (cliente != null) {
            cliente.setActivo(false);
            repositorioCliente.actualizar(cliente);
            return true;
        }
        return false;
    }

    public Cliente buscarCliente(String criterioBusqueda) {
        return repositorioCliente.buscarPorId(criterioBusqueda);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return repositorioCliente.listar();
    }
}