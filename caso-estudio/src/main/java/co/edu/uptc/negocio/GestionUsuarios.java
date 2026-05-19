package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.persistencia.PersistenciaUsuario;

import java.util.List;

public class GestionUsuarios {

    private PersistenciaUsuario persistenciaUsuario;
    private Usuario usuarioAutenticado;

    public GestionUsuarios() {
        this.persistenciaUsuario = new PersistenciaUsuario();
    }

    public boolean autenticar(String username, String password) {
        List<Usuario> usuarios = persistenciaUsuario.listarUsuarios();
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(username) && u.getClave().equals(password)) {
                this.usuarioAutenticado = u;
                return true;
            }
        }
        return false;
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void cerrarSesion() {
        this.usuarioAutenticado = null;
    }
}