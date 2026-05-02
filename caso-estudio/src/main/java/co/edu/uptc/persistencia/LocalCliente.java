package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.interfaces.IGestionCliente;
import co.edu.uptc.negocio.dto.clienteDto;

public class LocalCliente implements IGestionCliente {

    private List<clienteDto> listaClientes = new ArrayList<>();

    @Override public void guardar(clienteDto cliente) { listaClientes.add(cliente); }

    @Override
    public void actualizar(clienteDto cliente) {
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getCodigoCliente() == cliente.getCodigoCliente()) {
                listaClientes.set(i, cliente); return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        listaClientes.removeIf(c -> c.getCodigoCliente() == codigo);
    }

    @Override
    public clienteDto buscar(int codigo) {
        for (clienteDto c : listaClientes)
            if (c.getCodigoCliente() == codigo) return c;
        return null;
    }

    @Override public List<clienteDto> listar() { return listaClientes; }
}
