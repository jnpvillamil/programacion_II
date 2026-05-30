package co.edu.uptc.gui.negocio;

import co.edu.uptc.dto.CredencialDto;
import co.edu.uptc.gui.interfaces.RF31_AutenticarCredenciales;
import co.edu.uptc.gui.interfaces.RF32_VerificarRolUsuario;
import co.edu.uptc.gui.interfaces.RF33_CerrarSesionSegura;
import co.edu.uptc.gui.modelo.Usuario;
import co.edu.uptc.persistencia.LocalUsuario;

public class GestionSeguridad implements 
    RF31_AutenticarCredenciales, 
    RF32_VerificarRolUsuario, 
    RF33_CerrarSesionSegura {

    private LocalUsuario localUsuario;
    private String usuarioLogueadoRol;

    public GestionSeguridad() {
        this.localUsuario = new LocalUsuario();
        this.usuarioLogueadoRol = null;
    }

    @Override
    public boolean validarAcceso(CredencialDto login) {
        if (login == null || login.getUsuario() == null || login.getContrasena() == null) {
            return false;
        }
        
        boolean esValido = localUsuario.validarUsuario(login.getUsuario(), login.getContrasena());
        
        if (esValido) {
            this.usuarioLogueadoRol = obtenerPermisosRol(login.getUsuario());
        }
        
        return esValido;
    }

    @Override
    public String obtenerPermisosRol(String idUsuario) {
        // Regla de negocio: Determina el rol del usuario para restringir las interfaces
        if (idUsuario.toLowerCase().contains("admin")) {
            return "ADMINISTRADOR";
        }
        return "VENDEDOR";
    }

    @Override
    public void destruirTokenSesion() {
        this.usuarioLogueadoRol = null;
        System.gc(); 
    }

	public Usuario validarIngreso(CredencialDto credencialDto) {
		// TODO Auto-generated method stub
		return null;
	}
}