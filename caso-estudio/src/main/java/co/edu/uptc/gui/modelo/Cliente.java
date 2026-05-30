package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.enums.TipoClienteEnum;
import co.edu.uptc.enums.PoseeResponsabiliadTributaria;

public class Cliente extends Persona {

    private TipoClienteEnum tipoCliente;
    private boolean activo;
    private String correoElectronico;
    private PoseeResponsabiliadTributaria responsableTributariamente;
    
    private int paisId;
    private int ciudadId;

    public Cliente() {
        this.activo = true;
    }

    public Cliente(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
                   String numeroDocumento, String telefono, String direccion, int paisId, int ciudadId,
                   TipoClienteEnum tipoCliente, boolean activo, String correoElectronico, 
                   PoseeResponsabiliadTributaria responsableTributariamente) {

        super(codigo, nombre, apellido, tipoDocumento, numeroDocumento, telefono, direccion);
        this.paisId = paisId;
        this.ciudadId = ciudadId;
        this.tipoCliente = tipoCliente;
        this.activo = activo;
        this.correoElectronico = correoElectronico;
        this.responsableTributariamente = responsableTributariamente;
    }

    // Getters y Setters específicos del Cliente
    public int getPaisId() { 
        return paisId; 
    }

    public int setPaisId(int paisId) { 
        return paisId; 
    }

    public int getCiudadId() { 
        return ciudadId; 
    }

    public int setCiudadId(int ciudadId) { 
        return ciudadId; 
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

    public String getCedula() {
        return getNumeroDocumento(); 
    }

	public void registrar() {
		// TODO Auto-generated method stub
		
	}

	public void modificar() {
		// TODO Auto-generated method stub
		
	}
}