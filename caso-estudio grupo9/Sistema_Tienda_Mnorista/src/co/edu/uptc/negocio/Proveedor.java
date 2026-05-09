package co.edu.uptc.negocio;

public class Proveedor {
    private String razonSocial;
    private String nit;
    private String telefono;
    private String correo;

    public Proveedor(String razonSocial, String nit, String telefono, String correo) {
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.telefono = telefono;
        this.correo = correo;
    }

    //Getters/Setters
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}