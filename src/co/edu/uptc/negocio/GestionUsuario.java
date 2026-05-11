package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Usuario;
import java.util.List;

public class GestionUsuario {
    private Repositorio<Usuario> persistenciaUsuario;

    public GestionUsuario(Repositorio<Usuario> persistenciaUsuario) {
        this.persistenciaUsuario = persistenciaUsuario;
    }

    public void registrarUsuario(Usuario usuario) {
        this.persistenciaUsuario.guardar(usuario);
    }

    public Usuario buscarUsuario(String usuarioLogin) {
        return this.persistenciaUsuario.buscarPorId(usuarioLogin);
    }

    public List<Usuario> listarUsuarios() {
        return this.persistenciaUsuario.listar();
    }
}