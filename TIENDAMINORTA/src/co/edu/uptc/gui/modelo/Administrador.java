package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.RolUsuarioEnum;

public class Administrador extends Usuario {

    public Administrador() {
        setRol(RolUsuarioEnum.ADMINISTRADOR);
    }

    public Administrador(int idUsuario, String nombreUsuario, String contrasena) {
        super(idUsuario, nombreUsuario, contrasena, RolUsuarioEnum.ADMINISTRADOR);
    }
}