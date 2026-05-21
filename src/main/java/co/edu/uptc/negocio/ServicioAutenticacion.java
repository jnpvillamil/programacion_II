package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Autenticable;
import co.edu.uptc.modelo.Usuario;

public class ServicioAutenticacion implements Autenticable {
    private GestionUsuario gestionUsuario;
    private Usuario usuarioSesion;

    public ServicioAutenticacion(GestionUsuario gestionUsuario) {
        this.gestionUsuario = gestionUsuario;
    }

    @Override
    public boolean iniciarSesion(String usuario, String clave) {
        Usuario usuarioEncontrado = this.gestionUsuario.buscarUsuario(usuario);
        if (usuarioEncontrado != null && usuarioEncontrado.getClave().equals(clave)) {
            this.usuarioSesion = usuarioEncontrado;
            return true;
        }
        return false;
    }

    @Override
    public void cerrarSesion() {
        this.usuarioSesion = null;
    }

    public Usuario getUsuarioSesion() {
        return this.usuarioSesion;
    }
}