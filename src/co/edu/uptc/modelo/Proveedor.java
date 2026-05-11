package co.edu.uptc.modelo;

public class Proveedor extends Persona {
    private String codigoProveedor;
    private String razonSocial; 
    private String nit; 
    private String correoElectronico; 
    private boolean activo; 

    public Proveedor(String nombre, String apellido, String identificacion, String direccion, String telefono, String codigoProveedor, String razonSocial, String nit, String correoElectronico, boolean activo) {
        super(nombre, apellido, identificacion, direccion, telefono);
        this.codigoProveedor = codigoProveedor;
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.correoElectronico = correoElectronico;
        this.activo = activo;
    }

    public String getCodigoProveedor() { return codigoProveedor; }
    public void setCodigoProveedor(String codigoProveedor) { this.codigoProveedor = codigoProveedor; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}