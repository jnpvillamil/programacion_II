package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Cliente;

public class LocalCliente {

    private List<Cliente> clientes;

    public LocalCliente() {
        clientes = new ArrayList<>();
    }

    public boolean guardarCliente(Cliente cliente) {
        if (buscarCliente(cliente.getCodigo()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public Cliente buscarCliente(String codigoCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo().equalsIgnoreCase(codigoCliente)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean actualizarCliente(Cliente clienteActualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCodigo().equalsIgnoreCase(clienteActualizado.getCodigo())) {
                clientes.set(i, clienteActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarCliente(String codigoCliente) {
        Cliente cliente = buscarCliente(codigoCliente);
        if (cliente != null) {
            clientes.remove(cliente);
            return true;
        }
        return false;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}