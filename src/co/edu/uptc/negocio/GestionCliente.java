package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Cliente;
import java.util.List;

public class GestionCliente {
    private Repositorio<Cliente> persistenciaCliente;

    public GestionCliente(Repositorio<Cliente> persistenciaCliente) {
        this.persistenciaCliente = persistenciaCliente;
    }

    public boolean registrarCliente(Cliente cliente) throws Exception {
        if (this.persistenciaCliente.buscarPorId(cliente.getIdentificacion()) != null) {
            throw new Exception("El número de identificación ya se encuentra registrado.");
        }
        this.persistenciaCliente.guardar(cliente);
        return true;
    }

    public boolean actualizarCliente(Cliente clienteActualizado) {
        Cliente existente = this.persistenciaCliente.buscarPorId(clienteActualizado.getIdentificacion());
        if (existente != null) {
            this.persistenciaCliente.eliminar(existente.getIdentificacion());
            this.persistenciaCliente.guardar(clienteActualizado);
            return true;
        }
        return false;
    }

    public boolean inactivarCliente(String identificacion) {
        Cliente cliente = this.persistenciaCliente.buscarPorId(identificacion);
        if (cliente != null) {
            cliente.setActivo(false);
            actualizarCliente(cliente);
            return true;
        }
        return false;
    }

    public Cliente buscarCliente(String identificacion) {
        return this.persistenciaCliente.buscarPorId(identificacion);
    }

    public List<Cliente> listarTodos() {
        return this.persistenciaCliente.listar();
    }
}