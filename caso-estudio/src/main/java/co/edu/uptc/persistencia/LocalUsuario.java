package co.edu.uptc.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.gui.modelo.Administrador;
import co.edu.uptc.gui.modelo.Usuario;
import co.edu.uptc.gui.modelo.Vendedor;

public class LocalUsuario {

    private List<Usuario> usuarios;

    public LocalUsuario() {
        usuarios = new ArrayList<>();
        cargarUsuariosBase();
    }

    private void cargarUsuariosBase() {
        usuarios.add(new Administrador(1, "admin", "1234"));
        usuarios.add(new Vendedor(2, "vendedor", "1234"));
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
}