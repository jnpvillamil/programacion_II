package co.edu.uptc.negocio;

public class clienteDto {

    private String codigo;
    private String nombre;
    private String telefono;
    private String direccion;

    public String getCodigo() { 
    	return codigo; 
    	}
    
    public void setCodigo(String codigo) {
    	this.codigo = codigo; 
    	}

    public String getNombre() { 
    	return nombre;
    	}
    public void setNombre(String nombre) { 
    	this.nombre = nombre;
    	}

    public String getTelefono() { 
    	return telefono; 
    	}
    public void setTelefono(String telefono) {
    	this.telefono = telefono; 
    	}

    public String getDireccion() {
    	return direccion; 
    	}
    
    public void setDireccion(String direccion) { 
    	this.direccion = direccion;
    	}

    @Override
    public String toString() {
        return "clienteDto [codigo=" + codigo + ", nombre=" + nombre +", telefono=" + telefono + ", direccion=" + direccion + "]";
    }
}