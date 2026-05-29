package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.MovimientoContableDAO;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.TipoMovimientoContable;

public class GestionContabilidad implements IGestionContabilidad {

	private static final String PREFIJO_MOVIMIENTO = "MC";
	private static final String ORIGEN_VENTA = "VENTA";
	private static final String ORIGEN_COMPRA = "COMPRA";
	private static final String ORIGEN_ANULACION_COMPRA = "ANULACION_COMPRA";
	private static final String ORIGEN_ANULACION_VENTA = "ANULACION_VENTA";
	private static final String ORIGEN_DEVOLUCION_VENTA = "DEVOLUCION_VENTA";
	private static final String CUENTA_CAJA = "Caja";
	private static final String CUENTA_BANCOS = "Bancos";
	private static final String CUENTA_INGRESOS = "Ingresos por Ventas";
	private static final String CUENTA_COMPRAS = "Compras";
	private static final String CUENTA_IVA_GENERADO = "IVA Generado";
	private static final String CUENTA_IVA_COMPRAS = "IVA en Compras";

	private final MovimientoContableDAO movimientoContableDAO;
	private List<MovimientoContable> movimientos;

	public GestionContabilidad(MovimientoContableDAO movimientoContableDAO) {
		if (movimientoContableDAO == null) {
			throw new IllegalArgumentException("El MovimientoContableDAO no puede ser nulo.");
		}

		this.movimientoContableDAO = movimientoContableDAO;

		try {
			movimientos = movimientoContableDAO.listarMovimientos();
		} catch (Exception e) {
			movimientos = new ArrayList<>();
			System.out.println("Error al cargar movimientos contables: " + e.getMessage());
		}
	}

	@Override
	public void registrarIngresoPorVenta(Venta venta) throws Exception {
		validarVenta(venta);

		LocalDate fecha = venta.getFechaHora().toLocalDate();
		String cuentaIngreso = obtenerCuentaIngreso(venta.getFormaPago());

		guardarMovimiento(crearMovimiento(fecha, cuentaIngreso, venta.getTotal(),
				"Ingreso por venta " + venta.getNumeroFactura(), venta.getNumeroFactura()));

		guardarMovimiento(crearMovimiento(fecha, CUENTA_INGRESOS, venta.getSubTotal(),
				"Venta " + venta.getNumeroFactura(), venta.getNumeroFactura()));

		if (venta.getImpuestos() > 0) {
			guardarMovimiento(crearMovimiento(fecha, CUENTA_IVA_GENERADO, venta.getImpuestos(),
					"IVA generado venta " + venta.getNumeroFactura(), venta.getNumeroFactura()));
		}

		recargarMovimientos();
	}

	@Override
	public void registrarEgresoPorCompra(Compra compra) throws Exception {
		validarCompra(compra);

		LocalDate fecha = compra.getFecha();
		String cuentaEgreso = obtenerCuentaPago(compra.getFormaPago());

		guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, cuentaEgreso, compra.getTotalCompra(),
				"Egreso por compra " + compra.getNumeroFacturaProveedor(), ORIGEN_COMPRA,
				compra.getNumeroFacturaProveedor()));

		guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, CUENTA_COMPRAS,
				compra.getSubtotal(), "Compra " + compra.getNumeroFacturaProveedor(), ORIGEN_COMPRA,
				compra.getNumeroFacturaProveedor()));

		if (compra.getImpuestos() > 0) {
			guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, CUENTA_IVA_COMPRAS,
					compra.getImpuestos(), "IVA compra " + compra.getNumeroFacturaProveedor(), ORIGEN_COMPRA,
					compra.getNumeroFacturaProveedor()));
		}

		recargarMovimientos();
	}

	@Override
	public void registrarReversoPorAnulacionCompra(Compra compra, String motivo) throws Exception {
		validarCompra(compra);

		if (motivo == null || motivo.trim().isEmpty()) {
			throw new Exception("Debe ingresar un motivo de anulacion.");
		}

		LocalDate fecha = LocalDate.now();
		String cuentaPago = obtenerCuentaPago(compra.getFormaPago());
		String referencia = compra.getNumeroFacturaProveedor();

		guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.INGRESO, cuentaPago, compra.getTotalCompra(),
				"Reverso egreso por anulacion compra " + referencia + ". Motivo: " + motivo,
				ORIGEN_ANULACION_COMPRA, referencia));

		guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.INGRESO, CUENTA_COMPRAS,
				compra.getSubtotal(), "Reverso compra " + referencia + ". Motivo: " + motivo,
				ORIGEN_ANULACION_COMPRA, referencia));

		if (compra.getImpuestos() > 0) {
			guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.INGRESO, CUENTA_IVA_COMPRAS,
					compra.getImpuestos(), "Reverso IVA compra " + referencia + ". Motivo: " + motivo,
					ORIGEN_ANULACION_COMPRA, referencia));
		}

		recargarMovimientos();
	}

	@Override
	public void registrarReversoPorAnulacionVenta(Venta venta, String motivo) throws Exception {
		validarVenta(venta);

		LocalDate fecha = LocalDate.now();
		String cuentaIngreso = obtenerCuentaIngreso(venta.getFormaPago());
		String referencia = venta.getNumeroFactura();

		guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, cuentaIngreso, venta.getTotal(),
				"Reverso ingreso por anulacion venta " + referencia + ". Motivo: " + motivo,
				ORIGEN_ANULACION_VENTA, referencia));

		guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, CUENTA_INGRESOS, venta.getSubTotal(),
				"Reverso venta " + referencia + ". Motivo: " + motivo, ORIGEN_ANULACION_VENTA, referencia));

		if (venta.getImpuestos() > 0) {
			guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, CUENTA_IVA_GENERADO,
					venta.getImpuestos(), "Reverso IVA generado venta " + referencia + ". Motivo: " + motivo,
					ORIGEN_ANULACION_VENTA, referencia));
		}

		recargarMovimientos();
	}

	@Override
	public void registrarReversoPorDevolucionVenta(Venta venta, double subtotalDevuelto, double ivaDevuelto,
			String motivo) throws Exception {
		validarVenta(venta);

		if (subtotalDevuelto <= 0) {
			throw new Exception("El subtotal devuelto debe ser mayor a cero.");
		}

		if (ivaDevuelto < 0) {
			throw new Exception("El IVA devuelto no puede ser negativo.");
		}

		LocalDate fecha = LocalDate.now();
		String cuentaIngreso = obtenerCuentaIngreso(venta.getFormaPago());
		String referencia = venta.getNumeroFactura();
		double totalDevuelto = subtotalDevuelto + ivaDevuelto;

		guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, cuentaIngreso, totalDevuelto,
				"Reverso ingreso por devolucion venta " + referencia + ". Motivo: " + motivo,
				ORIGEN_DEVOLUCION_VENTA, referencia));

		guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, CUENTA_INGRESOS, subtotalDevuelto,
				"Reverso devolucion venta " + referencia + ". Motivo: " + motivo, ORIGEN_DEVOLUCION_VENTA,
				referencia));

		if (ivaDevuelto > 0) {
			guardarMovimiento(crearMovimiento(fecha, TipoMovimientoContable.EGRESO, CUENTA_IVA_GENERADO, ivaDevuelto,
					"Reverso IVA devolucion venta " + referencia + ". Motivo: " + motivo,
					ORIGEN_DEVOLUCION_VENTA, referencia));
		}

		recargarMovimientos();
	}

	@Override
	public List<MovimientoContable> obtenerMovimientos() {
		return new ArrayList<>(movimientos);
	}

	@Override
	public MovimientoContable buscarMovimientoPorCodigo(String codigoTransaccion) {
		if (codigoTransaccion == null) {
			return null;
		}

		for (MovimientoContable movimiento : movimientos) {
			if (codigoTransaccion.equalsIgnoreCase(movimiento.getCodigoTransaccion())) {
				return movimiento;
			}
		}

		try {
			return movimientoContableDAO.buscarPorCodigo(codigoTransaccion);
		} catch (Exception e) {
			System.out.println("Error al buscar movimiento contable: " + e.getMessage());
			return null;
		}
	}

	private MovimientoContable crearMovimiento(LocalDate fecha, String cuenta, double valor, String descripcion,
			String referencia) {
		return new MovimientoContable(generarCodigoMovimiento(), fecha, TipoMovimientoContable.INGRESO, cuenta, valor,
				descripcion, ORIGEN_VENTA, referencia);
	}

	private MovimientoContable crearMovimiento(LocalDate fecha, TipoMovimientoContable tipoMovimiento, String cuenta,
			double valor, String descripcion, String origen, String referencia) {
		return new MovimientoContable(generarCodigoMovimiento(), fecha, tipoMovimiento, cuenta, valor, descripcion,
				origen, referencia);
	}

	private void guardarMovimiento(MovimientoContable movimiento) throws Exception {
		movimientoContableDAO.guardarMovimiento(movimiento);
		movimientos.add(movimiento);
	}

	private String obtenerCuentaIngreso(String formaPago) {
		if (formaPago != null && formaPago.equalsIgnoreCase("Transferencia")) {
			return CUENTA_BANCOS;
		}

		return CUENTA_CAJA;
	}

	private String obtenerCuentaPago(String formaPago) {
		if (formaPago != null && formaPago.equalsIgnoreCase("Transferencia")) {
			return CUENTA_BANCOS;
		}

		return CUENTA_CAJA;
	}

	private String generarCodigoMovimiento() {
		int mayor = 0;

		for (MovimientoContable movimiento : movimientos) {
			String codigo = movimiento.getCodigoTransaccion();

			if (codigo != null && codigo.matches(PREFIJO_MOVIMIENTO + "\\d{6}")) {
				int numero = Integer.parseInt(codigo.substring(PREFIJO_MOVIMIENTO.length()));

				if (numero > mayor) {
					mayor = numero;
				}
			}
		}

		return String.format(PREFIJO_MOVIMIENTO + "%06d", mayor + 1);
	}

	private void recargarMovimientos() throws Exception {
		movimientos = movimientoContableDAO.listarMovimientos();
	}

	private void validarVenta(Venta venta) throws Exception {
		if (venta == null) {
			throw new Exception("La venta no puede ser nula.");
		}

		if (venta.getNumeroFactura() == null || venta.getNumeroFactura().trim().isEmpty()) {
			throw new Exception("La venta no tiene numero de factura.");
		}

		if (venta.getFechaHora() == null) {
			throw new Exception("La venta no tiene fecha.");
		}

		if (venta.getTotal() <= 0) {
			throw new Exception("La venta no tiene valor contable.");
		}
	}

	private void validarCompra(Compra compra) throws Exception {
		if (compra == null) {
			throw new Exception("La compra no puede ser nula.");
		}

		if (compra.getNumeroFacturaProveedor() == null || compra.getNumeroFacturaProveedor().trim().isEmpty()) {
			throw new Exception("La compra no tiene número de factura.");
		}

		if (compra.getFecha() == null) {
			throw new Exception("La compra no tiene fecha.");
		}

		if (compra.getTotalCompra() <= 0) {
			throw new Exception("La compra no tiene valor contable.");
		}

		if (compra.getFormaPago() == null || compra.getFormaPago().trim().isEmpty()) {
			throw new Exception("La compra no tiene forma de pago.");
		}
	}
}
