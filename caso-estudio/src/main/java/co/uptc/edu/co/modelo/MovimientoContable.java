package co.uptc.edu.co.modelo;

import java.time.LocalDate;

import co.uptc.edu.co.modelo.enums.TipoMovimientoContable;

public class MovimientoContable {
	private String codigoTransaccion;
	private LocalDate fecha;
	private TipoMovimientoContable tipoMovimientoContable;
	private String cuentaContable;
	private Double valor;
	private String descripcion;

	public MovimientoContable() {
	}

	public MovimientoContable(String codigoTransaccion, LocalDate fecha, TipoMovimientoContable tipoMovimientoContable,
			String cuentaContable, Double valor, String descripcion) {
		this.codigoTransaccion = codigoTransaccion;
		this.fecha = fecha;
		this.tipoMovimientoContable = tipoMovimientoContable;
		this.cuentaContable = cuentaContable;
		this.valor = valor;
		this.descripcion = descripcion;
	}

	public String getCodigoTransaccion() {
		return codigoTransaccion;
	}

	public void setCodigoTransaccion(String codigoTransaccion) {
		this.codigoTransaccion = codigoTransaccion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public TipoMovimientoContable getTipoMovimientoContable() {
		return tipoMovimientoContable;
	}

	public void setTipoMovimientoContable(TipoMovimientoContable tipoMovimientoContable) {
		this.tipoMovimientoContable = tipoMovimientoContable;
	}

	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
