package co.edu.uptc.gui.negocio;

import co.edu.uptc.dto.CredencialDto;
import co.edu.uptc.gui.interfaces.Autenticable;
import co.edu.uptc.gui.modelo.Usuario;
import co.edu.uptc.persistencia.LocalUsuario;

public class GestionSeguridad implements Autenticable {

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

	@Override
	public boolean iniciarSesion(CredencialDto credenciales) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarSesion(String usuario, String contrasena) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cerrarSesion() {
		// TODO Auto-generated method stub
		
	}
}