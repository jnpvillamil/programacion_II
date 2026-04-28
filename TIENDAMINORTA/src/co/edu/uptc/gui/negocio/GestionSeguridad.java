package co.edu.uptc.gui.negocio;

import co.edu.uptc.dto.CredencialDto;
import co.edu.uptc.gui.modelo.Usuario;
import co.edu.uptc.persistencia.LocalUsuario;

public class GestionSeguridad {

    private LocalUsuario localUsuario;

    public GestionSeguridad() {
        localUsuario = new LocalUsuario();
    }

    public Usuario validarIngreso(CredencialDto credencialDto) {
        Usuario usuario = localUsuario.buscarUsuario(credencialDto.getUsuario());

        if (usuario != null && usuario.iniciarSesion(
                credencialDto.getUsuario(),
                credencialDto.getContrasena())) {
            return usuario;
        }
        return null;
    }
}