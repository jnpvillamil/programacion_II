package co.edu.uptc.sistienda.negocio;

import java.time.LocalDate;
import java.util.List;

import co.edu.uptc.sistienda.compras.modelo.Compra;
import co.edu.uptc.sistienda.contabilidad.modelo.MovimientoContable;
import co.edu.uptc.sistienda.interfaces.IGestionContabilidad;
import co.edu.uptc.sistienda.modelo.Venta;

public class GestionContabilidad {

	private IGestionContabilidad repositorioContabilidad;
	private int consecutivoMovimiento = 1;

	public GestionContabilidad(IGestionContabilidad repositorioContabilidad) {
		this.repositorioContabilidad = repositorioContabilidad;
		this.consecutivoMovimiento = calcularSiguienteConsecutivo();
	}

	public void registrarMovimientoPorVenta(Venta venta) throws Exception {
		if (venta == null) {
			throw new Exception("No se puede crear movimiento contable sin venta.");
		}

		MovimientoContable movimiento = new MovimientoContable(generarCodigoMovimiento(), "VENTA",
				venta.getNumeroFactura(),
				venta.getCliente() != null ? venta.getCliente().getNombreCompletoORazonSocial() : "Cliente sin nombre",
				"Registro contable por venta");

		movimiento.setFechaMovimiento(venta.getFechaHora().toLocalDate());
		movimiento.agregarLinea("DEBITO", cuentaIngresoSegunFormaPago(venta), venta.getTotal());
		movimiento.agregarLinea("CREDITO", "Ingresos por ventas", venta.getSubtotal());
		movimiento.agregarLinea("CREDITO", "IVA generado", venta.getIva());

		if (!movimiento.estaCuadrado()) {
			throw new Exception("El movimiento contable de la venta no esta cuadrado.");
		}
		repositorioContabilidad.guardarMovimiento(movimiento);
	}

	public void registrarMovimientoPorCompra(Compra compra) throws Exception {
		if (compra == null) {
			throw new Exception("No se puede crear movimiento contable sin compra.");
		}

		MovimientoContable movimiento = new MovimientoContable(generarCodigoMovimiento(), "COMPRA",
				compra.getNumeroCompra(),
				compra.getProveedor() != null ? compra.getProveedor().getRazonSocial() : "Proveedor sin nombre",
				"Registro contable por compra");

		movimiento.setFechaMovimiento(compra.getFechaCompra());
		movimiento.agregarLinea("DEBITO", "Inventario de mercancias", compra.getTotalCompra());
		movimiento.agregarLinea("CREDITO", "Caja / Bancos", compra.getTotalCompra());

		if (!movimiento.estaCuadrado()) {
			throw new Exception("El movimiento contable de la compra no esta cuadrado.");
		}
		repositorioContabilidad.guardarMovimiento(movimiento);
	}

	public void anularMovimientoPorDocumento(String documentoOrigen) {
		repositorioContabilidad.anularMovimientoPorDocumento(documentoOrigen);
	}

	public List<MovimientoContable> obtenerListaMovimientos() {
		return repositorioContabilidad.obtenerListaMovimientos();
	}

	public List<MovimientoContable> consultarMovimientos(String cuentaContable, LocalDate fechaInicio,
			LocalDate fechaFin) {
		return repositorioContabilidad.consultarMovimientos(cuentaContable, fechaInicio, fechaFin);
	}

	private String generarCodigoMovimiento() {
		return String.format("MC-%05d", consecutivoMovimiento++);
	}

	private int calcularSiguienteConsecutivo() {
		int mayor = 0;
		for (MovimientoContable movimiento : repositorioContabilidad.obtenerListaMovimientos()) {
			String codigo = movimiento.getCodigoMovimiento();
			if (codigo != null && codigo.startsWith("MC-")) {
				try {
					mayor = Math.max(mayor, Integer.parseInt(codigo.substring(3)));
				} catch (NumberFormatException ignored) {
					// Un codigo manual no afecta el consecutivo automatico
				}
			}
		}
		return mayor + 1;
	}

	private String cuentaIngresoSegunFormaPago(Venta venta) {
		if (venta.getFormaPago() == null) {
			return "Caja / Bancos";
		}
		String descripcion = venta.getFormaPago().getDescripcion().toLowerCase();
		if (descripcion.contains("credito") || descripcion.contains("crédito")) {
			return "Clientes";
		}
		return "Caja / Bancos";
	}
}
