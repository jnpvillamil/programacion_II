package co.edu.uptc.sistienda.interfaces;

import java.util.List;
import co.edu.uptc.sistienda.modelo.Cliente;

public interface IGestionCliente {

	public void guardarCliente(Cliente cliente);

	public void actualizarCliente(Cliente cliente);

	public void inactivarCliente(String codigoCliente);
	
	public void activarCliente(String codigoCliente);

	public Cliente buscarClientePorCodigo(String codigoCliente);

	public List<Cliente> obtenerListaClientes();
}
