package co.edu.uptc.negocio;
public class Cliente extends Persona {
   
	private String tipoIdentificacion, numeroIdentificacion,
	tipoCliente;
    
	public Cliente(String c, String n, 
			String tId, String numId, String tC) { super(c,n); this.setTipoIdentificacion(tId); 
			this.setNumeroIdentificacion(numId); this.setTipoCliente(tC); }

	public Object getCodigo() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
}