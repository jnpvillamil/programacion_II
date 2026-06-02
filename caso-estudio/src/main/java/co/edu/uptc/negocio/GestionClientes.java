package co.edu.uptc.negocio;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.persistencia.PersistenciaCliente;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.util.List;

public class GestionClientes {

    private Repositorio<Cliente> repositorioCliente;

    public GestionClientes(Repositorio<Cliente> repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    public GestionClientes() {
        this(new PersistenciaCliente());
    }

    public ResultadoOperacion registrarCliente(Cliente cliente) {
        ResultadoOperacion validacion = validarCliente(cliente);
        if (!validacion.isExito()) {
            return validacion;
        }
        if (repositorioCliente.buscarPorId(cliente.getIdentificacion()) != null) {
            return ResultadoOperacion.error("Error: La identificación ya existe.");
        }
        repositorioCliente.guardar(cliente);
        return ResultadoOperacion.exito("Cliente registrado exitosamente.");
    }

    public ResultadoOperacion actualizarCliente(Cliente cliente) {
        ResultadoOperacion validacion = validarCliente(cliente);
        if (!validacion.isExito()) {
            return validacion;
        }
        if (repositorioCliente.buscarPorId(cliente.getCodigoCliente()) == null) {
            return ResultadoOperacion.error("No se encontró el cliente a actualizar.");
        }
        repositorioCliente.actualizar(cliente);
        return ResultadoOperacion.exito("Datos del cliente actualizados.");
    }

    public ResultadoOperacion inactivarCliente(String codigoCliente) {
        if (ValidadorEntradas.esVacio(codigoCliente)) {
            return ResultadoOperacion.error("Ingrese el código del cliente.");
        }
        Cliente cliente = repositorioCliente.buscarPorId(codigoCliente.trim());
        if (cliente == null) {
            return ResultadoOperacion.error("Cliente no encontrado.");
        }
        cliente.setActivo(false);
        repositorioCliente.actualizar(cliente);
        return ResultadoOperacion.exito("Cliente inactivado correctamente.");
    }

    public Cliente buscarCliente(String criterioBusqueda) {
        if (ValidadorEntradas.esVacio(criterioBusqueda)) {
            return null;
        }
        return repositorioCliente.buscarPorId(criterioBusqueda.trim());
    }

    public ResultadoOperacion buscarClienteParaVenta(String identificacion) {
        if (ValidadorEntradas.esVacio(identificacion)) {
            return ResultadoOperacion.error("Ingrese la identificación del cliente.");
        }
        Cliente cliente = buscarCliente(identificacion);
        if (cliente == null || !cliente.isActivo()) {
            return ResultadoOperacion.error("Cliente no encontrado o inactivo.");
        }
        return ResultadoOperacion.exito("Cliente encontrado.", cliente);
    }

    public ResultadoOperacion buscarClienteValidado(String criterio) {
        if (ValidadorEntradas.esVacio(criterio)) {
            return ResultadoOperacion.error("Ingrese un criterio de búsqueda.");
        }
        Cliente cliente = buscarCliente(criterio);
        if (cliente == null) {
            return ResultadoOperacion.error("Cliente no encontrado.");
        }
        return ResultadoOperacion.exito("Cliente encontrado.", cliente);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return repositorioCliente.listar();
    }

    public List<ClienteResumenDTO> listarResumen() {
        return repositorioCliente.listar().stream()
                .map(c -> new ClienteResumenDTO(
                        c.getCodigoCliente(),
                        c.getNombre(),
                        c.getTelefono()))
                .toList();
    }

    private ResultadoOperacion validarCliente(Cliente cliente) {
        if (cliente == null) {
            return ResultadoOperacion.error("Cliente inválido.");
        }
        if (ValidadorEntradas.esVacio(cliente.getNombre())) {
            return ResultadoOperacion.error("El nombre es obligatorio.");
        }
        if (ValidadorEntradas.esVacio(cliente.getIdentificacion())) {
            return ResultadoOperacion.error("La identificación es obligatoria.");
        }
        return ResultadoOperacion.exito("Validación correcta.");
    }
}
