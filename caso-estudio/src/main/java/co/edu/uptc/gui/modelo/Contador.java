package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.RolUsuarioEnum;

public class Contador extends Usuario {

    public Contador() {
        setRol(RolUsuarioEnum.CONTADOR);
    }

    public Contador(int idUsuario, String nombreUsuario, String contrasena) {
        super(idUsuario, nombreUsuario, contrasena, RolUsuarioEnum.CONTADOR);
    }
}
