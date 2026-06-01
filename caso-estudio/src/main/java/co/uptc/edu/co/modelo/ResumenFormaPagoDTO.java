package co.uptc.edu.co.modelo;

import co.uptc.edu.co.modelo.enums.FormaPago;

public class ResumenFormaPagoDTO {

	private FormaPago formaPago;
	private int cantidadVentas;
	private double valorTotal;

	public ResumenFormaPagoDTO() {
	}

	public ResumenFormaPagoDTO(FormaPago formaPago, int cantidadVentas, double valorTotal) {
		this.formaPago = formaPago;
		this.cantidadVentas = cantidadVentas;
		this.valorTotal = valorTotal;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public int getCantidadVentas() {
		return cantidadVentas;
	}

	public void setCantidadVentas(int cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
