package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Cliente;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCliente implements Repositorio<Cliente> {
    private List<Cliente> clientes;

    public PersistenciaCliente() {
        this.clientes = new ArrayList<>();
    }

    @Override
    public void guardar(Cliente cliente) {
        this.clientes.add(cliente);
    }

    @Override
    public void eliminar(String id) {
        this.clientes.removeIf(c -> c.getCodigoCliente().equals(id) || c.getIdentificacion().equals(id));
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(this.clientes);
    }

    @Override
    public Cliente buscarPorId(String identificacion) {
        for (Cliente c : this.clientes) {
            if (c.getIdentificacion().equals(identificacion)) {
                return c;
            }
        }
        return null;
    }
}