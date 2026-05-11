package co.edu.uptc.modelo;

import co.edu.uptc.enums.RolUsuario;

public class Administrador extends Usuario {
    public Administrador(String nombre, String apellido, 
    		String identificacion, String direccion, String telefono, 
    		String usuario, String clave) {
        super(nombre, apellido, identificacion, 
        		direccion, telefono, usuario, clave);
    }

    @Override
    public String obtenerRol() {
        return RolUsuario.ADMINISTRADOR.name();
    }
}