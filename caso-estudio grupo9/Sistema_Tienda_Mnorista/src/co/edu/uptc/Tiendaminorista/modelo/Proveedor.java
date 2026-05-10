package co.edu.uptc.Tiendaminorista.modelo;

public class Proveedor extends Persona {

    private String nit;
    private String correo;
    
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    @Override
    public String toString() {
        return getNombre() + " (" + nit + ")";
    }
}