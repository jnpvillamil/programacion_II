package co.edu.uptc.modelo;

import co.edu.uptc.enums.RolUsuario;

public class Bodeguero extends Usuario {

    private String zonaBodega;

    public Bodeguero(String nombre, String apellido, String identificacion,
                     String direccion, String telefono, String usuario, String clave) {
        super(nombre, apellido, identificacion, direccion, telefono, usuario, clave);
    }

    @Override
    public String obtenerRol() {
        return RolUsuario.BODEGUERO.name();
    }

    public String getZonaBodega() {
        return zonaBodega;
    }

    public void setZonaBodega(String zonaBodega) {
        this.zonaBodega = zonaBodega;
    }
}
