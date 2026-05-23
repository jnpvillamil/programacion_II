package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Usuario;
import java.util.List;

public interface IPersistenciaUsuario {
    Usuario validarUsuario(String nombreUsuario, String claveIngresada);
    List<Usuario> listarUsuarios();
}
