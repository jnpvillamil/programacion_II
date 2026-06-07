package co.uptc.edu.co.modelo;

public class Empleado {
	private String cargoEmpleado;
	private double salarioEmpleado;

	public Empleado() {
	}

	public Empleado(String cargoEmpleado, double salarioEmpleado) {
		this.cargoEmpleado = cargoEmpleado;
		this.salarioEmpleado = salarioEmpleado;
	}


	public String getCargoEmpleado() {
		return cargoEmpleado;
	}

	public void setCargoEmpleado(String cargoEmpleado) {
		this.cargoEmpleado = cargoEmpleado;
	}

	public double getSalarioEmpleado() {
		return salarioEmpleado;
	}

	public void setSalarioEmpleado(double salarioEmpleado) {
		this.salarioEmpleado = salarioEmpleado;
	}

	@Override
	public String toString() {
		return "Empleado [cargoEmpleado=" + cargoEmpleado + ", salarioEmpleado=" + salarioEmpleado + "]";
	}
}
