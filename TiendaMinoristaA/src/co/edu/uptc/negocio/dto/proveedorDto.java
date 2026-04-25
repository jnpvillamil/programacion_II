package co.edu.uptc.negocio.dto;

public class proveedorDto {

    public static int contadorProveedor = 100;

    private int codigoProveedor;
    private String razonSocial;
    private String nit;
    private String direccion;
    private long telefono;
    private String correo;

    public proveedorDto() {
        this.codigoProveedor = contadorProveedor++;
    }

    public proveedorDto(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
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

    public long getTelefono() {
        return telefono;
    }
    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "proveedorDto [codigoProveedor=" + codigoProveedor + ", razonSocial=" + razonSocial
                + ", nit=" + nit + ", direccion=" + direccion
                + ", telefono=" + telefono + ", correo=" + correo + "]";
    }
}
