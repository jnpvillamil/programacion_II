package co.edu.uptc.modelo;

import co.edu.uptc.enums.RolUsuario;

public class Cajero extends Usuario {
    public Cajero(String nombre, String apellido, String identificacion, 
    		String direccion, String telefono, String usuario, String clave) {
        super(nombre, apellido, identificacion, direccion, telefono, usuario, clave);
    }

    @Override
    public String obtenerRol() {
        return RolUsuario.CAJERO.name();
    }
}