package co.edu.uptc.negocio;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.interfaces.RepositorioCliente;
import co.edu.uptc.modelo.Cliente;
import java.util.List;

public class GestionCliente {

    private final RepositorioCliente persistenciaCliente;

    public GestionCliente(RepositorioCliente persistenciaCliente) {
        this.persistenciaCliente = persistenciaCliente;
    }

    public void registrarCliente(Cliente cliente) {
        if (persistenciaCliente.buscarPorId(cliente.getIdentificacion()) != null) {
            throw new IllegalStateException("El número de identificación ya se encuentra registrado.");
        }
        persistenciaCliente.guardar(cliente);
    }

    public void actualizarCliente(Cliente clienteActualizado) {
        Cliente existente = persistenciaCliente.buscarPorId(clienteActualizado.getIdentificacion());
        if (existente == null) {
            throw new IllegalStateException("Cliente no encontrado.");
        }
        persistenciaCliente.eliminar(existente.getIdentificacion());
        persistenciaCliente.guardar(clienteActualizado);
    }

    public void inactivarCliente(String identificacion) {
        Cliente cliente = persistenciaCliente.buscarPorId(identificacion);
        if (cliente == null) {
            throw new IllegalStateException("Cliente no encontrado.");
        }
        cliente.setActivo(false);
        actualizarCliente(cliente);
    }

    public Cliente buscarCliente(String identificacion) {
        return persistenciaCliente.buscarPorId(identificacion);
    }

    public List<ClienteResumenDTO> listarResumen() {
        return persistenciaCliente.listarResumen();
    }
}
