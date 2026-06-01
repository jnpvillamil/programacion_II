package co.edu.uptc.sistienda.negocio;

import java.util.List;

import co.edu.uptc.sistienda.compras.modelo.Compra;
import co.edu.uptc.sistienda.compras.modelo.DetalleCompra;
import co.edu.uptc.sistienda.interfaces.IGestionCompra;
import co.edu.uptc.sistienda.modelo.Producto;

public class GestionCompra {

	private IGestionCompra repositorioCompra;
	private GestionProducto gestionProducto;
	private GestionContabilidad gestionContabilidad;
	private int consecutivoCompra = 1;

	public GestionCompra(IGestionCompra repositorioCompra, GestionProducto gestionProducto) {
		this(repositorioCompra, gestionProducto, null);
	}

	public GestionCompra(IGestionCompra repositorioCompra, GestionProducto gestionProducto,
			GestionContabilidad gestionContabilidad) {
		this.repositorioCompra = repositorioCompra;
		this.gestionProducto = gestionProducto;
		this.gestionContabilidad = gestionContabilidad;
		this.consecutivoCompra = calcularSiguienteConsecutivo();
	}

	public String generarNumeroCompra() {
		return String.format("FC-%05d", consecutivoCompra++);
	}

	public void registrarCompra(Compra compra) throws Exception {
		validarCompra(compra);

		for (DetalleCompra detalle : compra.getDetalles()) {
			Producto productoComprado = detalle.getProducto();
			productoComprado.setStockActual(productoComprado.getStockActual() + detalle.getCantidad());
			productoComprado.setPrecioCompra(detalle.getPrecioCompra());
			gestionProducto.modificarProducto(productoComprado);
		}

		compra.calcularTotalCompra();
		repositorioCompra.guardarCompra(compra);
		if (gestionContabilidad != null) {
			gestionContabilidad.registrarMovimientoPorCompra(compra);
		}
	}

	public void anularCompra(String numeroCompra) throws Exception {
		Compra compra = repositorioCompra.buscarCompra(numeroCompra);
		if (compra == null) {
			throw new Exception("No se encontro la compra: " + numeroCompra);
		}
		if (compra.isAnulada()) {
			throw new Exception("La compra ya esta anulada.");
		}

		for (DetalleCompra detalle : compra.getDetalles()) {
			Producto productoComprado = gestionProducto
					.consultarProductoPorCodigo(detalle.getProducto().getCodigoInterno());
			if (productoComprado == null) {
				throw new Exception("No se encontro el producto: " + detalle.getProducto().getCodigoInterno());
			}
			if (productoComprado.getStockActual() < detalle.getCantidad()) {
				throw new Exception("No se puede anular la compra porque el producto "
						+ productoComprado.getNombreProducto() + " no tiene stock suficiente para descontar.");
			}
			productoComprado.setStockActual(productoComprado.getStockActual() - detalle.getCantidad());
			gestionProducto.modificarProducto(productoComprado);
		}

		repositorioCompra.anularCompra(numeroCompra);
		if (gestionContabilidad != null) {
			gestionContabilidad.anularMovimientoPorDocumento(numeroCompra);
		}
	}

	public Compra consultarCompraPorNumero(String numeroCompra) {
		return repositorioCompra.buscarCompra(numeroCompra);
	}

	public List<Compra> obtenerListaCompras() {
		return repositorioCompra.obtenerListaCompras();
	}

	public List<Compra> consultarComprasPorProveedorYFechas(String codigoProveedor, java.time.LocalDate fechaInicio,
			java.time.LocalDate fechaFin) {
		List<Compra> resultado = new java.util.ArrayList<>();
		for (Compra compra : repositorioCompra.obtenerListaCompras()) {
			boolean coincideProveedor = codigoProveedor == null || codigoProveedor.trim().isEmpty()
					|| (compra.getProveedor() != null
							&& compra.getProveedor().getCodigoProveedor().equalsIgnoreCase(codigoProveedor));
			boolean coincideFecha = compra.getFechaCompra() != null
					&& (fechaInicio == null || !compra.getFechaCompra().isBefore(fechaInicio))
					&& (fechaFin == null || !compra.getFechaCompra().isAfter(fechaFin));
			if (coincideProveedor && coincideFecha) {
				resultado.add(compra);
			}
		}
		return resultado;
	}

	private void validarCompra(Compra compra) throws Exception {
		if (compra == null) {
			throw new Exception("La compra no puede estar vacia.");
		}
		if (compra.getNumeroCompra() == null || compra.getNumeroCompra().trim().isEmpty()) {
			throw new Exception("La compra debe tener un numero.");
		}
		if (compra.getProveedor() == null) {
			throw new Exception("Debe seleccionar un proveedor.");
		}
		if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
			throw new Exception("Debe agregar al menos un producto a la compra.");
		}

		for (DetalleCompra detalle : compra.getDetalles()) {
			if (detalle.getProducto() == null) {
				throw new Exception("Cada detalle debe tener un producto.");
			}
			if (detalle.getCantidad() <= 0) {
				throw new Exception("La cantidad comprada debe ser mayor a cero.");
			}
			if (detalle.getPrecioCompra() <= 0) {
				throw new Exception("El precio de compra debe ser mayor a cero.");
			}
		}
	}

	private int calcularSiguienteConsecutivo() {
		int mayor = 0;
		for (Compra compra : repositorioCompra.obtenerListaCompras()) {
			String numero = compra.getNumeroCompra();
			if (numero != null && numero.startsWith("FC-")) {
				try {
					mayor = Math.max(mayor, Integer.parseInt(numero.substring(3)));
				} catch (NumberFormatException ignored) {
					// Si hay un numero manual con otro formato, simplemente no afecta el
					// consecutivo.
				}
			}
		}
		return mayor + 1;
	}
}
