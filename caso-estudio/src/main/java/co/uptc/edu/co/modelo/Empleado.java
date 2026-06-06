package co.uptc.edu.co.modelo;

public class Empleado {
	private String cargonEmpleado;
	private double salarioEmpleado;

	public Empleado() {
	}

	public Empleado(String cargonEmpleado, double salarioEmplead) {
		this.cargonEmpleado = cargonEmpleado;
		this.salarioEmpleado = salarioEmpleado;
	}

	public String getCargonEmpleado() {
		return cargonEmpleado;
	}

	public void setCargonEmpleado(String cargonEmpleado) {
		this.cargonEmpleado = cargonEmpleado;
	}

	public double getSalarioEmpleado() {
		return salarioEmpleado;
	}

	public void setSalarioEmpleado(double salarioEmpleado) {
		this.salarioEmpleado = salarioEmpleado;
	}

	@Override
	public String toString() {
		return "Empleado [salarioEmpleado=" + salarioEmpleado + "]";
	}

}
