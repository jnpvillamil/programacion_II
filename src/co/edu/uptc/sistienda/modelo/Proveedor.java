package co.edu.uptc.sistienda.modelo;

public class Proveedor {

	private String codigoProveedor;
	private String razonSocial;
	private String nit;
	private String direccion;
	private String ciudad; 
	private String telefono;
	private String correoElectronico;
	private boolean activo;
	
	private String responsabilidadFiscal; 
	
	private String responsabilidadTributaria; 
	
	private String actividadEconomica; 

	public Proveedor() {
		this.activo = true;
	}

	public String getCodigoProveedor() {
		return codigoProveedor;
	}

	public void setCodigoProveedor(String codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
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
	
	public void setCiudad(String ciudad){
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public String getResponsabilidadFiscal(){
		return responsabilidadFiscal; 
	}
	
	public void setResponsabilidadFiscal(String responsabilidadFiscal){
		this.responsabilidadFiscal = responsabilidadFiscal;
	}
	
	public String getResponsabilidadTributaria() {
		return responsabilidadTributaria; 
	}
	
	public void setResponsabilidadTributaria(String responsabilidadTributaria){
		this.responsabilidadTributaria = responsabilidadTributaria; 
	}
	
	public String getActividadEconomica(){
		return actividadEconomica;
	}
	
	public void setActividadEcocomica(String actividadEconomica){
		this.actividadEconomica = actividadEconomica;
	}
}