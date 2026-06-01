package co.uptc.edu.co.modelo.dto;

public class DetalleVentaDevolucionDTO {

	private String codigoProducto;
	private String nombreProducto;

	private int cantidadVendida;
	private int cantidadDevuelta;
	private int cantidadPendiente;

	private double precioUnitario;

	private double subtotalOriginal;
	private double impuestosOriginal;
	private double totalOriginal;

	private double valorDevuelto;
	private double impuestosDevueltos;
	private double totalDevuelto;

	private double subtotalPendiente;
	private double impuestoPendiente;
	private double totalPendiente;

	public DetalleVentaDevolucionDTO(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public DetalleVentaDevolucionDTO(String codigoProducto, String nombreProducto, int cantidadVendida,
			int cantidadDevuelta, int cantidadPendiente, double precioUnitario, double subtotalOriginal,
			double impuestosOriginal, double totalOriginal, double valorDevuelto, double impuestosDevueltos,
			double totalDevuelto, double subtotalPendiente, double impuestoPendiente,
			double totalPendiente) {

		this.codigoProducto = codigoProducto;
		this.nombreProducto = nombreProducto;
		this.cantidadVendida = cantidadVendida;
		this.cantidadDevuelta = cantidadDevuelta;
		this.cantidadPendiente = cantidadPendiente;
		this.precioUnitario = precioUnitario;
		this.subtotalOriginal = subtotalOriginal;
		this.impuestosOriginal = impuestosOriginal;
		this.totalOriginal = totalOriginal;
		this.valorDevuelto = valorDevuelto;
		this.impuestosDevueltos = impuestosDevueltos;
		this.totalDevuelto = totalDevuelto;
		this.subtotalPendiente = subtotalPendiente;
		this.impuestoPendiente = impuestoPendiente;
		this.totalPendiente = totalPendiente;
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

	public int getCantidadDevuelta() {
		return cantidadDevuelta;
	}

	public void setCantidadDevuelta(int cantidadDevuelta) {
		this.cantidadDevuelta = cantidadDevuelta;
	}

	public int getCantidadPendiente() {
		return cantidadPendiente;
	}

	public void setCantidadPendiente(int cantidadPendiente) {
		this.cantidadPendiente = cantidadPendiente;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public double getSubtotalOriginal() {
		return subtotalOriginal;
	}

	public void setSubtotalOriginal(double subtotalOriginal) {
		this.subtotalOriginal = subtotalOriginal;
	}

	public double getImpuestosOriginal() {
		return impuestosOriginal;
	}

	public void setImpuestosOriginal(double impuestosOriginal) {
		this.impuestosOriginal = impuestosOriginal;
	}

	public double getTotalOriginal() {
		return totalOriginal;
	}

	public void setTotalOriginal(double totalOriginal) {
		this.totalOriginal = totalOriginal;
	}

	public double getValorDevuelto() {
		return valorDevuelto;
	}

	public void setValorDevuelto(double valorDevuelto) {
		this.valorDevuelto = valorDevuelto;
	}

	public double getImpuestosDevueltos() {
		return impuestosDevueltos;
	}

	public void setImpuestosDevueltos(double impuestosDevueltos) {
		this.impuestosDevueltos = impuestosDevueltos;
	}

	public double getTotalDevuelto() {
		return totalDevuelto;
	}

	public void setTotalDevuelto(double totalDevuelto) {
		this.totalDevuelto = totalDevuelto;
	}

	public double getSubtotalPendiente() {
		return subtotalPendiente;
	}

	public void setSubtotalPendiente(double subtotalPendiente) {
		this.subtotalPendiente = subtotalPendiente;
	}

	public double getImpuestoPendiente() {
		return impuestoPendiente;
	}

	public void setImpuestoPendiente(double impuestoPendiente) {
		this.impuestoPendiente = impuestoPendiente;
	}

	public double getTotalPendiente() {
		return totalPendiente;
	}

	public void setTotalPendiente(double totalPendiente) {
		this.totalPendiente = totalPendiente;
	}
}
