package co.uptc.edu.co.modelo;

import java.util.ArrayList;
import java.util.List;

public class ResumenUtilidadBrutaDTO {

	private String periodo;
	private List<DetalleUtilidadBrutaDTO> detalles;
	private double totalVentas;
	private double costoVentas;
	private double utilidadBruta;
	private int cantidadVentas;
	private int cantidadVendida;

	public ResumenUtilidadBrutaDTO() {
		this.detalles = new ArrayList<>();
	}

	public ResumenUtilidadBrutaDTO(String periodo, double totalVentas, double costoVentas, double utilidadBruta,
			int cantidadVentas) {
		this.periodo = periodo;
		this.detalles = new ArrayList<>();
		this.totalVentas = totalVentas;
		this.costoVentas = costoVentas;
		this.utilidadBruta = utilidadBruta;
		this.cantidadVentas = cantidadVentas;
	}

	public ResumenUtilidadBrutaDTO(String periodo, List<DetalleUtilidadBrutaDTO> detalles, double totalVentas,
			double costoVentas, double utilidadBruta, int cantidadVentas, int cantidadVendida) {
		this.periodo = periodo;
		this.detalles = detalles != null ? new ArrayList<>(detalles) : new ArrayList<>();
		this.totalVentas = totalVentas;
		this.costoVentas = costoVentas;
		this.utilidadBruta = utilidadBruta;
		this.cantidadVentas = cantidadVentas;
		this.cantidadVendida = cantidadVendida;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public List<DetalleUtilidadBrutaDTO> getDetalles() {
		return new ArrayList<>(detalles);
	}

	public void setDetalles(List<DetalleUtilidadBrutaDTO> detalles) {
		this.detalles = detalles != null ? new ArrayList<>(detalles) : new ArrayList<>();
	}

	public double getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(double totalVentas) {
		this.totalVentas = totalVentas;
	}

	public double getCostoVentas() {
		return costoVentas;
	}

	public void setCostoVentas(double costoVentas) {
		this.costoVentas = costoVentas;
	}

	public double getUtilidadBruta() {
		return utilidadBruta;
	}

	public void setUtilidadBruta(double utilidadBruta) {
		this.utilidadBruta = utilidadBruta;
	}

	public int getCantidadVentas() {
		return cantidadVentas;
	}

	public void setCantidadVentas(int cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}

	public int getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(int cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}
}
