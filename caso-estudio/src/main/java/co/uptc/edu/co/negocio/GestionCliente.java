package co.uptc.edu.co.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionCliente;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class GestionCliente implements IGestionCliente {

    private List<Cliente> clientes;

    public GestionCliente() {
        clientes = new ArrayList<>();
    }

    public Cliente buscarClientePorCodigo(String codigo) {
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo().equalsIgnoreCase(codigo)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> obtenerClientes() {
        return new ArrayList<>(clientes);
    }

    public void registrarCliente(Cliente cliente) throws Exception {
        validarCliente(cliente);

        Cliente existente = buscarClientePorCodigo(cliente.getCodigo());

        if (existente != null) {
            throw new Exception("Ya existe un cliente con ese código.");
        }

        cliente.setEstado(EstadoEnum.ACTIVO);
        clientes.add(cliente);
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
    }

    public void actualizarCliente(Cliente clienteActualizado) throws Exception {
        validarCliente(clienteActualizado);

        Cliente clienteExistente = buscarClientePorCodigo(clienteActualizado.getCodigo());

        if (clienteExistente == null) {
            throw new Exception("No se encontró el cliente a actualizar.");
        }

        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setTipoIdentificacion(clienteActualizado.getTipoIdentificacion());
        clienteExistente.setNumeroIdentificacion(clienteActualizado.getNumeroIdentificacion());
        clienteExistente.setDireccion(clienteActualizado.getDireccion());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());
        clienteExistente.setTipoCliente(clienteActualizado.getTipoCliente());
    }

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
    }
}