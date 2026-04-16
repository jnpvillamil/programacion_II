package co.edu.uptc.Datos;

public class Clientedt {

	private String Nombre;
	private String Tipodocli;
	private String Numerodocli;
	private String Direccioncli;
	private String Telefonocli;
	private String Tipocli;
	
	public Clientedt(String Nombre,String Tipodocli,String Numerodocli,String Direccioncli,String Telefonocli,String Tipocli) {
	
		this.Nombre=Nombre;
		this.Tipodocli=Tipodocli;
		this.Numerodocli=Numerodocli;
		this.Direccioncli=Direccioncli;
		this.Telefonocli=Telefonocli;
		this.Tipocli=Tipocli;
		
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getTipodocli() {
		return Tipodocli;
	}

	public void setTipodocli(String tipodocli) {
		Tipodocli = tipodocli;
	}

	public String getNumerodocli() {
		return Numerodocli;
	}

	public void setNumerodocli(String numerodocli) {
		Numerodocli = numerodocli;
	}

	public String getDireccioncli() {
		return Direccioncli;
	}

	public void setDireccioncli(String direccioncli) {
		Direccioncli = direccioncli;
	}

	public String getTelefonocli() {
		return Telefonocli;
	}

	public void setTelefonocli(String telefonocli) {
		Telefonocli = telefonocli;
	}

	public String getTipocli() {
		return Tipocli;
	}

	public void setTipocli(String tipocli) {
		Tipocli = tipocli;
	}
	
}
