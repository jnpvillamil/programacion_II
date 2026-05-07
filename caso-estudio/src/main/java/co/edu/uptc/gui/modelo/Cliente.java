package co.edu.uptc.gui.modelo;



import co.edu.uptc.enums.TipoClienteEnum;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.gui.interfaces.Gestionable;

public class Cliente extends Persona implements Gestionable {

    private TipoClienteEnum tipoCliente;
    private boolean activo;

    public Cliente() {
        this.activo = true; //porque
    }

    public Cliente(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
                   String numeroDocumento, String telefono, String direccion,
                   TipoClienteEnum tipoCliente, boolean activo) {
        super(codigo, nombre, apellido, tipoDocumento, numeroDocumento, telefono, direccion);
        this.tipoCliente = tipoCliente;
        this.activo = activo;
    }

    public TipoClienteEnum getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteEnum tipoCliente) {
        this.tipoCliente = tipoCliente;
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
        System.out.println("Cliente modificado");
    }

    @Override
    public void inactivar() {
        this.activo = false;
    }

}
