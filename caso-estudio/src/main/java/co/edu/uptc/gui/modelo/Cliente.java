package co.edu.uptc.gui.modelo;



import co.edu.uptc.enums.PoseeResponsabiliadTributaria;
import co.edu.uptc.enums.TipoClienteEnum;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.gui.interfaces.Gestionable;

public class Cliente extends Persona implements Gestionable {

    private TipoClienteEnum tipoCliente;
    private boolean activo;
    private String correoElectronico;
    private PoseeResponsabiliadTributaria responsableTributariamente;


	public Cliente() {
        this.activo = true; 
    }
	



	public Cliente(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
                   String numeroDocumento, String telefono, String direccion,
                   TipoClienteEnum tipoCliente, boolean activo, String correoElectronico, PoseeResponsabiliadTributaria responsableTributariamente) {
        super(codigo, nombre, apellido, tipoDocumento, numeroDocumento, telefono, direccion);
        this.tipoCliente = tipoCliente;
        this.activo = activo;
        this.correoElectronico= correoElectronico;
       
       
    }

    public TipoClienteEnum getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteEnum tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

    
    public PoseeResponsabiliadTributaria getResponsableTributariamente() {
		return responsableTributariamente;
	}

	public void setResponsableTributariamente(PoseeResponsabiliadTributaria responsableTributariamente) {
		this.responsableTributariamente = responsableTributariamente;
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
