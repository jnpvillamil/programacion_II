package co.edu.uptc.negocio;
public class Proveedor extends Persona {
    
	private String nit, correoElectronico;
    
	public Proveedor(String c, String n, String nit, String email) { 
		super(c,n); this.nit=nit; this.setCorreoElectronico(email); }

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Object getCodigo() {
		// TODO Auto-generated method stub
		return null;
	}
}