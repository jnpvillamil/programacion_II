package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.RolUsuarioEnum;

public class Contador extends Usuario {

    public Contador(long l, String string, String string2, String string3) {
        setRol(RolUsuarioEnum.CONTADOR);
    }

    public Contador(int idUsuario, String nombreUsuario, String contrasena) {
        super(idUsuario, nombreUsuario, contrasena, RolUsuarioEnum.CONTADOR);
    }

	public String getNombre() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTarjetaProfesional() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTelefono() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
