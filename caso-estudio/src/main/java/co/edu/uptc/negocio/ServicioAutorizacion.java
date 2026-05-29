package co.edu.uptc.negocio;

import co.edu.uptc.enums.ModuloSistema;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.interfaces.Autorizable;

public class ServicioAutorizacion implements Autorizable {

    @Override
    public boolean verificarPermiso(RolUsuario rol, String modulo) {
        if (rol == RolUsuario.ADMINISTRADOR) {
            return true;
        }
        ModuloSistema moduloSistema = ModuloSistema.desdeTexto(modulo);
        if (moduloSistema == null) {
            return false;
        }
        return moduloSistema == ModuloSistema.VENTAS
                || moduloSistema == ModuloSistema.CLIENTES
                || moduloSistema == ModuloSistema.CERRAR_SESION;
    }
}
