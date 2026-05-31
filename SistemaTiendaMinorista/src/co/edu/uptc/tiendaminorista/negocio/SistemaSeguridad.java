package co.edu.uptc.tiendaminorista.negocio;

import co.edu.uptc.tiendaminorista.dto.CredencialDto;

public class SistemaSeguridad {

    //Usuario y contraseña de pruebas
    private static final String USUARIO_VALIDO = "admin";
    private static final String CONTRASENA_VALIDA = "1234";
    
    public boolean validarInicio(CredencialDto credencial) {
        //Valida que las credenciales no sean nulas
        if (credencial == null) {
            return false;
        }
        
        String usuario = credencial.getUsuario();
        byte[] passwordBytes = credencial.getPassword();
        
        //Valida que los campos no esten vacios
        if (usuario == null || usuario.isBlank()) {
            return false;
        }
        
        if (passwordBytes == null || passwordBytes.length == 0) {
            return false;
        }
        
        //Convertir byte[] a String para comparar
        String password = new String(passwordBytes);
        
        //Valida usuario y contraseña
        return USUARIO_VALIDO.equals(usuario) && CONTRASENA_VALIDA.equals(password);
    }
}