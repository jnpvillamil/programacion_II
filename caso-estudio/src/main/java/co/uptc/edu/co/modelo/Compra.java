package co.uptc.edu.co.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import co.uptc.edu.co.modelo.enums.EstadoCompraEnum;
import co.uptc.edu.co.modelo.enums.FormaPago;
public class Compra {
	private String numeroFacturaProveedor;
	private LocalDate fecha;
	private String codigoProveedor;
	private String proveedor;
	private FormaPago formaPago;
	private List<DetalleCompra> detalles;
	private double subtotal;
	private double impuestos;
	private double totalCompra;
	private EstadoCompraEnum estado;
	private String motivoAnulacion;

	public Compra() {
		this.detalles = new ArrayList<>();
		this.estado = EstadoCompraEnum.ACTIVA;
	}

	public Compra(String numeroFacturaProveedor, LocalDate fecha, String codigoProveedor, String proveedor,
			FormaPago formaPago, double subtotal, double impuestos, double totalCompra, EstadoCompraEnum estado) {
		this.numeroFacturaProveedor = numeroFacturaProveedor;
		this.fecha = fecha;
		this.codigoProveedor = codigoProveedor;
		this.proveedor = proveedor;
		this.formaPago = formaPago;
		this.subtotal = subtotal;
		this.impuestos = impuestos;
		this.totalCompra = totalCompra;
		this.estado = estado;
	}



	public String getNumeroFacturaProveedor() {
		return numeroFacturaProveedor;
	}

	public void setNumeroFacturaProveedor(String numeroFacturaProveedor) {
		this.numeroFacturaProveedor = numeroFacturaProveedor;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getCodigoProveedor() {
		return codigoProveedor;
	}

	public void setCodigoProveedor(String codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
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

	public double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(double totalCompra) {
		this.totalCompra = totalCompra;
	}

	public List<DetalleCompra> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleCompra> detalles) {
		this.detalles = detalles;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public EstadoCompraEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoCompraEnum estado) {
		this.estado = estado;
	}

	public String getMotivoAnulacion() {
		return motivoAnulacion;
	}

	public void setMotivoAnulacion(String motivoAnulacion) {
		this.motivoAnulacion = motivoAnulacion;
	}
	@Override
	public String toString() {
		return "Compra [numeroFacturaProveedor=" + numeroFacturaProveedor + ", fecha=" + fecha
				+ ", codigoProveedor=" + codigoProveedor + ", proveedor=" + proveedor
				+ ", formaPago=" + formaPago + ", estado=" + getEstado()
				+ ", motivoAnulacion=" + motivoAnulacion
				+ ", detalles=" + (detalles != null ? detalles.size() : 0)
				+ "]";


}
}
