package co.edu.uptc.tiendaminorista.modelo;

import co.edu.uptc.tiendaminorista.enums.TipoDocumentoEnum;

public class Cliente extends Persona {
    private TipoDocumentoEnum tipodoc;
    private String numeroIdentificacion;
    private String tipoCliente;

    public TipoDocumentoEnum getTipodoc() { return tipodoc; }
    public void setTipodoc(TipoDocumentoEnum tipodoc) { this.tipodoc = tipodoc; }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
}
