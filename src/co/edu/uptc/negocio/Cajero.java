package co.edu.uptc.negocio;

public class Cajero extends Persona {
    
	private String usuario; private String contrasena; private int cajaAsignada;
    
	public Cajero(String c, String n, String u, String p, int caja) { super(c,n); this.usuario=u; this.contrasena=p; this.cajaAsignada=caja; }
    
	public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
}