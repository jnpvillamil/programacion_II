package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.IPersistenciaUsuario;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.persistencia.PersistenciaUsuario;


public class GestionUsuarios {

    private IPersistenciaUsuario persistenciaUsuario;
    private Usuario usuarioAutenticado;

  
    public GestionUsuarios(IPersistenciaUsuario persistenciaUsuario) {
        this.persistenciaUsuario = persistenciaUsuario;
    }

 
    public GestionUsuarios() {
        this(new PersistenciaUsuario());
    }

    public boolean autenticar(String username, String password) {
        Usuario usuario = persistenciaUsuario.validarUsuario(username, password);
        if (usuario != null) {
            this.usuarioAutenticado = usuario;
            return true;
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