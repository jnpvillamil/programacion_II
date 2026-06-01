package co.uptc.edu.co.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.interfaces.dao.ClienteDAO;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class GestionCliente implements IGestionCliente {

	private List<Cliente> clientes;

	private final ClienteDAO clienteDAO;

	public GestionCliente(ClienteDAO clienteDAO) {

		if (clienteDAO == null) {

			throw new IllegalArgumentException("El ClienteDAO no puede ser nulo.");
		}

		this.clienteDAO = clienteDAO;

		try {

			clientes = clienteDAO.listarClientes();

		} catch (Exception e) {

			clientes = new ArrayList<>();

			throw new IllegalStateException("Error al cargar clientes.", e);
		}
	}

	@Override
	public Cliente buscarClientePorCodigo(String codigo) {

		for (Cliente cliente : clientes) {

			if (cliente.getCodigo().equalsIgnoreCase(codigo)) {

				return cliente;
			}
		}

		return null;
	}

	@Override
	public List<Cliente> obtenerClientes() {

		return new ArrayList<>(clientes);
	}

	@Override
	public void registrarCliente(Cliente cliente) throws Exception {

		validarCliente(cliente);

		Cliente existente = buscarClientePorCodigo(cliente.getCodigo());

		if (existente != null) {

			throw new Exception("Ya existe un cliente con ese código.");
		}

		if (existeIdentificacionDuplicada(cliente)) {
			throw new Exception("No se puede registrar: número de identificación duplicado (" 
					+ cliente.getTipoIdentificacion() + "-" + cliente.getNumeroIdentificacion() + ").");
		}

		cliente.setEstado(EstadoEnum.ACTIVO);

		clienteDAO.guardarCliente(cliente);

		clientes.add(cliente);
	}
	
	@Override
	public void actualizarCliente(Cliente clienteActualizado) throws Exception {

		validarCliente(clienteActualizado);

		Cliente clienteExistente = buscarClientePorCodigo(clienteActualizado.getCodigo());

		if (clienteExistente == null) {

			throw new Exception("No se encontró el cliente a actualizar.");
		}

		if (existeIdentificacionDuplicada(clienteActualizado)) {
			throw new Exception("No se puede actualizar: número de identificación duplicado (" 
					+ clienteActualizado.getTipoIdentificacion() + "-" + clienteActualizado.getNumeroIdentificacion() + ").");
		}

		clienteDAO.actualizarCliente(clienteActualizado);

		clienteExistente.setNombre(clienteActualizado.getNombre());

		clienteExistente.setTipoIdentificacion(clienteActualizado.getTipoIdentificacion());

		clienteExistente.setNumeroIdentificacion(clienteActualizado.getNumeroIdentificacion());

		clienteExistente.setDireccion(clienteActualizado.getDireccion());

		clienteExistente.setTelefono(clienteActualizado.getTelefono());

		clienteExistente.setTipoCliente(clienteActualizado.getTipoCliente());

	}

	private boolean existeIdentificacionDuplicada(Cliente cliente) {
		if (cliente == null || cliente.getNumeroIdentificacion() == null || cliente.getTipoIdentificacion() == null) {
			return false;
		}

		String numero = cliente.getNumeroIdentificacion().trim();
		for (Cliente c : clientes) {
			if (c == null) {
				continue;
			}
			// Ignorar el mismo cliente por código
			if (c.getCodigo() != null && cliente.getCodigo() != null
					&& c.getCodigo().equalsIgnoreCase(cliente.getCodigo())) {
				continue;
			}

			if (c.getTipoIdentificacion() == cliente.getTipoIdentificacion()
					&& c.getNumeroIdentificacion() != null
					&& c.getNumeroIdentificacion().trim().equalsIgnoreCase(numero)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void cambiarEstadoCliente(String codigo) throws Exception {

		Cliente cliente = buscarClientePorCodigo(codigo);

		if (cliente == null) {

			throw new Exception("No se encontró el cliente.");
		}

		if (cliente.estaActivo()) {

			cliente.setEstado(EstadoEnum.INACTIVO);

		} else {

			cliente.setEstado(EstadoEnum.ACTIVO);
		}

		clienteDAO.actualizarCliente(cliente);
	}
	@Override
	public String generarCodigoCliente() {
		try {
			clientes = clienteDAO.listarClientes();
		} catch (Exception e) {
			throw new IllegalStateException("Error al recargar clientes para generar codigo.", e);
		}

		int mayor = 0;

		for (Cliente cliente : clientes) {
			String codigo = cliente.getCodigo();

			if (codigo != null && codigo.matches("CLIT\\d{4}")) {
				int numero = Integer.parseInt(codigo.substring(4));

				if (numero > mayor) {
					mayor = numero;
				}
			}
		}    

		return String.format("CLIT%04d", mayor + 1);

	}
	private void validarCliente(Cliente cliente) throws Exception {

		if (cliente == null) {

			throw new Exception("El cliente no puede ser nulo.");
		}

		if (cliente.getCodigo() == null || cliente.getCodigo().trim().isEmpty()) {

			throw new Exception("El código es obligatorio.");
		}

		if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {

			throw new Exception("El nombre es obligatorio.");
		}

		if (cliente.getTipoIdentificacion() == null) {

			throw new Exception("El tipo de identificación es obligatorio.");
		}

		if (cliente.getNumeroIdentificacion() == null || cliente.getNumeroIdentificacion().trim().isEmpty()) {

			throw new Exception("El número de identificación es obligatorio.");
		}

		if (cliente.getDireccion() == null || cliente.getDireccion().trim().isEmpty()) {

			throw new Exception("La dirección es obligatoria.");
		}

		if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {

			throw new Exception("El teléfono es obligatorio.");
		}

		if (cliente.getTipoCliente() == null) {

			throw new Exception("El tipo de cliente es obligatorio.");
		}
		
		
		 validarNumeroIdentificacion(cliente);

		    validarDireccion(cliente.getDireccion());

		    validarTelefono(cliente.getTelefono());
	}
	
	
	private void validarNumeroIdentificacion(Cliente cliente)
	        throws Exception {

	    String numero = cliente.getNumeroIdentificacion();

	    if (numero == null || numero.trim().isEmpty()) {

	        throw new Exception(
	            "El número de identificación es obligatorio."
	        );
	    }
	    
	    switch (cliente.getTipoIdentificacion()) {

	        case CC:

	            if (!numero.matches("\\d{7,10}")) {

	                throw new Exception(
	                    "La Cédula de Ciudadanía debe contener entre 7 y 10 dígitos."
	                );
	            }

	            break;

	        case NIT:

	            if (!numero.matches("\\d{8,15}")) {

	                throw new Exception(
	                    "El NIT debe contener entre 8 y 15 dígitos."
	                );
	            }

	            break;

	        case CE:

	            if (!numero.matches("\\d{6,12}")) {

	                throw new Exception(
	                    "La Cédula de Extranjería debe contener entre 6 y 12 dígitos."
	                );
	            }

	            break;

	        case PA:

	            if (!numero.matches("[A-Za-z0-9]{5,20}")) {

	                throw new Exception(
	                    "El Pasaporte debe contener entre 5 y 20 caracteres alfanuméricos."
	                );
	            }

	            break;

	        default:

	            throw new Exception(
	                "Tipo de identificación no válido."
	            );
	    }
	}

	private void validarDireccion(String direccion)
	        throws Exception {

	    if (direccion == null ||
	        direccion.trim().isEmpty()) {

	        throw new Exception(
	            "La dirección es obligatoria."
	        );
	    }

	    if (direccion.trim().length() < 5) {

	        throw new Exception(
	            "La dirección debe tener al menos 5 caracteres."
	        );
	    }
	}

	private void validarTelefono(String telefono)
	        throws Exception {

	    if (telefono == null ||
	        telefono.trim().isEmpty()) {

	        throw new Exception(
	            "El teléfono es obligatorio."
	        );
	    }

	    if (!telefono.matches("^3\\d{9}$")) {

	        throw new Exception(
	            "El teléfono debe iniciar por 3 y tener exactamente 10 dígitos."
	        );
	    }
	}
	
	
	
	
	}
