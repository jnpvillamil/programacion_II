package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Cliente;
import java.util.List;

public class GestionCliente {
    private Repositorio<Cliente> persistenciaCliente;

    public GestionCliente(Repositorio<Cliente> persistenciaCliente) {
        this.persistenciaCliente = persistenciaCliente;
    }

    public boolean registrarCliente(Cliente cliente) {
        if (this.persistenciaCliente.buscarPorId(cliente.getCodigoCliente()) != null) {
            return false;
        }
        this.persistenciaCliente.guardar(cliente);
        return true;
    }

    public void actualizarCliente(Cliente cliente) {
        Cliente existente = this.persistenciaCliente.buscarPorId(cliente.getCodigoCliente());
        if (existente != null) {
            this.persistenciaCliente.eliminar(existente.getCodigoCliente());
            this.persistenciaCliente.guardar(cliente);
        }
    }

    public Cliente buscarCliente(String codigoCliente) {
        return this.persistenciaCliente.buscarPorId(codigoCliente);
    }

    public List<Cliente> listarClientes() {
        return this.persistenciaCliente.listar();
    }
}