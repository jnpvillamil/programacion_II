package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Cliente;

public interface ClienteDAO {

    void guardarCliente(Cliente cliente) throws Exception;

    void actualizarCliente(Cliente cliente) throws Exception;

    Cliente buscarPorCodigo(String codigo) throws Exception;

    List<Cliente> listarClientes() throws Exception;
}

