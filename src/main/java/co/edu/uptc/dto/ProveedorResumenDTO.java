package co.edu.uptc.dto;

public class ProveedorResumenDTO {
    private String nit;
    private String razonSocial;
    private String correo;
    private String telefono;
    private String estado;

    public ProveedorResumenDTO(String nit, String razonSocial, String correo, String telefono, String estado) {
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.correo = correo;
        this.telefono = telefono;
        this.estado = estado;
    }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}