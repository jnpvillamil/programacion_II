package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.caso.estudio.dao.ClienteDAOImpl;
import co.edu.uptc.interfaces.IGestionCliente;
import co.edu.uptc.negocio.dto.clienteDto;

public class LocalCliente implements IGestionCliente {

    private List<clienteDto> listaClientes = new ArrayList<>();
    private ClienteDAOImpl dao = new ClienteDAOImpl();
    private String ruta = "clientes.json";

    @Override 
    public void guardar(clienteDto cliente) { 
        listaClientes.add(cliente);
        dao.guardarClientes(listaClientes, ruta);
    }

    @Override
    public void actualizar(clienteDto cliente) {
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getCodigoCliente() == cliente.getCodigoCliente()) {
                listaClientes.set(i, cliente);
            }
        }
        dao.guardarClientes(listaClientes, ruta);
    }

    @Override
    public void eliminar(int codigo) {
        listaClientes.removeIf(c -> c.getCodigoCliente() == codigo);
        dao.guardarClientes(listaClientes, ruta);
    }

    @Override
    public clienteDto buscar(int codigo) {
        for (clienteDto c : listaClientes)
            if (c.getCodigoCliente() == codigo) return c;
        return null;
    }

    @Override 
    public List<clienteDto> listar() { 
        return listaClientes; 
    }
}