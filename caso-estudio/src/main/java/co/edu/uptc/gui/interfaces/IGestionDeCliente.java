package co.edu.uptc.gui.interfaces;
import java.util.List;
import co.edu.uptc.gui.modelo.Cliente;

public interface IGestionDeCliente {
	
	public void guardarCliente(Cliente cliente);

	public void actualizarCliente(Cliente cliente);

	public void inactivarCliente(String codigoCliente);
	
	public void activarCliente(String codigoCliente);

	public Cliente buscarClientePorCodigo(String codigoCliente);
	
    public void ejecutarOperacionCliente(Cliente cliente);
    public List<Cliente> listarClientes();

	public List<Cliente> obtenerListaClientes();

}
