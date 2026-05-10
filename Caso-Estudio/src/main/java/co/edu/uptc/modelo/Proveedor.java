package co.edu.uptc.modelo;

public class Proveedor {
    private String codigo;
    private String razonSocial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correo;
    private boolean activo;
    
   
    public Proveedor(String codigo, String razonSocial, String nit, 
                     String direccion, String telefono, String correo) {
        this.codigo = codigo;
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.activo = true;
    }
    
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    @Override
    public String toString() {
        return razonSocial + " (" + codigo + ") - NIT: " + nit;
    }
}