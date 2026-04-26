package co.edu.uptc.sistienda.modelo;

import co.edu.uptc.sistienda.modelo.enums.TipoClienteEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoIdentificacionEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoPersonaEnum;

public class Cliente {

	private String codigoCliente;
	private String nombreCompletoORazonSocial;
	private TipoIdentificacionEnum tipoIdentificacion;
	private String numeroIdentificacion;
	private String direccion;
	private String ciudad; 
	private String telefono;
	private String correoElectronico; 
	private TipoClienteEnum tipoCliente;
	private boolean activo;
	private TipoPersonaEnum tipoPersona;
	private String responsabilidadFiscal; 
	private String responsabilidadTributaria; 
	

	public Cliente() {
		this.activo = true;
	}


	public String getCodigoCliente() {
		return codigoCliente;
	}


	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}


	public String getNombreCompletoORazonSocial() {
		return nombreCompletoORazonSocial;
	}


	public void setNombreCompletoORazonSocial(String nombreCompletoORazonSocial) {
		this.nombreCompletoORazonSocial = nombreCompletoORazonSocial;
	}


	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}


	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}


	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}


	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCorreoElectronico() {
		return correoElectronico;
	}


	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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


	public TipoPersonaEnum getTipoPersona() {
		return tipoPersona;
	}


	public void setTipoPersona(TipoPersonaEnum tipoPersona) {
		this.tipoPersona = tipoPersona;
	}


	public String getResponsabilidadFiscal() {
		return responsabilidadFiscal;
	}


	public void setResponsabilidadFiscal(String responsabilidadFiscal) {
		this.responsabilidadFiscal = responsabilidadFiscal;
	}


	public String getResponsabilidadTributaria() {
		return responsabilidadTributaria;
	}


	public void setResponsabilidadTributaria(String responsabilidadTributaria) {
		this.responsabilidadTributaria = responsabilidadTributaria;
	}

	
}