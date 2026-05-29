package co.edu.uptc.negocio;

import co.edu.uptc.dto.LoginDTO;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.interfaces.Autenticable;
import co.edu.uptc.interfaces.IPersistenciaUsuario;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.persistencia.PersistenciaUsuario;
import co.edu.uptc.utilidades.LogSistema;
import co.edu.uptc.utilidades.MapeadorDTO;

public class GestionUsuarios implements Autenticable {

    private final IPersistenciaUsuario persistenciaUsuario;
    private Usuario usuarioAutenticado;

    public GestionUsuarios(IPersistenciaUsuario persistenciaUsuario) {
        this.persistenciaUsuario = persistenciaUsuario;
    }

    public GestionUsuarios() {
        this(new PersistenciaUsuario());
    }

    @Override
    public boolean iniciarSesion(String usuario, String clave) {
        return autenticar(usuario, clave);
    }

    public boolean autenticar(LoginDTO credenciales) {
        return autenticar(credenciales.getUsuario(), credenciales.getClave());
    }

    public boolean autenticar(String username, String password) {
        Usuario usuario = persistenciaUsuario.validarUsuario(username, password);
        if (usuario != null) {
            this.usuarioAutenticado = usuario;
            LogSistema.loginExitoso(username);
            return true;
        }
        LogSistema.loginFallido(username);
        return false;
    }

    public UsuarioDTO obtenerSesionActiva() {
        if (usuarioAutenticado == null) {
            return null;
        }
        return MapeadorDTO.desdeUsuario(usuarioAutenticado);
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    @Override
    public void cerrarSesion() {
        this.usuarioAutenticado = null;
    }
}
