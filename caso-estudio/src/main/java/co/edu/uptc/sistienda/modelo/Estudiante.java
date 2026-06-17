package co.edu.uptc.sistienda.modelo;

public class Estudiante {

	public Estudiante() {
    }
	private String cedula;

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	} 
 

       private String nombre;


	    public Estudiante(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }
	
	public String telefono;

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}


