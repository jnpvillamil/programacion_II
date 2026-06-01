package co.edu.uptc.sistienda.contabilidad.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimientoContable {

	private String codigoMovimiento;
	private LocalDate fechaMovimiento;
	private String tipoOperacion;
	private String documentoOrigen;
	private String tercero;
	private String descripcion;
	private boolean anulado;
	private List<LineaMovimientoContable> lineas;

	public MovimientoContable() {
		fechaMovimiento = LocalDate.now();
		lineas = new ArrayList<>();
		anulado = false;
	}

	public MovimientoContable(String codigoMovimiento, String tipoOperacion, String documentoOrigen, String tercero,
			String descripcion) {
		this();
		this.codigoMovimiento = codigoMovimiento;
		this.tipoOperacion = tipoOperacion;
		this.documentoOrigen = documentoOrigen;
		this.tercero = tercero;
		this.descripcion = descripcion;
	}

	public void agregarLinea(String tipoMovimiento, String cuentaContable, double valor) {
		if (valor > 0) {
			lineas.add(new LineaMovimientoContable(tipoMovimiento, cuentaContable, valor));
		}
	}

	public double getTotalDebito() {
		double total = 0;
		for (LineaMovimientoContable linea : lineas) {
			if ("DEBITO".equalsIgnoreCase(linea.getTipoMovimiento())) {
				total += linea.getValor();
			}
		}
		return total;
	}

	public double getTotalCredito() {
		double total = 0;
		for (LineaMovimientoContable linea : lineas) {
			if ("CREDITO".equalsIgnoreCase(linea.getTipoMovimiento())) {
				total += linea.getValor();
			}
		}
		return total;
	}

	public boolean estaCuadrado() {
		return Math.abs(getTotalDebito() - getTotalCredito()) < 0.01;
	}

	public String getCodigoMovimiento() {
		return codigoMovimiento;
	}

	public void setCodigoMovimiento(String codigoMovimiento) {
		this.codigoMovimiento = codigoMovimiento;
	}

	public LocalDate getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(LocalDate fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getDocumentoOrigen() {
		return documentoOrigen;
	}

	public void setDocumentoOrigen(String documentoOrigen) {
		this.documentoOrigen = documentoOrigen;
	}

	public String getTercero() {
		return tercero;
	}

	public void setTercero(String tercero) {
		this.tercero = tercero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isAnulado() {
		return anulado;
	}

	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}

	public List<LineaMovimientoContable> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaMovimientoContable> lineas) {
		this.lineas = lineas;
	}
}
