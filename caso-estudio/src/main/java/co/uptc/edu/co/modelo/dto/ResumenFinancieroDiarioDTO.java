package co.uptc.edu.co.modelo.dto;

import java.time.LocalDate;
import java.util.List;

public class ResumenFinancieroDiarioDTO {

	private LocalDate fecha;
	private double totalVentas;
	private double totalCompras;
	private double utilidadBruta;
	private List<ResumenFormaPagoDTO> ventasPorFormaPago;
	private List<ResumenProductoDTO> productosMasVendidos;
	private ResumenContableDTO resumenContable;
	private double ivaGenerado;
	private double ivaDescontable;

	public ResumenFinancieroDiarioDTO(LocalDate fecha, double totalVentas, double totalCompras, double utilidadBruta,
			List<ResumenFormaPagoDTO> ventasPorFormaPago, List<ResumenProductoDTO> productosMasVendidos,
			ResumenContableDTO resumenContable, double ivaGenerado, double ivaDescontable) {
		this.fecha = fecha;
		this.totalVentas = totalVentas;
		this.totalCompras = totalCompras;
		this.utilidadBruta = utilidadBruta;
		this.ventasPorFormaPago = ventasPorFormaPago;
		this.productosMasVendidos = productosMasVendidos;
		this.resumenContable = resumenContable;
		this.ivaGenerado = ivaGenerado;
		this.ivaDescontable = ivaDescontable;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public double getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(double totalVentas) {
		this.totalVentas = totalVentas;
	}

	public double getTotalCompras() {
		return totalCompras;
	}

	public void setTotalCompras(double totalCompras) {
		this.totalCompras = totalCompras;
	}

	public double getUtilidadBruta() {
		return utilidadBruta;
	}

	public void setUtilidadBruta(double utilidadBruta) {
		this.utilidadBruta = utilidadBruta;
	}

	public List<ResumenFormaPagoDTO> getVentasPorFormaPago() {
		return ventasPorFormaPago;
	}

	public void setVentasPorFormaPago(List<ResumenFormaPagoDTO> ventasPorFormaPago) {
		this.ventasPorFormaPago = ventasPorFormaPago;
	}

	public List<ResumenProductoDTO> getProductosMasVendidos() {
		return productosMasVendidos;
	}

	public void setProductosMasVendidos(List<ResumenProductoDTO> productosMasVendidos) {
		this.productosMasVendidos = productosMasVendidos;
	}

	public ResumenContableDTO getResumenContable() {
		return resumenContable;
	}

	public void setResumenContable(ResumenContableDTO resumenContable) {
		this.resumenContable = resumenContable;
	}

	public double getIvaGenerado() {
		return ivaGenerado;
	}

	public void setIvaGenerado(double ivaGenerado) {
		this.ivaGenerado = ivaGenerado;
	}

	public double getIvaDescontable() {
		return ivaDescontable;
	}

	public void setIvaDescontable(double ivaDescontable) {
		this.ivaDescontable = ivaDescontable;
	}
}
