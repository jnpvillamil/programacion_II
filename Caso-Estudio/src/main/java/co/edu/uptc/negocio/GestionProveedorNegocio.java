package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.IGestionProveedor;
import co.edu.uptc.modelo.Proveedor;
import java.util.List;

public class GestionProveedorNegocio {

    private final IGestionProveedor persistencia;

    public GestionProveedorNegocio(IGestionProveedor persistencia) {
        this.persistencia = persistencia;
    }

    // ===== MÉTODOS PÚBLICOS =====
    public void crear(Proveedor proveedor) {
        validarProveedorNulo(proveedor);
        validarCodigoUnico(proveedor.getCodigo());
        validarCamposObligatorios(proveedor);
        validarFormatoTelefono(proveedor.getTelefono());
        validarCorreo(proveedor.getCorreo());
        persistencia.crear(proveedor);
    }

    public void actualizar(Proveedor proveedor) {
        validarProveedorNulo(proveedor);
        if (!persistencia.existe(proveedor.getCodigo())) {
            throw new IllegalArgumentException("No existe proveedor con código: " + proveedor.getCodigo());
        }
        validarCamposObligatorios(proveedor);
        validarFormatoTelefono(proveedor.getTelefono());
        validarCorreo(proveedor.getCorreo());
        persistencia.actualizar(proveedor);
    }

    public void inactivar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser vacío.");
        }
        Proveedor p = persistencia.buscar(codigo);
        if (p == null) {
            throw new IllegalArgumentException("No existe proveedor con código: " + codigo);
        }
        if (!p.isActivo()) {
            throw new IllegalStateException("El proveedor ya está inactivo.");
        }
        persistencia.eliminar(codigo);
    }

    public Proveedor buscar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }
        return persistencia.buscar(codigo);
    }

    public List<Proveedor> listarActivos() {
        return persistencia.listarActivos();
    }

    // ===== VALIDACIONES PRIVADAS =====
    private void validarProveedorNulo(Proveedor p) {
        if (p == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo.");
        }
    }

    private void validarCodigoUnico(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }
        if (persistencia.existe(codigo)) {
            throw new IllegalStateException("Ya existe un proveedor con el código: " + codigo);
        }
    }

    private void validarCamposObligatorios(Proveedor p) {
        if (p.getRazonSocial() == null || p.getRazonSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("La razón social es obligatoria.");
        }
        if (p.getNit() == null || p.getNit().trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT es obligatorio.");
        }
        if (p.getDireccion() == null || p.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }
        if (p.getTelefono() == null || p.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
        if (p.getCorreo() == null || p.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }
    }

    private void validarFormatoTelefono(String telefono) {
        String limpio = telefono.replaceAll("[\\s\\-]", "");
        if (!limpio.matches("\\d{7,15}")) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 15 dígitos.");
        }
    }

    private void validarCorreo(String correo) {
        if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido.");
        }
    }
}
