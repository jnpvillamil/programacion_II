package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.gui.interfaces.Gestionable;

public class Proveedor extends Persona implements Gestionable {

    private String razonSocial;
    private String correoElectronico;
    private boolean activo;

    public Proveedor() {
        this.activo = true;
    }

    public Proveedor(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
            String numeroDocumento, String telefono, String direccion,
            String razonSocial, String correoElectronico, boolean activo) {
        super(codigo, nombre, apellido, tipoDocumento, numeroDocumento, telefono, direccion);
        this.razonSocial = razonSocial;
        this.correoElectronico = correoElectronico;
        this.activo = activo;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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

    @Override
    public void registrar() {
        this.activo = true;
    }

    @Override
    public void modificar() {
        System.out.println("Proveedor modificado");
    }

    @Override
    public void inactivar() {
        this.activo = false;
    }
}