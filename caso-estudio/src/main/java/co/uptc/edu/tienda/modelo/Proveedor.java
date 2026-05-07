package co.uptc.edu.tienda.modelo;

import co.uptc.edu.tienda.enums.EstadoEnum;

public class Proveedor {
	
	public static int contadorProveedor = 100;
	
	private int codigoProveedor;
	private String razonSocial;
	private String nit;
	private String direccionP;
	private long telefonoP;
	private String correoP;
	private EstadoEnum estado;
	
	public Proveedor() {
		this.codigoProveedor = contadorProveedor++;
	}
	
	public Proveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
	
	public static void setContador(int ultimoId) {
		contadorProveedor = ultimoId + 1;
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
	public String getDireccionP() {
		return direccionP;
	}
	public void setDireccionP(String direccionP) {
		this.direccionP = direccionP;
	}
	public long getTelefonoP() {
		return telefonoP;
	}
	public void setTelefonoP(long telefonoP) {
		this.telefonoP = telefonoP;
	}
	public String getCorreoP() {
		return correoP;
	}
	public void setCorreoP(String correoP) {
		this.correoP = correoP;
	}
	public int getCodigoProveedor() {
		return codigoProveedor;
	}
	
	public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
	

	
	

}
