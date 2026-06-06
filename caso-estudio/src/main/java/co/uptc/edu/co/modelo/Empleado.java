package co.uptc.edu.co.modelo;

public class Empleado {

	private double salarioEmpleado;

	public Empleado() {

	}

	public Empleado(double salarioEmpleado) {
		this.salarioEmpleado = salarioEmpleado;

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
