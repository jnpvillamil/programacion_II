package co.edu.uptc.sistienda.negocio;

import java.util.List;

import co.edu.uptc.sistienda.interfaces.IGestionCliente;
import co.edu.uptc.sistienda.modelo.Cliente;

public class GestionCliente {

	private IGestionCliente repositorioCliente;

	public GestionCliente(IGestionCliente repositorioCliente) {
		this.repositorioCliente = repositorioCliente;
	}

	public void registrarNuevoCliente(Cliente cliente) throws Exception {
		if (cliente.getCodigoCliente() == null || cliente.getCodigoCliente().trim().isEmpty()) {
			throw new Exception("El código del cliente no puede estar vacío.");
		}
		if (cliente.getNombreCompletoORazonSocial() == null
				|| cliente.getNombreCompletoORazonSocial().trim().isEmpty()) {
			throw new Exception("El nombre o razón social del cliente no puede estar vacío.");
		}
		if (cliente.getNumeroIdentificacion() == null || cliente.getNumeroIdentificacion().trim().isEmpty()) {
			throw new Exception("El número de identificación no puede estar vacío.");
		}
		if (repositorioCliente.buscarClientePorCodigo(cliente.getCodigoCliente()) != null) {
			throw new Exception("Ya existe un cliente con el código: " + cliente.getCodigoCliente());
		}
		repositorioCliente.guardarCliente(cliente);
	}

	public void modificarCliente(Cliente cliente) throws Exception {
		if (repositorioCliente.buscarClientePorCodigo(cliente.getCodigoCliente()) == null) {
			throw new Exception("No se encontró el cliente con código: " + cliente.getCodigoCliente());
		}
		repositorioCliente.actualizarCliente(cliente);
	}

	public void inactivarCliente(String codigoCliente) throws Exception {
		if (repositorioCliente.buscarClientePorCodigo(codigoCliente) == null) {
			throw new Exception("No se encontró el cliente con código: " + codigoCliente);
		}
		repositorioCliente.inactivarCliente(codigoCliente);
	}
	public void activarCliente(String codigoCliente) throws Exception {
		if (repositorioCliente.buscarClientePorCodigo(codigoCliente) == null) {
			throw new Exception("No se encontró el cliente con código: " + codigoCliente);
		}
		repositorioCliente.activarCliente(codigoCliente);
	}

	public Cliente consultarClientePorCodigo(String codigoCliente) {
		return repositorioCliente.buscarClientePorCodigo(codigoCliente);
	}

	public List<Cliente> obtenerListaClientes() {
		return repositorioCliente.obtenerListaClientes();
	}
}
