package co.edu.uptc.interfaces;

import co.edu.uptc.modelo.Usuario;

/**
 * Contrato unificado de servicios globales de autenticación y sesión.
 */
public interface ServicioSistema {

    void iniciarSesion(String usuario, String clave);

    void cerrarSesion();

    Usuario getUsuarioSesion();

    boolean haySesionActiva();
}
