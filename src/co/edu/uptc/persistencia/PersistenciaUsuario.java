package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario implements Repositorio<Usuario> {
    private List<Usuario> usuarios;

    public PersistenciaUsuario() {
        this.usuarios = new ArrayList<>();
    }

    @Override
    public void guardar(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    @Override
    public void eliminar(String id) {
        this.usuarios.removeIf(u -> u.getUsuario().equals(id));
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(this.usuarios);
    }

    @Override
    public Usuario buscarPorId(String id) {
        for (Usuario u : this.usuarios) {
            if (u.getUsuario().equals(id)) {
                return u;
            }
        }
        return null;
    }
}