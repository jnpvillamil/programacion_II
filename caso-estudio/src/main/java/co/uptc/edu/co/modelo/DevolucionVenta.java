package co.uptc.edu.co.modelo;

import java.time.LocalDateTime;

public class DevolucionVenta {

	private String codigoDevolucion;
	private String numeroFactura;
	private String codigoProducto;
	private String nombreProducto;
	private int cantidadDevuelta;
	private double valorDevuelto;
	private LocalDateTime fechaHora;
	private String motivo;

	public DevolucionVenta() {

	}

	public DevolucionVenta(String codigoDevolucion, String numeroFactura, String codigoProducto, String nombreProducto,
			int cantidadDevuelta, double valorDevuelto, LocalDateTime fechaHora, String motivo) {

		this.codigoDevolucion = codigoDevolucion;
		this.numeroFactura = numeroFactura;
		this.codigoProducto = codigoProducto;
		this.nombreProducto = nombreProducto;
		this.cantidadDevuelta = cantidadDevuelta;
		this.valorDevuelto = valorDevuelto;
		this.fechaHora = fechaHora;
		this.motivo = motivo;

	}

	public String getCodigoDevolucion() {
		return codigoDevolucion;
	}

	public void setCodigoDevolucion(String codigoDevolucion) {
		this.codigoDevolucion = codigoDevolucion;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
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

	public int getCantidadDevuelta() {
		return cantidadDevuelta;
	}

	public void setCantidadDevuelta(int cantidadDevuelta) {
		this.cantidadDevuelta = cantidadDevuelta;
	}

	public double getValorDevuelto() {
		return valorDevuelto;
	}

	public void setValorDevuelto(double valorDevuelto) {
		this.valorDevuelto = valorDevuelto;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
