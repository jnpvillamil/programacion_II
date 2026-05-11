package co.edu.uptc.modelo;

public class Proveedor extends Persona {
    private String codigoProveedor;
    private String correoElectronico;
    private boolean activo;

    public Proveedor(String nombre, String identificacion, String direccion, String telefono, String codigoProveedor, String correoElectronico, boolean activo) {
        super(nombre, identificacion, direccion, telefono);
        this.codigoProveedor = codigoProveedor;
        this.correoElectronico = correoElectronico;
        this.activo = activo;
    }

    public Proveedor() {
        super();
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}