package co.edu.uptc.Tiendaminorista.modelo;

import co.edu.uptc.Tiendaminorista.Enum.TipoDcoumenEnum;

public class Cliente extends Persona{
    private TipoDcoumenEnum tipodoc;
    private String numeroIdentificacion;
    private String tipoCliente;

	
	public TipoDcoumenEnum getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(TipoDcoumenEnum tipodoc) {
		this.tipodoc = tipodoc;
	}
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
    
    
}
