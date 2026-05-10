package co.edu.uptc.modelo;

import co.edu.uptc.enums.TipoDocumentoEnum;

public class Cliente {
    private String codigo;
    private String nombre;
    private TipoDocumentoEnum tipoIdentificacion;  
    private String numeroIdentificacion;
    private String direccion;
    private String telefono;
    private String tipoCliente;  
    private boolean activo;
    
    
    public Cliente(String codigo, String nombre, TipoDocumentoEnum tipoIdentificacion,
                   String numeroIdentificacion, String direccion, 
                   String telefono, String tipoCliente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipoCliente = tipoCliente;
        this.activo = true;
    }
    
   
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public TipoDocumentoEnum getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(TipoDocumentoEnum tipoIdentificacion) { 
        this.tipoIdentificacion = tipoIdentificacion; 
    }
    
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { 
        this.numeroIdentificacion = numeroIdentificacion; 
    }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
   
    public String getTipoIdentificacionString() {
        return tipoIdentificacion.name();
    }
    
    @Override
    public String toString() {
        return nombre + " (" + codigo + ")";
    }
}