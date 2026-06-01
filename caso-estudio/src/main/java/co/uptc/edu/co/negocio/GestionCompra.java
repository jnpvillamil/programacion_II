package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.CompraDAO;
import co.uptc.edu.co.interfaces.IGestionContabilidad;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.enums.EstadoCompraEnum;
import co.uptc.edu.co.modelo.enums.CategoriaProductoEnum;

public class GestionCompra implements IGestionCompra {
	private static final long NUMERO_FACTURA_INICIAL = 708507L;
	private final CompraDAO compraDAO;
	private final IGestionInventario gestionInventario;
	private final IGestionContabilidad gestionContabilidad;
	private List<Compra> compras;

	public GestionCompra(CompraDAO compraDAO, IGestionInventario gestionInventario,
			IGestionContabilidad gestionContabilidad) {
		if (compraDAO == null) {
			throw new IllegalArgumentException("El compraDAO no puede ser nulo.");
		}

		if (gestionInventario == null) {
			throw new IllegalArgumentException("El gestionInventario no puede ser nulo.");
		}
		if (gestionContabilidad == null) {
			throw new IllegalArgumentException("El gestionContabilidad no puede ser nulo.");
		}

		this.compraDAO = compraDAO;
		this.gestionInventario = gestionInventario;
		this.gestionContabilidad = gestionContabilidad;
		try {
			compras = compraDAO.listarCompra();
		} catch (Exception e) {
			compras = new ArrayList<>();
			System.out.println("Error al carga compras:" + e.getMessage());
		}
	}

	@Override
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

	@Override
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
		compra.setFecha(LocalDate.now());
		compra.setEstado(EstadoCompraEnum.ACTIVA);

		if (compra.getFormaPago() == null) {
		    throw new Exception("La forma de pago es obligatoria.");
		}

		compra.setSubtotal(calcularSubtotal(compra.getDetalles()));
		compra.setImpuestos(calcularimpuestos(compra.getDetalles()));
		compra.setTotalCompra(compra.getSubtotal() + compra.getImpuestos());

		
		registrarEntradaInventario(compra);
		try {
			compraDAO.guardarComprar(compra);
			gestionContabilidad.registrarEgresoPorCompra(compra);
			compras.add(compra);
		} catch (Exception e) {
			try {
				compraDAO.eliminarCompra(compra.getNumeroFacturaProveedor());
			} catch (Exception ignored) {
			}
			revertirEntradaInventario(compra);
			throw e;
		}
	}

	@Override
	public List<Compra> obtenerCompras() {
		return new ArrayList<>(compras);
	}

	@Override
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
			return compraDAO.buscarComprarpornumero(numeroFactura);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String generarNumeroFactura() {
		long siguienteNumero = NUMERO_FACTURA_INICIAL;

		for (Compra compra : compras) {
			String numeroFactura = compra.getNumeroFacturaProveedor();

			if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
				continue;
			}

			try {
				long numeroActual = Long.parseLong(numeroFactura.trim());
				if (numeroActual >= siguienteNumero) {
					siguienteNumero = numeroActual + 1;
				}
			} catch (NumberFormatException e) {

			}
		}

		return String.valueOf(siguienteNumero);
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

	@Override
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

		revertirEntradaInventario(compra);
		compra.setEstado(EstadoCompraEnum.ANULADA);
		compra.setMotivoAnulacion(motivoAnulacion.trim());
		try {
			compraDAO.actualizarCompra(compra);
			gestionContabilidad.registrarReversoPorAnulacionCompra(compra, motivoAnulacion.trim());
		} catch (Exception e) {
			compra.setEstado(EstadoCompraEnum.ACTIVA);
			compra.setMotivoAnulacion(null);
			registrarEntradaInventario(compra);
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

	private void registrarEntradaInventario(Compra compra) throws Exception {
		for (DetalleCompra detalle : compra.getDetalles()) {
			gestionInventario.registrarEntrada(detalle.getProducto().getCodigoProducto(), detalle.getCantidad(),
					"Entrada por compra " + compra.getNumeroFacturaProveedor());
		}
	}

	private void revertirEntradaInventario(Compra compra) throws Exception {
		for (DetalleCompra detalle : compra.getDetalles()) {
			gestionInventario.registrarSalida(detalle.getProducto().getCodigoProducto(), detalle.getCantidad(),
					"Reversion de compra " + compra.getNumeroFacturaProveedor());
		}
	}

	private void validarCompra(Compra compra) throws Exception {
		if (compra == null) {
			throw new Exception("El codigo del proveedor es obligatorio.");
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
		if (numeroFactura != null && !numeroFactura.trim().isEmpty() && !numeroFactura.trim().matches("\\d+")) {
			throw new Exception("El número de factura debe contener solo dígitos.");
		}
	}
}
