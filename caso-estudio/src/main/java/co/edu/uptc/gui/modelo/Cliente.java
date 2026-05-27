package co.edu.uptc.gui.modelo;

import co.edu.uptc.gui.interfaces.Gestionable;
import co.edu.uptc.dao.ClienteDao;
import co.edu.uptc.enums.TipoDocumentoEnum;
import co.edu.uptc.enums.TipoClienteEnum;
import co.edu.uptc.enums.PoseeResponsabiliadTributaria;

public class Cliente extends Persona implements Gestionable {

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



    public int getPaisId() { 
        return paisId; 
    }

    public void setPaisId(int paisId) { 
        this.paisId = paisId; 
    }

    public int getCiudadId() { 
        return ciudadId; 
    }

    public void setCiudadId(int ciudadId) { 
        this.ciudadId = ciudadId; 
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

    @Override
    public void registrar() {
        this.activo = true;
        ClienteDao miDao = new ClienteDao();
        miDao.registrarCliente(this);
    }

    @Override
    public void modificar() {
        ClienteDao miDao = new ClienteDao();
        miDao.actualizarCliente(this);
    }

    @Override
    public void inactivar() {
        this.activo = false;
        ClienteDao miDao = new ClienteDao();
        miDao.actualizarCliente(this);
    }
}