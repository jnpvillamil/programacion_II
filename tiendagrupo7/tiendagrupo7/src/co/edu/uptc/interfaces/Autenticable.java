package co.edu.uptc.interfaces;

public interface Autenticable {
    boolean iniciarSesion(String usuario, String clave);
    void cerrarSesion();
}