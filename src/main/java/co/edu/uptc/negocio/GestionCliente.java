package co.edu.uptc.negocio;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.interfaces.RepositorioCliente;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.util.List;

public class GestionCliente {

    private final RepositorioCliente persistenciaCliente;

    public GestionCliente(RepositorioCliente persistenciaCliente) {
        this.persistenciaCliente = persistenciaCliente;
    }

    public void registrarCliente(Cliente cliente) {
        validarDatosObligatorio(cliente);

        if (persistenciaCliente.existeIdentificacion(cliente.getIdentificacion())) {
            throw new IllegalStateException("El número de identificación ya se encuentra registrado.");
        }
        if (persistenciaCliente.buscarPorId(cliente.getCodigoCliente()) != null) {
            throw new IllegalStateException("El código de cliente ya se encuentra registrado.");
        }

        persistenciaCliente.guardar(cliente);
    }

    public void actualizarCliente(Cliente clienteActualizado) {
        validarDatosObligatorio(clienteActualizado);

        Cliente existente = persistenciaCliente.buscarPorId(clienteActualizado.getCodigoCliente());
        if (existente == null) {
            throw new IllegalStateException("Cliente no encontrado.");
        }

        if (!existente.getIdentificacion().equals(clienteActualizado.getIdentificacion())
                && persistenciaCliente.existeIdentificacion(clienteActualizado.getIdentificacion())) {
            throw new IllegalStateException("El número de identificación ya pertenece a otro cliente.");
        }

        clienteActualizado.setActivo(existente.isActivo());
        persistenciaCliente.actualizar(clienteActualizado);
    }

    public void inactivarCliente(String identificacion) {
        if (ValidadorEntradas.esNuloOVacio(identificacion)) {
            throw new IllegalArgumentException("Debe indicar la identificación del cliente.");
        }

        Cliente cliente = persistenciaCliente.buscarPorIdentificacion(identificacion.trim());
        if (cliente == null) {
            throw new IllegalStateException("Cliente no encontrado.");
        }

        if (!cliente.isActivo()) {
            throw new IllegalStateException("El cliente ya se encuentra inactivo.");
        }

        cliente.setActivo(false);
        persistenciaCliente.actualizar(cliente);
    }

    public void activarCliente(String identificacion) {
        if (ValidadorEntradas.esNuloOVacio(identificacion)) {
            throw new IllegalArgumentException("Debe indicar la identificación del cliente.");
        }

        Cliente cliente = persistenciaCliente.buscarPorIdentificacion(identificacion.trim());
        if (cliente == null) {
            throw new IllegalStateException("Cliente no encontrado.");
        }
        if (cliente.isActivo()) {
            throw new IllegalStateException("El cliente ya se encuentra activo.");
        }

        cliente.setActivo(true);
        persistenciaCliente.actualizar(cliente);
    }

    public Cliente buscarPorIdentificacion(String identificacion) {
        if (ValidadorEntradas.esNuloOVacio(identificacion)) {
            return null;
        }
        return persistenciaCliente.buscarPorIdentificacion(identificacion.trim());
    }

    /** Compatibilidad con módulos que consultan cliente por identificación. */
    public Cliente buscarCliente(String identificacion) {
        return buscarPorIdentificacion(identificacion);
    }

    public Cliente buscarPorCodigo(String codigoCliente) {
        if (ValidadorEntradas.esNuloOVacio(codigoCliente)) {
            return null;
        }
        return persistenciaCliente.buscarPorId(codigoCliente.trim());
    }

    public List<ClienteResumenDTO> listarResumen() {
        return persistenciaCliente.listarResumen();
    }

    private void validarDatosObligatorio(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (ValidadorEntradas.esNuloOVacio(cliente.getCodigoCliente())) {
            throw new IllegalArgumentException("El código de cliente es obligatorio.");
        }
        if (cliente.getTipoIdentificacion() == null) {
            throw new IllegalArgumentException("El tipo de identificación es obligatorio.");
        }
        if (ValidadorEntradas.esNuloOVacio(cliente.getIdentificacion())) {
            throw new IllegalArgumentException("El número de identificación es obligatorio.");
        }
        if (ValidadorEntradas.esNuloOVacio(cliente.getNombre())) {
            throw new IllegalArgumentException("Los nombres son obligatorios.");
        }
        if (ValidadorEntradas.esNuloOVacio(cliente.getApellido())) {
            throw new IllegalArgumentException("Los apellidos son obligatorios.");
        }
        if (cliente.getTipoCliente() == null) {
            throw new IllegalArgumentException("El tipo de cliente es obligatorio.");
        }
    }
}
