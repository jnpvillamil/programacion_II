package co.uptc.edu.co.interfaces;

import java.util.List;
import co.uptc.edu.co.modelo.Cliente;

public interface IGestionCliente {

    void guardar(Cliente cliente) throws Exception;

    void actualizar(Cliente cliente) throws Exception;

    Cliente buscar(String codigo) throws Exception;

    List<Cliente> listar() throws Exception;

    void cambiarEstado(String codigo) throws Exception;
}