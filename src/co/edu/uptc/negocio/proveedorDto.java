package co.edu.uptc.negocio;

public class proveedorDto {

    private String codigo;
    private String razonSocial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correo;

    public String getCodigo() { 
    	return codigo;
    	}
    
    public void setCodigo(String codigo) { 
    	this.codigo = codigo;
    	}

    public String getRazonSocial() { 
    	return razonSocial; 
    	}
    
    public void setRazonSocial(String razonSocial) { 
    	this.razonSocial = razonSocial;
    	}

    public String getNit() {
    	return nit; 
    	}
    
    public void setNit(String nit) { 
    	this.nit = nit; 
    	}

    public String getDireccion() { 
    	return direccion;
    	}
    public void setDireccion(String direccion) { 
    	this.direccion = direccion;
    	}

    public String getTelefono() { 
    	return telefono; 
    	}
    
    public void setTelefono(String telefono) {
    	this.telefono = telefono;
    	}

    public String getCorreo() { 
    	return correo; 
    	}
    public void setCorreo(String correo) {
    	this.correo = correo; 
    	}


    public String toString() {
        return "proveedorDto [codigo=" + codigo + ", razonSocial=" + razonSocial +", nit=" + nit + ", direccion=" + direccion +", telefono=" + telefono + ", correo=" + correo + "]";
    }
}