package co.uptc.edu.co.negocio;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.uptc.edu.co.conexion.TransaccionBD;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.enums.EstadoCompraEnum;
import co.uptc.edu.co.modelo.enums.CategoriaProductoEnum;

public class GestionCompra {
	private static final String PREFIJO_FACTURA_COMPRA = "FC-";
	private static final int DIGITOS_FACTURA_COMPRA = 5;
	private static final int CONSECUTIVO_FACTURA_INICIAL = 513;
	private final IGestionCompra gestionCompra;
	private final GestionInventario gestionInventario;
	private final GestionContabilidad gestionContabilidad;
	private List<Compra> compras;

	public GestionCompra(IGestionCompra gestionCompra, GestionInventario gestionInventario,
			GestionContabilidad gestionContabilidad) {
		if (gestionCompra == null) {
			throw new IllegalArgumentException("La gestionCompra no puede ser nula.");
		}

		if (gestionInventario == null) {
			throw new IllegalArgumentException("El gestionInventario no puede ser nulo.");
		}
		if (gestionContabilidad == null) {
			throw new IllegalArgumentException("El gestionContabilidad no puede ser nulo.");
		}

		this.gestionCompra = gestionCompra;
		this.gestionInventario = gestionInventario;
		this.gestionContabilidad = gestionContabilidad;
		try {
			compras = gestionCompra.listar();
		} catch (Exception e) {
			compras = new ArrayList<>();
			throw new IllegalStateException("Error al cargar compras.", e);
		}
	}

	public double calcularImpuesto(CategoriaProductoEnum categoria, double subtotal) {
		double tasa;
		if (categoria == null) {
			tasa = 0.19;
		} else {
			switch (categoria) {
			case ALIMENTOS:
				tasa = 0.05;
				break;
			case ASEO:
			case PAPELERIA:
			default:
				tasa = 0.19;
				break;
			}
		}
		return subtotal * tasa;
	}

	public void registrarCompra(Compra compra) throws Exception {
		validarCompra(compra);
		String numeroFactura = compra.getNumeroFacturaProveedor();
		if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
			numeroFactura = generarNumeroFactura();
		}
		if (buscarCompraPorNumero(numeroFactura) != null) {
			throw new Exception("Ya existe una compra con ese numero de factura ");

		}

		compra.setNumeroFacturaProveedor(numeroFactura);
		if (compra.getFecha() == null) {
			compra.setFecha(LocalDate.now());
		}
		compra.setEstado(EstadoCompraEnum.ACTIVA);

		if (compra.getFormaPago() == null) {
			throw new Exception("La forma de pago es obligatoria.");
		}

		compra.setSubtotal(calcularSubtotal(compra.getDetalles()));
		compra.setImpuestos(calcularimpuestos(compra.getDetalles()));
		compra.setTotalCompra(compra.getSubtotal() + compra.getImpuestos());

		TransaccionBD.ejecutar(conexion -> {
			gestionCompra.guardar(conexion, compra);
			registrarEntradaInventario(conexion, compra);
			gestionContabilidad.registrarEgresoPorCompra(conexion, compra);
		});

		compras.add(compra);
	}

	public List<Compra> obtenerCompras() {
		return new ArrayList<>(compras);
	}

	public Compra buscarCompraPorNumero(String numeroFactura) throws Exception {
		if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
			return null;
		}

		for (Compra compra : compras) {
			if (compra.getNumeroFacturaProveedor() != null
					&& compra.getNumeroFacturaProveedor().equalsIgnoreCase(numeroFactura)) {
				return compra;
			}
		}

		try {
			return gestionCompra.buscar(numeroFactura);
		} catch (Exception e) {
			throw new Exception("Error al buscar la compra por numero de factura: " + numeroFactura, e);
		}
	}

	public String generarNumeroFactura() {
		int siguienteNumero = CONSECUTIVO_FACTURA_INICIAL;

		for (Compra compra : compras) {
			String numeroFactura = compra.getNumeroFacturaProveedor();

			if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
				continue;
			}

			Integer consecutivo = obtenerConsecutivoFacturaCompra(numeroFactura);
			if (consecutivo != null && consecutivo >= siguienteNumero) {
				siguienteNumero = consecutivo + 1;
			}
		}

		return PREFIJO_FACTURA_COMPRA + String.format("%0" + DIGITOS_FACTURA_COMPRA + "d", siguienteNumero);
	}

	private Integer obtenerConsecutivoFacturaCompra(String numeroFactura) {
		String numeroNormalizado = numeroFactura.trim().toUpperCase();
		if (!numeroNormalizado.startsWith(PREFIJO_FACTURA_COMPRA)) {
			return null;
		}

		String consecutivo = numeroNormalizado.substring(PREFIJO_FACTURA_COMPRA.length());
		if (!consecutivo.matches("\\d+")) {
			return null;
		}

		return Integer.parseInt(consecutivo);
	}

	private double calcularimpuestos(List<DetalleCompra> detalles) {
		double impuestos = 0.0;
		for (DetalleCompra detalle : detalles) {
			impuestos += detalle.getImpuestos();
		}
		return impuestos;
	}

	private double calcularSubtotal(List<DetalleCompra> detalles) {
		double subtotal = 0.0;
		for (DetalleCompra detalle : detalles) {
			subtotal += detalle.getSubtotal();
		}
		return subtotal;
	}

	public void anularCompra(String numeroFactura, String motivoAnulacion) throws Exception {
		Compra compra = buscarCompraPorNumero(numeroFactura);

		if (compra == null) {
			throw new Exception("No se encotro la compra a anular");
		}

		if (compra.getEstado() == EstadoCompraEnum.ANULADA) {
			throw new Exception("La compra ya esta anulada.");
		}

		if (motivoAnulacion == null || motivoAnulacion.trim().isEmpty()) {
			throw new Exception("Debe ingresar un motivo de anulacion.");
		}

		String motivo = motivoAnulacion.trim();
		EstadoCompraEnum estadoAnterior = compra.getEstado();
		String motivoAnterior = compra.getMotivoAnulacion();

		try {
			TransaccionBD.ejecutar(conexion -> {
				validarStockParaAnulacion(conexion, compra);
				revertirEntradaInventario(conexion, compra);
				compra.setEstado(EstadoCompraEnum.ANULADA);
				compra.setMotivoAnulacion(motivo);
				gestionCompra.actualizar(conexion, compra);
				gestionContabilidad.registrarReversoPorAnulacionCompra(conexion, compra, motivo);
			});
		} catch (Exception e) {
			compra.setEstado(estadoAnterior);
			compra.setMotivoAnulacion(motivoAnterior);
			throw e;
		}

		for (int i = 0; i < compras.size(); i++) {
			Compra c = compras.get(i);
			if (c.getNumeroFacturaProveedor() != null
					&& c.getNumeroFacturaProveedor().equalsIgnoreCase(numeroFactura)) {
				compras.set(i, compra);
				break;
			}
		}
	}

	private void registrarEntradaInventario(Connection conexion, Compra compra) throws Exception {
		for (DetalleCompra detalle : compra.getDetalles()) {
			gestionInventario.registrarEntrada(conexion, detalle.getProducto().getCodigoProducto(),
					detalle.getCantidad(), "Entrada por compra " + compra.getNumeroFacturaProveedor());
		}
	}

	private void revertirEntradaInventario(Connection conexion, Compra compra) throws Exception {
		for (DetalleCompra detalle : compra.getDetalles()) {
			gestionInventario.registrarSalida(conexion, detalle.getProducto().getCodigoProducto(),
					detalle.getCantidad(), "Reversion de compra " + compra.getNumeroFacturaProveedor());
		}
	}

	private void validarStockParaAnulacion(Connection conexion, Compra compra) throws Exception {
		try {
			gestionInventario.validarStockDisponible(conexion, convertirDetallesCompraAVenta(compra));
		} catch (Exception e) {
			throw new Exception(
					"No se puede anular la compra porque el inventario disponible no alcanza para revertirla. "
							+ e.getMessage(),
					e);
		}
	}

	private List<DetalleVenta> convertirDetallesCompraAVenta(Compra compra) {
		Map<String, DetalleVenta> detallesPorProducto = new LinkedHashMap<>();

		for (DetalleCompra detalleCompra : compra.getDetalles()) {
			String codigoProducto = detalleCompra.getProducto().getCodigoProducto();
			DetalleVenta detalleVenta = detallesPorProducto.get(codigoProducto);

			if (detalleVenta == null) {
				detalleVenta = new DetalleVenta(detalleCompra.getProducto(), detalleCompra.getCantidad(),
						detalleCompra.getCostoUnitario(), detalleCompra.getSubtotal());
				detallesPorProducto.put(codigoProducto, detalleVenta);
			} else {
				detalleVenta.setCantidad(detalleVenta.getCantidad() + detalleCompra.getCantidad());
				detalleVenta.setSubtotal(detalleVenta.getSubtotal() + detalleCompra.getSubtotal());
			}
		}

		return new ArrayList<>(detallesPorProducto.values());
	}

	private void validarCompra(Compra compra) throws Exception {
		if (compra == null) {
			throw new Exception("La compra no puede ser nula.");
		}
		if (compra.getCodigoProveedor() == null || compra.getCodigoProveedor().trim().isEmpty()) {
			throw new Exception("El código del proveedor es obligatorio.");
		}

		if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
			throw new Exception("La compra debe contener al menos un producto.");
		}

		for (DetalleCompra detalle : compra.getDetalles()) {
			if (detalle == null) {
				throw new Exception("El detalle de compra no puede contener elementos nulos.");
			}

			if (detalle.getProducto() == null) {
				throw new Exception("Cada detalle debe tener un producto.");
			}

			if (detalle.getCantidad() <= 0) {
				throw new Exception("La cantidad debe ser mayor que cero.");
			}

			if (detalle.getCostoUnitario() < 0) {
				throw new Exception("El costo unitario no puede ser negativo.");
			}

			if (detalle.getSubtotal() < 0) {
				throw new Exception("El subtotal no puede ser negativo.");
			}

			if (detalle.getImpuestos() < 0) {
				throw new Exception("Los impuestos no pueden ser negativos.");
			}

			if (detalle.getTotalCompra() < 0) {
				throw new Exception("El total del detalle no puede ser negativo.");
			}
		}

		String numeroFactura = compra.getNumeroFacturaProveedor();
		if (numeroFactura != null && !numeroFactura.trim().isEmpty()
				&& !numeroFactura.trim().matches("(?i)(\\d+|FC-\\d{5})")) {
			throw new Exception("El número de factura debe ser numérico o tener formato FC-00000.");
		}
	}
}
