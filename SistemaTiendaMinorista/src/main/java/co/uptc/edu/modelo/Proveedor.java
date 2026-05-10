package co.uptc.edu.modelo;

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

    // GETTERS

    public String getCodigo() {
        return codigo;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean isActivo() {
        return activo;
    }

    // SETTERS

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return codigo + " - " + razonSocial;
    }
}