package co.uptc.edu.co.modelo;

import java.util.ArrayList;
import java.util.List;

public class ResumenVentasDTO {

	private final String periodo;
	private final List<Venta> ventas;
	private final double subtotalVentas;
	private final double totalVentas;
	private final int cantidadVentas;
	private final double impuestos;

	public ResumenVentasDTO(String periodo, List<Venta> ventas, double subtotalVentas, double totalVentas,
			int cantidadVentas, double impuestos) {
		this.periodo = periodo;
		this.ventas = ventas != null ? new ArrayList<>(ventas) : new ArrayList<>();
		this.subtotalVentas = subtotalVentas;
		this.totalVentas = totalVentas;
		this.cantidadVentas = cantidadVentas;
		this.impuestos = impuestos;
	}

	public String getPeriodo() {
		return periodo;
	}

	public List<Venta> getVentas() {
		return new ArrayList<>(ventas);
	}

	public double getSubtotalVentas() {
		return subtotalVentas;
	}

	public double getTotalVentas() {
		return totalVentas;
	}

	public int getCantidadVentas() {
		return cantidadVentas;
	}

	public double getImpuestos() {
		return impuestos;
	}
}
