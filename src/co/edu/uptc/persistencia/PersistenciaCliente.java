package co.edu.uptc.persistencia;

import co.edu.uptc.negocio.Cliente;
import java.util.ArrayList;

public class PersistenciaCliente {
    private ArrayList<Cliente> listaClientes;

    public PersistenciaCliente() {
        this.listaClientes = new ArrayList<>();
    }

    public void crear(Cliente cliente) {
        this.listaClientes.add(cliente);
    }

    public ArrayList<Cliente> leerTodos() {
        return this.listaClientes;
    }

    public void actualizar(String codigo, Cliente clienteActualizado) {
        for (int i = 0; i < listaClientes.size(); i++) {
        
            if (listaClientes.get(i).getCodigo().equals(codigo)) {
                listaClientes.set(i, clienteActualizado);
                break;
            }
        }
    }

    public void eliminar(String codigo) {
        listaClientes.removeIf(cliente -> cliente.getCodigo().equals(codigo));
    }
}