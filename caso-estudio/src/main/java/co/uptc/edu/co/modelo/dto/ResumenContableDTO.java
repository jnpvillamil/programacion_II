package co.uptc.edu.co.modelo.dto;

public class ResumenContableDTO {

	private double ingresos;
	private double egresos;
	private double utilidad;

	public ResumenContableDTO(double ingresos, double egresos, double utilidad) {
		this.ingresos = ingresos;
		this.egresos = egresos;
		this.utilidad = utilidad;
	}

	public double getIngresos() {
		return ingresos;
	}

	public void setIngresos(double ingresos) {
		this.ingresos = ingresos;
	}

	public double getEgresos() {
		return egresos;
	}

	public void setEgresos(double egresos) {
		this.egresos = egresos;
	}

	public double getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(double utilidad) {
		this.utilidad = utilidad;
	}
}
