package co.uptc.edu.co.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;
import co.uptc.edu.co.modelo.enums.FormaPago;

public class Venta {

	private String numeroFactura;
	private LocalDateTime fechaHora;
	private String cliente;
	private List<DetalleVenta> detalles;
	private double subtotal;
	private FormaPago formaPago;
	private double impuestos;
	private double total;
	private EstadoVentaEnum estado;
	private String motivoAnulacion;
	private LocalDateTime fechaAnulacion;

	public Venta() {
		this.detalles = new ArrayList<>();
		this.estado = EstadoVentaEnum.ACTIVA;
	}

	public Venta(String numeroFactura, LocalDateTime fechaHora, String cliente, List<DetalleVenta> detalles,
			double subtotal, FormaPago formaPago, double impuestos, double total, EstadoVentaEnum estado) {

		this.numeroFactura = numeroFactura;
		this.fechaHora = fechaHora;
		this.cliente = cliente;
		this.detalles = detalles;
		this.subtotal = subtotal;
		this.formaPago = formaPago;
		this.impuestos = impuestos;
		this.total = total;
		this.estado = estado;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public List<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVenta> detalles) {
		this.detalles = detalles;
	}

	public double getSubTotal() {
		return subtotal;
	}

	public void setSubTotal(double subTotal) {
		this.subtotal = subTotal;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public double getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(double impuestos) {
		this.impuestos = impuestos;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public EstadoVentaEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoVentaEnum estado) {
		this.estado = estado;
	}

	public String getMotivoAnulacion() {
		return motivoAnulacion;
	}

	public void setMotivoAnulacion(String motivoAnulacion) {
		this.motivoAnulacion = motivoAnulacion;
	}

	public LocalDateTime getFechaAnulacion() {
		return fechaAnulacion;
	}

	public void setFechaAnulacion(LocalDateTime fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

}
