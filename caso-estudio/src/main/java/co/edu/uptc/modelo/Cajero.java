package co.edu.uptc.modelo;

public class Cajero extends Usuario {

    public Cajero(String nombre, String identificacion, String direccion, String telefono, String usuario, String clave) {
        super(nombre, identificacion, direccion, telefono, usuario, clave);
    }

    public Cajero() {
        super();
    }

    @Override
    public String obtenerRol() {
        return "CAJERO";
    }
}