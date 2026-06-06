package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.enums.RolUsuarioEnum;
import co.edu.uptc.gui.modelo.Usuario;

public class LocalUsuario {

    private List<Usuario> usuarios;

    public LocalUsuario() {
        usuarios = new ArrayList<>();
        cargarUsuariosBase();
    }

    private void cargarUsuariosBase() {
        usuarios.add(new Usuario(1, "admin", "1234", RolUsuarioEnum.ADMINISTRADOR));
        usuarios.add(new Usuario(2, "vendedor", "1234", RolUsuarioEnum.VENDEDOR));
    }

    public Usuario buscarUsuario(String nombreUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return usuario;
            } 
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

	public boolean validarUsuario(String usuario, String contrasena) {
		// TODO Auto-generated method stub
		return false;
	}
}