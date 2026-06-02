package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Usuario;

public interface ServicioSistema {

    void iniciarSesion(String usuario, String clave);

    void cerrarSesion();

    Usuario getUsuarioSesion();

    boolean haySesionActiva();
}
