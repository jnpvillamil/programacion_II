package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.ProveedorUsuarioSesion;
import co.edu.uptc.interfaces.ServicioSistema;
import co.edu.uptc.modelo.Usuario;

public class ServicioAutenticacion implements ServicioSistema, ProveedorUsuarioSesion {

    private final GestionUsuario gestionUsuario;
    private final ServicioAuditoria servicioAuditoria;
    private Usuario usuarioSesion;

    public ServicioAutenticacion(GestionUsuario gestionUsuario, ServicioAuditoria servicioAuditoria) {
        this.gestionUsuario = gestionUsuario;
        this.servicioAuditoria = servicioAuditoria;
    }

    @Override
    public void iniciarSesion(String usuario, String clave) {
        String loginNormalizado = usuario != null ? usuario.trim() : "";
        String claveNormalizada = clave != null ? clave.trim() : "";

        if (loginNormalizado.isBlank() || claveNormalizada.isBlank()) {
            servicioAuditoria.registrarIngresoFallido(loginNormalizado);
            throw new ExcepcionAutenticacion("Debe ingresar usuario y contraseña.");
        }

        Usuario usuarioEncontrado = gestionUsuario.buscarUsuario(loginNormalizado);
        if (usuarioEncontrado == null) {
            servicioAuditoria.registrarIngresoFallido(loginNormalizado);
            throw new ExcepcionAutenticacion("Usuario o contraseña incorrectos.");
        }

        String claveAlmacenada = usuarioEncontrado.getClave() != null
                ? usuarioEncontrado.getClave().trim()
                : "";
        if (!claveAlmacenada.equals(claveNormalizada)) {
            servicioAuditoria.registrarIngresoFallido(loginNormalizado);
            throw new ExcepcionAutenticacion("Usuario o contraseña incorrectos.");
        }
        if (!usuarioEncontrado.isActivo()) {
            servicioAuditoria.registrarIngresoFallido(loginNormalizado);
            throw new ExcepcionAutenticacion("El usuario se encuentra inactivo.");
        }
        servicioAuditoria.registrarIngresoExitoso(usuarioEncontrado);
        this.usuarioSesion = usuarioEncontrado;
    }

    @Override
    public void cerrarSesion() {
        this.usuarioSesion = null;
    }

    @Override
    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    @Override
    public boolean haySesionActiva() {
        return usuarioSesion != null;
    }

    @Override
    public String obtenerLoginOperador() {
        return usuarioSesion != null ? usuarioSesion.getUsuario() : "SIN_SESION";
    }
}
