package co.edu.uptc.negocio;

import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.persistencia.PersistenciaSupervisor;
import co.edu.uptc.utilidades.ValidadorEntradas;
import java.util.List;

public class GestionSupervisor {

    private final PersistenciaSupervisor persistenciaSupervisor;
    public GestionSupervisor(PersistenciaSupervisor persistenciaSupervisor) {
        this.persistenciaSupervisor = persistenciaSupervisor;
    }
    public ResultadoOperacion registrarUsuarioConRol(Usuario usuario, RolUsuario rol) {
        if (usuario == null) {
            return ResultadoOperacion.error("Datos del usuario incompletos.");
        }
        if (ValidadorEntradas.esVacio(usuario.getNombre())) {
            return ResultadoOperacion.error("Ingrese el nombre.");
        }
        if (ValidadorEntradas.esVacio(usuario.getUsuario())) {
            return ResultadoOperacion.error("Ingrese el nombre de usuario.");
        }
        if (ValidadorEntradas.esVacio(usuario.getClave())) {
            return ResultadoOperacion.error("Ingrese la contraseña.");
        }
        if (rol == null || rol == RolUsuario.SUPERVISOR) {
            return ResultadoOperacion.error("No puede asignar el rol de Supervisor.");
        }
        if (persistenciaSupervisor.existeUsuario(usuario.getUsuario().trim())) {
            return ResultadoOperacion.error("El usuario ya existe.");
        }
        boolean guardado = persistenciaSupervisor.registrarUsuario(usuario, rol.name());
        if (!guardado) {
            return ResultadoOperacion.error("No se pudo registrar el usuario.");
        }
        return ResultadoOperacion.exito("Usuario registrado correctamente.");
    }
    public List<UsuarioDTO> listarUsuarios() {
        return persistenciaSupervisor.listarUsuariosConRol();
    }
}
	
