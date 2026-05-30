package co.edu.uptc.gui.interfaces;
import co.edu.uptc.dto.CredencialDto;
public interface RF31_AutenticarCredenciales {
    public boolean validarAcceso(CredencialDto login);
}