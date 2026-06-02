package co.uptc.edu.co.negocio;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.TransaccionBD;
import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.dao.MovimientoContableDAO;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.TipoMovimientoContable;
import co.uptc.edu.co.modelo.enums.FormaPago;

public class GestionContabilidad implements IGestionContabilidad {

	private static final String PREFIJO_MOVIMIENTO = "MC";
	private static final String ORIGEN_VENTA = "VENTA";
	private static final String ORIGEN_COMPRA = "COMPRA";
	private static final String ORIGEN_ANULACION_COMPRA = "ANULACION_COMPRA";
	private static final String ORIGEN_ANULACION_VENTA = "ANULACION_VENTA";
	private static final String ORIGEN_DEVOLUCION_VENTA = "DEVOLUCION_VENTA";
	private static final String CUENTA_CAJA = "Caja";
	private static final String CUENTA_BANCOS = "Bancos";
	private static final String CUENTA_CUENTAS_POR_COBRAR = "Cuentas por Cobrar";
	private static final String CUENTA_PROVEEDORES = "Proveedores";
	private static final String CUENTA_INGRESOS = "Ingresos por Ventas";
	private static final String CUENTA_INVENTARIO = "Inventario";
	private static final String CUENTA_IVA_GENERADO = "IVA Generado";
	private static final String CUENTA_IVA_DESCONTABLE = "IVA Descontable";

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
			throw new IllegalStateException("Error al cargar movimientos contables.", e);
		}
	}

	@Override
	public void registrarIngresoPorVenta(Venta venta) throws Exception {
		TransaccionBD.ejecutar(conexion -> registrarIngresoPorVenta(conexion, venta));
		recargarMovimientos();
	}

	public void registrarIngresoPorVenta(Connection conexion, Venta venta) throws Exception {
		validarVenta(venta);

		LocalDate fecha = venta.getFechaHora().toLocalDate();
		String cuentaIngreso = obtenerCuentaIngreso(venta.getFormaPago());
		List<MovimientoContable> movimientosAGuardar = new ArrayList<>();

		movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, cuentaIngreso, venta.getTotal(),
				"Ingreso por venta " + venta.getNumeroFactura(), venta.getNumeroFactura()));

		movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, CUENTA_INGRESOS, venta.getSubTotal(),
				"Venta " + venta.getNumeroFactura(), venta.getNumeroFactura()));

		if (venta.getImpuestos() > 0) {
			movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, CUENTA_IVA_GENERADO,
					venta.getImpuestos(), "IVA generado venta " + venta.getNumeroFactura(), venta.getNumeroFactura()));
		}

		guardarMovimientos(conexion, movimientosAGuardar);
	}

	@Override
	public void registrarEgresoPorCompra(Compra compra) throws Exception {
		TransaccionBD.ejecutar(conexion -> registrarEgresoPorCompra(conexion, compra));
		recargarMovimientos();
	}

	@Override
	public void registrarEgresoPorCompra(Connection conexion, Compra compra) throws Exception {
		validarCompra(compra);

		LocalDate fecha = compra.getFecha();
		String cuentaEgreso = obtenerCuentaPago(compra.getFormaPago());
		List<MovimientoContable> movimientosAGuardar = new ArrayList<>();

		movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO, cuentaEgreso,
				compra.getTotalCompra(), "Egreso por compra " + compra.getNumeroFacturaProveedor(), ORIGEN_COMPRA,
				compra.getNumeroFacturaProveedor()));

		movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO,
				CUENTA_INVENTARIO, compra.getSubtotal(), "Compra " + compra.getNumeroFacturaProveedor(), ORIGEN_COMPRA,
				compra.getNumeroFacturaProveedor()));

		if (compra.getImpuestos() > 0) {
			movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO,
					CUENTA_IVA_DESCONTABLE, compra.getImpuestos(), "IVA compra " + compra.getNumeroFacturaProveedor(),
					ORIGEN_COMPRA, compra.getNumeroFacturaProveedor()));
		}

		guardarMovimientos(conexion, movimientosAGuardar);
	}

	@Override
	public void registrarReversoPorAnulacionCompra(Compra compra, String motivo) throws Exception {
		TransaccionBD.ejecutar(conexion -> registrarReversoPorAnulacionCompra(conexion, compra, motivo));
		recargarMovimientos();
	}

	@Override
	public void registrarReversoPorAnulacionCompra(Connection conexion, Compra compra, String motivo) throws Exception {
		validarCompra(compra);

		if (motivo == null || motivo.trim().isEmpty()) {
			throw new Exception("Debe ingresar un motivo de anulacion.");
		}

		LocalDate fecha = LocalDate.now();
		String cuentaPago = obtenerCuentaPago(compra.getFormaPago());
		String referencia = compra.getNumeroFacturaProveedor();
		List<MovimientoContable> movimientosAGuardar = new ArrayList<>();

		movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.INGRESO, cuentaPago,
				compra.getTotalCompra(), "Reverso egreso por anulacion compra " + referencia + ". Motivo: " + motivo,
				ORIGEN_ANULACION_COMPRA, referencia));

		movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.INGRESO,
				CUENTA_INVENTARIO, compra.getSubtotal(), "Reverso compra " + referencia + ". Motivo: " + motivo,
				ORIGEN_ANULACION_COMPRA, referencia));

		if (compra.getImpuestos() > 0) {
			movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.INGRESO,
					CUENTA_IVA_DESCONTABLE, compra.getImpuestos(),
					"Reverso IVA compra " + referencia + ". Motivo: " + motivo, ORIGEN_ANULACION_COMPRA, referencia));
		}

		guardarMovimientos(conexion, movimientosAGuardar);
	}

	@Override
	public void registrarReversoPorAnulacionVenta(Venta venta, String motivo) throws Exception {
		TransaccionBD.ejecutar(conexion -> registrarReversoPorAnulacionVenta(conexion, venta, motivo));
		recargarMovimientos();
	}

	public void registrarReversoPorAnulacionVenta(Connection conexion, Venta venta, String motivo) throws Exception {
		validarVenta(venta);

		LocalDate fecha = LocalDate.now();
		String cuentaIngreso = obtenerCuentaIngreso(venta.getFormaPago());
		String referencia = venta.getNumeroFactura();
		List<MovimientoContable> movimientosAGuardar = new ArrayList<>();

		movimientosAGuardar
				.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO, cuentaIngreso,
						venta.getTotal(), "Reverso ingreso por anulacion venta " + referencia + ". Motivo: " + motivo,
						ORIGEN_ANULACION_VENTA, referencia));

		movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO,
				CUENTA_INGRESOS, venta.getSubTotal(), "Reverso venta " + referencia + ". Motivo: " + motivo,
				ORIGEN_ANULACION_VENTA, referencia));

		if (venta.getImpuestos() > 0) {
			movimientosAGuardar
					.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO, CUENTA_IVA_GENERADO,
							venta.getImpuestos(), "Reverso IVA generado venta " + referencia + ". Motivo: " + motivo,
							ORIGEN_ANULACION_VENTA, referencia));
		}

		guardarMovimientos(conexion, movimientosAGuardar);
	}

	@Override
	public void registrarReversoPorDevolucionVenta(Venta venta, double subtotalDevuelto, double ivaDevuelto,
			String motivo) throws Exception {
		TransaccionBD.ejecutar(
				conexion -> registrarReversoPorDevolucionVenta(conexion, venta, subtotalDevuelto, ivaDevuelto, motivo));
		recargarMovimientos();
	}

	public void registrarReversoPorDevolucionVenta(Connection conexion, Venta venta, double subtotalDevuelto,
			double ivaDevuelto, String motivo) throws Exception {
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
		List<MovimientoContable> movimientosAGuardar = new ArrayList<>();

		movimientosAGuardar
				.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO, cuentaIngreso,
						totalDevuelto, "Reverso ingreso por devolucion venta " + referencia + ". Motivo: " + motivo,
						ORIGEN_DEVOLUCION_VENTA, referencia));

		movimientosAGuardar.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO,
				CUENTA_INGRESOS, subtotalDevuelto, "Reverso devolucion venta " + referencia + ". Motivo: " + motivo,
				ORIGEN_DEVOLUCION_VENTA, referencia));

		if (ivaDevuelto > 0) {
			movimientosAGuardar
					.add(crearMovimiento(movimientosAGuardar, fecha, TipoMovimientoContable.EGRESO, CUENTA_IVA_GENERADO,
							ivaDevuelto, "Reverso IVA devolucion venta " + referencia + ". Motivo: " + motivo,
							ORIGEN_DEVOLUCION_VENTA, referencia));
		}

		guardarMovimientos(conexion, movimientosAGuardar);
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
			throw new IllegalStateException("Error al buscar el movimiento contable por codigo: " + codigoTransaccion,
					e);
		}
	}

	private MovimientoContable crearMovimiento(List<MovimientoContable> pendientes, LocalDate fecha, String cuenta,
			double valor, String descripcion, String referencia) {
		return new MovimientoContable(generarCodigoMovimiento(pendientes), fecha, TipoMovimientoContable.INGRESO,
				cuenta, valor, descripcion, ORIGEN_VENTA, referencia);
	}

	private MovimientoContable crearMovimiento(List<MovimientoContable> pendientes, LocalDate fecha,
			TipoMovimientoContable tipoMovimiento, String cuenta, double valor, String descripcion, String origen,
			String referencia) {
		return new MovimientoContable(generarCodigoMovimiento(pendientes), fecha, tipoMovimiento, cuenta, valor,
				descripcion, origen, referencia);
	}

	private void guardarMovimientos(Connection conexion, List<MovimientoContable> movimientosAGuardar)
			throws Exception {
		movimientoContableDAO.guardarMovimientos(conexion, movimientosAGuardar);
		movimientos.addAll(movimientosAGuardar);
	}

	private String obtenerCuentaIngreso(FormaPago formaPago) {
		if (formaPago == FormaPago.TRANSFERENCIA || formaPago == FormaPago.TARJETA) {
			return CUENTA_BANCOS;
		}

		if (formaPago == FormaPago.CREDITO) {
			return CUENTA_CUENTAS_POR_COBRAR;
		}

		return CUENTA_CAJA;
	}

	private String obtenerCuentaPago(FormaPago formaPago) {
		if (formaPago == FormaPago.CREDITO) {
			return CUENTA_PROVEEDORES;
		}

		if (formaPago == FormaPago.TRANSFERENCIA) {
			return CUENTA_BANCOS;
		}

		return CUENTA_CAJA;
	}

	String generarCodigoMovimiento(List<MovimientoContable> pendientes) {
		int mayor = 0;

		for (MovimientoContable movimiento : movimientos) {
			mayor = Math.max(mayor, obtenerNumeroMovimiento(movimiento));
		}

		if (pendientes != null) {
			for (MovimientoContable movimiento : pendientes) {
				mayor = Math.max(mayor, obtenerNumeroMovimiento(movimiento));
			}
		}

		return String.format(PREFIJO_MOVIMIENTO + "%06d", mayor + 1);
	}

	private int obtenerNumeroMovimiento(MovimientoContable movimiento) {
		String codigo = movimiento.getCodigoTransaccion();

		if (codigo != null && codigo.matches(PREFIJO_MOVIMIENTO + "\\d{6}")) {
			return Integer.parseInt(codigo.substring(PREFIJO_MOVIMIENTO.length()));
		}

		return 0;
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

		if (compra.getFormaPago() == null) {
		    throw new Exception("La compra no tiene forma de pago.");
		}
	}
}
