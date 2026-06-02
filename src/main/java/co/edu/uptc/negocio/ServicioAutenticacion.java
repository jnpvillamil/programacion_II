package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.ServicioSistema;
import co.edu.uptc.modelo.Usuario;

public class ServicioAutenticacion implements ServicioSistema {

    private final GestionUsuario gestionUsuario;
    private Usuario usuarioSesion;

    public ServicioAutenticacion(GestionUsuario gestionUsuario) {
        this.gestionUsuario = gestionUsuario;
    }

    @Override
    public void iniciarSesion(String usuario, String clave) {
        String loginNormalizado = usuario != null ? usuario.trim() : "";
        String claveNormalizada = clave != null ? clave.trim() : "";

        if (loginNormalizado.isBlank() || claveNormalizada.isBlank()) {
            throw new ExcepcionAutenticacion("Debe ingresar usuario y contraseña.");
        }

        Usuario usuarioEncontrado = gestionUsuario.buscarUsuario(loginNormalizado);
        if (usuarioEncontrado == null) {
            throw new ExcepcionAutenticacion("Usuario o contraseña incorrectos.");
        }

        String claveAlmacenada = usuarioEncontrado.getClave() != null
                ? usuarioEncontrado.getClave().trim()
                : "";
        if (!claveAlmacenada.equals(claveNormalizada)) {
            throw new ExcepcionAutenticacion("Usuario o contraseña incorrectos.");
        }
        if (!usuarioEncontrado.isActivo()) {
            throw new ExcepcionAutenticacion("El usuario se encuentra inactivo.");
        }
        this.usuarioSesion = usuarioEncontrado;
    }

    @Override
    public void cerrarSesion() {
        this.usuarioSesion = null;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public boolean haySesionActiva() {
        return usuarioSesion != null;
    }
}
