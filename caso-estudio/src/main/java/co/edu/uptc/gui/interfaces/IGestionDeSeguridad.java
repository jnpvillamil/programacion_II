package co.edu.uptc.gui.interfaces;

import co.edu.uptc.dto.CredencialDto;

public interface IGestionDeSeguridad {
    
    public boolean validarAcceso(CredencialDto login);

    public String obtenerPermisosRol(String idUsuario);

    public void destruirTokenSesion();
}