package co.edu.uptc.sistienda.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.sistienda.interfaces.IGestionCliente;
import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.enums.TipoClienteEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoIdentificacionEnum;

public class LocalCliente implements IGestionCliente {

	private List<Cliente> listaClientes;

	public LocalCliente() {
		listaClientes = new ArrayList<>();
		cargarDatosIniciales();
	}

	private void cargarDatosIniciales() {
		Cliente c1 = new Cliente();
		c1.setCodigoCliente("C001");
		c1.setNombreCompletoORazonSocial("Juan Pérez García");
		c1.setTipoIdentificacion(TipoIdentificacionEnum.CC);
		c1.setNumeroIdentificacion("1012345678");
		c1.setDireccion("Calle 10 # 5-20, Bogotá");
		c1.setTelefono("3101234567");
		c1.setTipoCliente(TipoClienteEnum.MINORISTA);

		Cliente c2 = new Cliente();
		c2.setCodigoCliente("C002");
		c2.setNombreCompletoORazonSocial("Distribuidora El Sol S.A.S");
		c2.setTipoIdentificacion(TipoIdentificacionEnum.NIT);
		c2.setNumeroIdentificacion("900123456-1");
		c2.setDireccion("Av. 30 # 45-60, Bogotá");
		c2.setTelefono("3209876543");
		c2.setTipoCliente(TipoClienteEnum.MAYORISTA);

		listaClientes.add(c1);
		listaClientes.add(c2);
	}

	@Override
	public void guardarCliente(Cliente cliente) {
		listaClientes.add(cliente);
	}

	@Override
	public void actualizarCliente(Cliente clienteActualizado) {
		Cliente existente = buscarClientePorCodigo(clienteActualizado.getCodigoCliente());
		if (existente != null) {
			existente.setNombreCompletoORazonSocial(clienteActualizado.getNombreCompletoORazonSocial());
			existente.setTipoIdentificacion(clienteActualizado.getTipoIdentificacion());
			existente.setNumeroIdentificacion(clienteActualizado.getNumeroIdentificacion());
			existente.setDireccion(clienteActualizado.getDireccion());
			existente.setTelefono(clienteActualizado.getTelefono());
			existente.setTipoCliente(clienteActualizado.getTipoCliente());
		}
	}

	@Override
	public void inactivarCliente(String codigoCliente) {
		Cliente existente = buscarClientePorCodigo(codigoCliente);
		if (existente != null) {
			existente.setActivo(false);
		}
	}
	@Override
	public void activarCliente(String codigoCliente) {
		Cliente existente = buscarClientePorCodigo(codigoCliente);
		if (existente != null) {
			existente.setActivo(true);
		}
	}

	@Override
	public Cliente buscarClientePorCodigo(String codigoCliente) {
		for (Cliente cliente : listaClientes) {
			if (cliente.getCodigoCliente().equalsIgnoreCase(codigoCliente)) {
				return cliente;
			}
		}
		return null;
	}

	@Override
	public List<Cliente> obtenerListaClientes() {
		return listaClientes;
	}
}
