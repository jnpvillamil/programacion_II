package co.edu.uptc.negocio.dto;

public class itemCompraDto {

	private int codigoProducto;
	private String nombreProducto;
	private int cantidad;
	private double costoUnitario;
	private double subtotal;

	public itemCompraDto() {
	}

	public itemCompraDto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "itemCompraDto [codigoProducto=" + codigoProducto + ", nombreProducto=" + nombreProducto + ", cantidad="
				+ cantidad + ", costoUnitario=" + costoUnitario + ", subtotal=" + subtotal + "]";
	}
}