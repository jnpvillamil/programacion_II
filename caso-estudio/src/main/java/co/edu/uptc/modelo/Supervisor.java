package co.edu.uptc.modelo;

public class Supervisor extends Usuario {

	   public Supervisor(String nombre, String identificacion, String direccion,
               String telefono, String usuario, String clave) {
 super(nombre, identificacion, direccion, telefono, usuario, clave);
}
	public Supervisor() {
		super();
	}
	 public String obtenerRol() {
	        return "SUPERVISOR";
	    }
}
