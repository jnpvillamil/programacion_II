package co.edu.uptc.negocio.dto;

import java.util.ArrayList;
import java.util.List;

public class compraDto {
	private int idCompra;
	private String numeroFacturaProv;
	private int codigoProveedor;
	private double totalCompra;
	private double impuestos;
	private double subtotal;
	private String fecha;
	private String razonSocialProveedor;
	private List<itemCompraDto> detalles;

	public compraDto() {
		this.detalles = new ArrayList<>();
	}

	// MÉTODOS DE ACCESO ESTÁNDAR 

	// Acceso a Totales
	public double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(double t) {
		this.totalCompra = t;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double s) {
		this.subtotal = s;
	}

	public double getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(double i) {
		this.impuestos = i;
	}

	// Acceso a Datos de Factura/Proveedor
	public String getNumeroFacturaProv() {
		return numeroFacturaProv;
	}

	public void setNumeroFacturaProv(String n) {
		this.numeroFacturaProv = n;
	}

	public int getCodigoProveedor() {
		return codigoProveedor;
	}

	public void setCodigoProveedor(int c) {
		this.codigoProveedor = c;
	}

	public String getRazonSocialProveedor() {
		return razonSocialProveedor;
	}

	public void setRazonSocialProveedor(String r) {
		this.razonSocialProveedor = r;
	}

	// Acceso a Fecha
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String f) {
		this.fecha = f;
	}

	// Acceso a Detalles (Items)
	public List<itemCompraDto> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<itemCompraDto> d) {
		this.detalles = d;
	}

	// --- MÉTODOS DE INTEGRACIÓN (No son redundantes, son necesarios) ---
	// Son necesarios porque la Vista usa un nombre, la lógica usa otro.
	// Es mejor tenerlos aquí que cambiar 20 archivos distintos.

	public void agregarDetalle(itemCompraDto d) {
		this.detalles.add(d);
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}
}