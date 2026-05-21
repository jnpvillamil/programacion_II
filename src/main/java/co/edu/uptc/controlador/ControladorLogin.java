package co.edu.uptc.controlador;

import co.edu.uptc.dto.LoginDTO;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.negocio.ServicioAutenticacion;

public class ControladorLogin {
    private ServicioAutenticacion servicioAutenticacion;

    public ControladorLogin(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    public UsuarioDTO validarIngreso(LoginDTO loginDTO) {
        boolean esValido = this.servicioAutenticacion.iniciarSesion(loginDTO.getUsuario(), loginDTO.getClave());
        
        if (esValido) {
            Usuario usuarioSesion = this.servicioAutenticacion.getUsuarioSesion();
            return new UsuarioDTO(
                usuarioSesion.getNombre(), 
                usuarioSesion.getUsuario(), 
                usuarioSesion.obtenerRol()
            );
        }
        return null;
    }

    public void cerrarSesion() {
        this.servicioAutenticacion.cerrarSesion();
    }
}