package co.edu.uptc.gui.interfaces;

public interface IAutenticable {
    boolean iniciarSesion(String usuario, String contrasena);
    void cerrarSesion();
}