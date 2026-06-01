package co.edu.uptc.sistienda.contabilidad.modelo;

public class LineaMovimientoContable {

	private String tipoMovimiento;
	private String cuentaContable;
	private double valor;

	public LineaMovimientoContable() {
	}

	public LineaMovimientoContable(String tipoMovimiento, String cuentaContable, double valor) {
		this.tipoMovimiento = tipoMovimiento;
		this.cuentaContable = cuentaContable;
		this.valor = valor;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}
