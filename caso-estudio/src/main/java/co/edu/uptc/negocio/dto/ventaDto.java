package co.edu.uptc.negocio.dto;

import java.util.ArrayList;
import java.util.List;

public class ventaDto {

	private int numeroFactura;
	private String fecha;
	private int codigoCliente;
	private String formaPago;
	private boolean aplicaIva;
	private double subtotal;
	private double total;

	// ¡La magia del Maestro-Detalle! Una factura contiene muchos productos.
	private List<detalleVentaDto> detalles;

	public ventaDto() {
		this.detalles = new ArrayList<>();
	}

	// --- GETTERS Y SETTERS ---
	public int getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(int numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public boolean isAplicaIva() {
		return aplicaIva;
	}

	public void setAplicaIva(boolean aplicaIva) {
		this.aplicaIva = aplicaIva;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<detalleVentaDto> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<detalleVentaDto> detalles) {
		this.detalles = detalles;
	}

	// Método útil para agregar productos al carrito del DTO
	public void agregarDetalle(detalleVentaDto detalle) {
		this.detalles.add(detalle);
	}
}