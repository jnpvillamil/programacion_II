package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.RolUsuarioEnum;

public class Vendedor extends Usuario {

    public Vendedor() {
        setRol(RolUsuarioEnum.VENDEDOR);
    }

    public Vendedor(int idUsuario, String nombreUsuario, String contrasena) {
        super(idUsuario, nombreUsuario, contrasena, RolUsuarioEnum.VENDEDOR);
    }
}