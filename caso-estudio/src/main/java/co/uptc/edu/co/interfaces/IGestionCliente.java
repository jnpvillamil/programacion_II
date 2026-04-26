package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Cliente;

public interface IGestionCliente {

    Cliente buscarClientePorCodigo(String codigo);

    List<Cliente> obtenerClientes();

    void registrarCliente(Cliente cliente) throws Exception;

    void actualizarCliente(Cliente clienteActualizado) throws Exception;

    void cambiarEstadoCliente(String codigo) throws Exception;
}