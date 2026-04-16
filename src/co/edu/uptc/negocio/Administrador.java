package co.edu.uptc.negocio;

public class Administrador extends Persona {
	
    private String usuario; private String contrasena;
    
    public Administrador(String c, String n, String u, String p) { super(c,n); this.usuario=u; this.contrasena=p; }
    
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
}