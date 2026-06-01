package co.uptc.edu.co.modelo.dto;

public class DetalleUtilidadBrutaDTO {

	private String codigoProducto;
	private String nombreProducto;
	private int cantidadVendida;
	private double ventas;
	private double costoVenta;
	private double utilidad;

	public DetalleUtilidadBrutaDTO() {
	}

	public DetalleUtilidadBrutaDTO(String codigoProducto, String nombreProducto, int cantidadVendida, double ventas,
			double costoVenta, double utilidad) {
		this.codigoProducto = codigoProducto;
		this.nombreProducto = nombreProducto;
		this.cantidadVendida = cantidadVendida;
		this.ventas = ventas;
		this.costoVenta = costoVenta;
		this.utilidad = utilidad;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public int getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(int cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}

	public double getVentas() {
		return ventas;
	}

	public void setVentas(double ventas) {
		this.ventas = ventas;
	}

	public double getCostoVenta() {
		return costoVenta;
	}

	public void setCostoVenta(double costoVenta) {
		this.costoVenta = costoVenta;
	}

	public double getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(double utilidad) {
		this.utilidad = utilidad;
	}
}
