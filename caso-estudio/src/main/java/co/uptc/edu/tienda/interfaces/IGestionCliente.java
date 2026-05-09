package co.uptc.edu.tienda.interfaces;

import java.util.List;
import co.uptc.edu.tienda.modelo.Cliente;

public interface IGestionCliente {

    public void guardar(List<Cliente> clientes);

    public void actualizar(Cliente cliente);

    public void eliminar(int codigoCliente);

    public Cliente buscar(int codigoCliente);

    public List<Cliente> leerClientes();
}