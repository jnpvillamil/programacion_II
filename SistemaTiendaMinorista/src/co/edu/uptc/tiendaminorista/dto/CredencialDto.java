package co.edu.uptc.tiendaminorista.dto;

import java.util.Arrays;

public class CredencialDto {
    private String usuario;
    private byte[] password;

    public String getUsuario() { 
        return usuario; 
    }
    
    public void setUsuario(String usuario) { 
        this.usuario = usuario; 
    }

    public byte[] getPassword() { 
        return password; 
    }
    
    public void setPassword(byte[] password) { 
        this.password = password; 
    }
    
    // Método para comparar contraseñas 
    public boolean compararPassword(String textoPlano) {
        if (password == null || textoPlano == null) {
            return false;
        }
        String passwordStr = new String(password);
        return passwordStr.equals(textoPlano);
    }

    @Override
    public String toString() {
        return "CredencialDto [usuario=" + usuario + ", password=" + Arrays.toString(password) + "]";
    }
}