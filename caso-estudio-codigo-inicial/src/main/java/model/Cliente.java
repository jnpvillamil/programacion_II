package model;

public class Cliente {

// paso 1 crear clase modelo//
	    private String codigoCliente;
	    private String nombre;
	    private String apellido;
	    private String direccion;
	    private String telefono;
	    private String email;

	    public Cliente(String codigoCliente, String nombre,
	                   String apellido, String direccion,
	                   String telefono, String email) {

	        this.codigoCliente = codigoCliente;
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.direccion = direccion;
	        this.telefono = telefono;
	        this.email = email;
	    }

	    public String getCodigoCliente() {
	        return codigoCliente;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public String getApellido() {
	        return apellido;
	    }

	    public String getDireccion() {
	        return direccion;
	    }

	    public String getTelefono() {
	        return telefono;
	    }

	    public String getEmail() {
	        return email;
	    }

	    @Override
	    public String toString() {
	        return "Cliente{" +
	                "codigoCliente='" + codigoCliente + '\'' +
	                ", nombre='" + nombre + '\'' +
	                ", apellido='" + apellido + '\'' +
	                ", direccion='" + direccion + '\'' +
	                ", telefono='" + telefono + '\'' +
	                ", email='" + email + '\'' +
	                '}';
	    }
	}

