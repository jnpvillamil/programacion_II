package co.edu.uptc.tiendaminorista.negocio;

import co.edu.uptc.tiendaminorista.dto.CredencialDto;

public class SistemaSeguridad {

    public boolean validarInicio(CredencialDto credencial) throws Exception {
        if (credencial != null && credencial.getUsuario() != null
                && !credencial.getUsuario().isBlank()) {
            return true;
        }
        throw new Exception("Las credenciales no son válidas");
    }
}
