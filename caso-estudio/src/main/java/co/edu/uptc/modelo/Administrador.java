package co.edu.uptc.modelo;

public class Administrador extends Usuario {

    public Administrador(String nombre, String identificacion, String direccion, String telefono, String usuario, String clave) {
        super(nombre, identificacion, direccion, telefono, usuario, clave);
    }

    public Administrador() {
        super();
    }

    @Override
    public String obtenerRol() {
        return "ADMINISTRADOR";
    }
}