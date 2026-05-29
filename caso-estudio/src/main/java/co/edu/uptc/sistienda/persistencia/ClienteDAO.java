package co.edu.uptc.sistienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.sistienda.interfaces.IGestionCliente;
import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.enums.TipoClienteEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoIdentificacionEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoPersonaEnum;

public class ClienteDAO implements IGestionCliente {

	private static final String RUTA_ARCHIVO = "datos/clientes.json";
	private List<Cliente> listaClientes;
	private final Gson gson;

	public ClienteDAO() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		listaClientes = cargarDesdeArchivo();
	}

	private List<Cliente> cargarDesdeArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		if (!archivo.exists() || archivo.length() == 0) {
			return new ArrayList<>();
		}
		try (FileReader reader = new FileReader(archivo)) {
			Type tipoLista = new TypeToken<List<Cliente>>() {
			}.getType();
			List<Cliente> lista = gson.fromJson(reader, tipoLista);
			return lista != null ? lista : new ArrayList<>();
		} catch (IOException e) {
			System.err.println("Error al leer cliente.json: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	private void guardarEnArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		try (FileWriter writer = new FileWriter(archivo)) {
			gson.toJson(listaClientes, writer);

		} catch (IOException e) {
			System.err.println("Error al guardar clientes.json: " + e.getMessage());
		}
	}

	@Override
	public void guardarCliente(Cliente cliente) {
		listaClientes.add(cliente);
		guardarEnArchivo();
	}

	@Override
	public void actualizarCliente(Cliente clienteActualizado) {
		Cliente existente = buscarClientePorCodigo(clienteActualizado.getCodigoCliente());
		if (existente != null) {
			existente.setNombreCompletoORazonSocial(clienteActualizado.getNombreCompletoORazonSocial());
			existente.setTipoIdentificacion(clienteActualizado.getTipoIdentificacion());
			existente.setNumeroIdentificacion(clienteActualizado.getNumeroIdentificacion());
			existente.setDireccion(clienteActualizado.getDireccion());
			existente.setCiudad(clienteActualizado.getCiudad());
			existente.setTelefono(clienteActualizado.getTelefono());
			existente.setCorreoElectronico(clienteActualizado.getCorreoElectronico());
			existente.setTipoCliente(clienteActualizado.getTipoCliente());
			existente.setTipoPersona(clienteActualizado.getTipoPersona());
			existente.setResponsabilidadFiscal(clienteActualizado.getResponsabilidadFiscal());
			existente.setResponsabilidadTributaria(clienteActualizado.getResponsabilidadTributaria());
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