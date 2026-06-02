package co.edu.uptc.negocio;

import co.edu.uptc.dto.UsuarioResumenDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.interfaces.RepositorioAdministracion;
import co.edu.uptc.modelo.Administrador;
import co.edu.uptc.modelo.Cajero;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.utilidades.ValidadorEntradas;

import java.util.List;

public class GestionUsuario {

    private final RepositorioAdministracion persistenciaAdministracion;

    public GestionUsuario(RepositorioAdministracion persistenciaAdministracion) {
        this.persistenciaAdministracion = persistenciaAdministracion;
    }

    public void registrarUsuario(String login, String clave, RolUsuario rol) {
        validarDatosRegistro(login, clave, rol);

        if (persistenciaAdministracion.existeUsuarioLogin(login.trim())) {
            throw new IllegalStateException("Ya existe un usuario con el mismo login.");
        }

        Usuario usuario = construirUsuario(login.trim(), clave.trim(), rol);
        persistenciaAdministracion.guardarUsuario(usuario);
    }

    public Usuario buscarUsuario(String usuarioLogin) {
        if (ValidadorEntradas.esNuloOVacio(usuarioLogin)) {
            return null;
        }
        return persistenciaAdministracion.buscarUsuarioPorLogin(usuarioLogin.trim());
    }

    public void eliminarUsuario(String usuarioLogin) {
        if (ValidadorEntradas.esNuloOVacio(usuarioLogin)) {
            throw new IllegalArgumentException("Debe indicar el login del usuario.");
        }
        if (!persistenciaAdministracion.existeUsuarioLogin(usuarioLogin.trim())) {
            throw new IllegalStateException("Usuario no encontrado.");
        }
        persistenciaAdministracion.eliminarUsuario(usuarioLogin.trim());
    }

    public void activarUsuario(String usuarioLogin) {
        if (ValidadorEntradas.esNuloOVacio(usuarioLogin)) {
            throw new IllegalArgumentException("Debe indicar el login del usuario.");
        }
        Usuario usuario = persistenciaAdministracion.buscarUsuarioPorLogin(usuarioLogin.trim());
        if (usuario == null) {
            throw new IllegalStateException("Usuario no encontrado.");
        }
        if (usuario.isActivo()) {
            throw new IllegalStateException("El usuario ya se encuentra activo.");
        }
        persistenciaAdministracion.activarUsuarioPorLogin(usuarioLogin.trim());
    }

    public void inactivarUsuario(String usuarioLogin) {
        if (ValidadorEntradas.esNuloOVacio(usuarioLogin)) {
            throw new IllegalArgumentException("Debe indicar el login del usuario.");
        }
        Usuario usuario = persistenciaAdministracion.buscarUsuarioPorLogin(usuarioLogin.trim());
        if (usuario == null) {
            throw new IllegalStateException("Usuario no encontrado.");
        }
        if (!usuario.isActivo()) {
            throw new IllegalStateException("El usuario ya se encuentra inactivo.");
        }
        persistenciaAdministracion.inactivarUsuarioPorLogin(usuarioLogin.trim());
    }

    public List<UsuarioResumenDTO> listarResumenUsuario() {
        return persistenciaAdministracion.listarResumenUsuario();
    }

    public List<Usuario> listarUsuario() {
        return persistenciaAdministracion.listarUsuario();
    }

    private void validarDatosRegistro(String login, String clave, RolUsuario rol) {
        if (ValidadorEntradas.esNuloOVacio(login)) {
            throw new IllegalArgumentException("Debe indicar el login del usuario.");
        }
        if (ValidadorEntradas.esNuloOVacio(clave)) {
            throw new IllegalArgumentException("Debe indicar la contraseña del usuario.");
        }
        if (clave.trim().length() < 3) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
        }
        if (rol == null) {
            throw new IllegalArgumentException("Debe seleccionar un rol válido.");
        }
    }

    private Usuario construirUsuario(String login, String clave, RolUsuario rol) {
        String nombre = capitalizar(login);
        String apellido = rol == RolUsuario.ADMINISTRADOR ? "Administrador" : "Cajero";
        if (rol == RolUsuario.ADMINISTRADOR) {
            return new Administrador(nombre, apellido, "", "", "", login, clave);
        }
        return new Cajero(nombre, apellido, "", "", "", login, clave);
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isBlank()) {
            return "";
        }
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
}
