package co.edu.uptc.negocio;

import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.interfaces.Autorizable;

public class ServicioAutorizacion implements Autorizable {

    @Override
    public boolean verificarPermiso(RolUsuario rol, String modulo) {
        if (rol == RolUsuario.ADMINISTRADOR) {
            return true;
        }
        return "VENTAS".equals(modulo)
                || "CLIENTES".equals(modulo)
                || "CERRAR_SESION".equals(modulo);
    }
}
