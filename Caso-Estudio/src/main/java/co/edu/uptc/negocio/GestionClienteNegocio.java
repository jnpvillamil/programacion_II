package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.GestionCliente;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.enums.TipoDocumentoEnum;
import java.util.List;

public class GestionClienteNegocio {

    private final GestionCliente persistencia;

    public GestionClienteNegocio(GestionCliente persistencia) {
        this.persistencia = persistencia;
    }

    // MÉTODOS PÚBLICOS 

    public void agregarCliente(Cliente cliente) {
        validarClienteNulo(cliente);
        validarCodigoUnico(cliente.getCodigo());
        validarCamposObligatorios(cliente);
        validarFormatoTelefono(cliente.getTelefono());
        validarTipoCliente(cliente.getTipoCliente());
        // para mayoristas
        if ("Mayorista".equals(cliente.getTipoCliente())) {
            validarNumeroIdentificacionMayorista(cliente.getNumeroIdentificacion());
        }
        

        persistencia.crear(cliente);
    }

    public void actualizarCliente(Cliente cliente) {
        validarClienteNulo(cliente);
        // Verificar que el cliente exista (por código)
        Cliente existente = persistencia.buscar(cliente.getCodigo());
        if (existente == null) {
            throw new IllegalArgumentException("No existe un cliente con código: " + cliente.getCodigo());
        }
        // No permitir cambiar el código (usamos el código original existente)
        if (!existente.getCodigo().equals(cliente.getCodigo())) {
            throw new IllegalArgumentException("No se puede modificar el código del cliente.");
        }

        // Validar campos (excepto código, que ya está fijo)
        validarCamposObligatorios(cliente);
        validarFormatoTelefono(cliente.getTelefono());
        validarTipoCliente(cliente.getTipoCliente());
        if ("Mayorista".equals(cliente.getTipoCliente())) {
            validarNumeroIdentificacionMayorista(cliente.getNumeroIdentificacion());
        }
        persistencia.actualizar(cliente);
    }

    public void inactivarCliente(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío.");
        }
        Cliente cliente = persistencia.buscar(codigo);
        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con código: " + codigo);
        }
        if (!cliente.isActivo()) {
            throw new IllegalStateException("El cliente ya está inactivo.");
        }
        persistencia.eliminar(codigo); // Método eliminar de LocalCliente hace setActivo(false)
    }

    public Cliente buscarCliente(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío.");
        }
        return persistencia.buscar(codigo);
    }

    public List<Cliente> listarClientesActivos() {
        return persistencia.listarActivos();
    }

    public List<Cliente> listarTodosClientes() {
        return persistencia.listar();
    }

    //MÉTODOS PRIVADOS DE VALIDACIÓN 

    private void validarClienteNulo(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
    }

    private void validarCodigoUnico(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del cliente es obligatorio.");
        }
        if (persistencia.existe(codigo)) {
            throw new IllegalStateException("Ya existe un cliente con el código: " + codigo);
        }
    }

    private void validarCamposObligatorios(Cliente cliente) {
        // Nombre
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo o razón social es obligatorio.");
        }
        if (cliente.getNombre().trim().length() < 3) {
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres.");
        }

        // Tipo identificación
        if (cliente.getTipoIdentificacion() == null) {
            throw new IllegalArgumentException("El tipo de identificación es obligatorio.");
        }
        // Verificar que sea un valor permitido (ya lo garantiza el enum, pero por si acaso)
        try {
            TipoDocumentoEnum.valueOf(cliente.getTipoIdentificacion().name());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de identificación no válido. Debe ser CC, NIT, CE o PA.");
        }

        // Número identificación
        if (cliente.getNumeroIdentificacion() == null || cliente.getNumeroIdentificacion().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de identificación es obligatorio.");
        }

        // Dirección
        if (cliente.getDireccion() == null || cliente.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }
        if (cliente.getDireccion().trim().length() < 5) {
            throw new IllegalArgumentException("La dirección debe tener al menos 5 caracteres.");
        }

        // Teléfono (formato se valida aparte)
        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
    }

    private void validarFormatoTelefono(String telefono) {
        // Eliminar espacios y guiones si el usuario los ingresal
        String telefonoLimpio = telefono.replaceAll("[\\s\\-]", "");
        if (!telefonoLimpio.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono debe contener solo dígitos.");
        }
        if (telefonoLimpio.length() < 7 || telefonoLimpio.length() > 15) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 15 dígitos.");
        }
    }

    private void validarTipoCliente(String tipoCliente) {
        if (tipoCliente == null || tipoCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cliente es obligatorio (Minorista/Mayorista).");
        }
        if (!"Minorista".equals(tipoCliente) && !"Mayorista".equals(tipoCliente)) {
            throw new IllegalArgumentException("El tipo de cliente debe ser 'Minorista' o 'Mayorista'.");
        }
    }

    private void validarNumeroIdentificacionMayorista(String numeroIdentificacion) {
        if (numeroIdentificacion == null || numeroIdentificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de identificación es obligatorio para mayoristas.");
        }
        // Eliminar posibles espacios o guiones
        String numLimpio = numeroIdentificacion.replaceAll("[\\s\\-]", "");
        if (numLimpio.length() < 10) {
            throw new IllegalArgumentException("Para clientes mayoristas, el número de identificación debe tener al menos 10 dígitos.");
        }
    }

    // TODO implementar función opcional
    private void validarIdentificacionUnica(TipoDocumentoEnum tipo, String numero) {
        
    }
}