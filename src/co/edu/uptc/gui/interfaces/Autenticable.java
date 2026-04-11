package co.edu.uptc.gui.interfaces;

public interface Autenticable {
    boolean iniciarSesion(String usuario, String contrasena);
    void cerrarSesion();
}