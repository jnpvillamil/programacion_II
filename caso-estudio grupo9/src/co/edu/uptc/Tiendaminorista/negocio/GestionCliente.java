package co.edu.uptc.Tiendaminorista.negocio;
import co.edu.uptc.Tiendaminorista.interfaces.*;
import co.edu.uptc.Tiendaminorista.modelo.Cliente;

public class GestionCliente {
	private IGestionCliente clientes;

	public GestionCliente(IGestionCliente Clientes) {
		super();
		this.clientes = Clientes;
	}

	public void agregarCliente(Cliente cliente) {
		clientes.guardar(cliente);
	}

	public void actualizarCliente(Cliente cliente) {
		clientes.actualizar(cliente);
	}

	public void desactivarCliente(String codigo) {
		clientes.desactivar(codigo);
	}

	public void activarCliente(String codigo) {
		clientes.activar(codigo);
	}

	public java.util.List<Cliente> listarClientes() {
		return clientes.listar();
	}
}
