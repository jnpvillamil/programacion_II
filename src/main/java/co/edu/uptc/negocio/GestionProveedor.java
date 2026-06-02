package co.edu.uptc.negocio;

import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.interfaces.RepositorioAdministracion;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.util.List;

public class GestionProveedor {

    private final RepositorioAdministracion persistenciaAdministracion;

    public GestionProveedor(RepositorioAdministracion persistenciaAdministracion) {
        this.persistenciaAdministracion = persistenciaAdministracion;
    }

    public void registrarProveedor(Proveedor proveedor) {
        validarDatosObligatorio(proveedor);
        validarFormatoCorreo(proveedor.getCorreoElectronico());

        if (persistenciaAdministracion.existeNitProveedor(proveedor.getNit())) {
            throw new IllegalStateException("El NIT ya se encuentra registrado en el sistema.");
        }
        if (persistenciaAdministracion.buscarProveedorPorCodigo(proveedor.getCodigoProveedor()) != null) {
            throw new IllegalStateException("El código de proveedor ya se encuentra registrado.");
        }

        persistenciaAdministracion.guardarProveedor(proveedor);
    }

    public void actualizarProveedor(Proveedor proveedorActualizado) {
        validarDatosObligatorio(proveedorActualizado);
        validarFormatoCorreo(proveedorActualizado.getCorreoElectronico());

        Proveedor existente = persistenciaAdministracion.buscarProveedorPorCodigo(proveedorActualizado.getCodigoProveedor());
        if (existente == null) {
            throw new IllegalStateException("Proveedor no encontrado.");
        }

        if (!existente.getNit().equals(proveedorActualizado.getNit())
                && persistenciaAdministracion.existeNitProveedor(proveedorActualizado.getNit())) {
            throw new IllegalStateException("El NIT ya pertenece a otro proveedor.");
        }

        proveedorActualizado.setActivo(existente.isActivo());
        persistenciaAdministracion.actualizarProveedor(proveedorActualizado);
    }

    public void inactivarProveedor(String nit) {
        if (ValidadorEntradas.esNuloOVacio(nit)) {
            throw new IllegalArgumentException("Debe indicar el NIT del proveedor.");
        }

        Proveedor proveedor = persistenciaAdministracion.buscarProveedorPorNit(nit.trim());
        if (proveedor == null) {
            throw new IllegalStateException("Proveedor no encontrado.");
        }
        if (!proveedor.isActivo()) {
            throw new IllegalStateException("El proveedor ya se encuentra inactivo.");
        }

        persistenciaAdministracion.inactivarProveedorPorNit(nit.trim());
    }

    public void activarProveedor(String nit) {
        if (ValidadorEntradas.esNuloOVacio(nit)) {
            throw new IllegalArgumentException("Debe indicar el NIT del proveedor.");
        }

        Proveedor proveedor = persistenciaAdministracion.buscarProveedorPorNit(nit.trim());
        if (proveedor == null) {
            throw new IllegalStateException("Proveedor no encontrado.");
        }
        if (proveedor.isActivo()) {
            throw new IllegalStateException("El proveedor ya se encuentra activo.");
        }

        persistenciaAdministracion.activarProveedorPorNit(nit.trim());
    }

    public Proveedor buscarPorNit(String nit) {
        if (ValidadorEntradas.esNuloOVacio(nit)) {
            return null;
        }
        return persistenciaAdministracion.buscarProveedorPorNit(nit.trim());
    }

    public Proveedor buscarPorCodigo(String codigoProveedor) {
        if (ValidadorEntradas.esNuloOVacio(codigoProveedor)) {
            return null;
        }
        return persistenciaAdministracion.buscarProveedorPorCodigo(codigoProveedor.trim());
    }

    public Proveedor buscarProveedor(String nit) {
        return buscarPorNit(nit);
    }

    public List<ProveedorResumenDTO> listarResumen() {
        return persistenciaAdministracion.listarResumenProveedor();
    }

    private void validarFormatoCorreo(String correoElectronico) {
        if (!ValidadorEntradas.esCorreoValido(correoElectronico)) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido.");
        }
    }

    private void validarDatosObligatorio(Proveedor proveedor) {
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo.");
        }
        if (ValidadorEntradas.esNuloOVacio(proveedor.getCodigoProveedor())) {
            throw new IllegalArgumentException("El código de proveedor es obligatorio.");
        }
        if (ValidadorEntradas.esNuloOVacio(proveedor.getNit())) {
            throw new IllegalArgumentException("El NIT es obligatorio.");
        }
        if (ValidadorEntradas.esNuloOVacio(proveedor.getRazonSocial())) {
            throw new IllegalArgumentException("La razón social es obligatoria.");
        }
        if (ValidadorEntradas.esNuloOVacio(proveedor.getNombre())) {
            throw new IllegalArgumentException("El representante legal es obligatorio.");
        }
        if (ValidadorEntradas.esNuloOVacio(proveedor.getCorreoElectronico())) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }
    }
}
