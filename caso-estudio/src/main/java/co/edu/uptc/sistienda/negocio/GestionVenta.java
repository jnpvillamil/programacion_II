package co.edu.uptc.sistienda.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.uptc.sistienda.interfaces.IGestionVenta;
import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Venta;

public class GestionVenta {

	private IGestionVenta repositorioVenta;
	private GestionProducto gestionProducto;
	private int correlativoFactura = 1;

	public GestionVenta(IGestionVenta repositorioVenta, GestionProducto gestionProducto) {
		this.repositorioVenta = repositorioVenta;
		this.gestionProducto = gestionProducto;
	}

	public String generarNumeroFactura() {
		return String.format("FV-%05d", correlativoFactura++);
	}

	public void resgistrarVenta(Venta venta) throws Exception {
		if (venta.getCliente() == null) {
			throw new Exception("Debe selecionar un cliente para la venta");
		}
		if (venta.getItems() == null || venta.getItems().isEmpty()) {
			throw new Exception("La venta debe tener al menos un producto");
		}
		if (venta.getFormaPago() == null) {
			throw new Exception("Debe seleccionar una forma de pago");
		}

		for (DetalleVenta item : venta.getItems()) {
			int stock = item.getProducto().getStockActual();
			if (item.getCantidad() > stock) {
				throw new Exception("Stock insuficiente para " + item.getProducto().getNombreProducto() + "Disponible: "
						+ stock + ", solicitado: " + item.getCantidad());
			}
		}

		for (DetalleVenta item : venta.getItems()) {
			item.getProducto().setStockActual(item.getProducto().getStockActual() - item.getCantidad());
			gestionProducto.modificarProducto(item.getProducto());
		}
		repositorioVenta.guardarVenta(venta);
	}

	public void anularVenta(String numeroFactura) throws Exception {
		Venta venta = repositorioVenta.buscarVentaPorNumeroFactura(numeroFactura);
		if (venta == null) {
			throw new Exception("No se encontró la venta: " + numeroFactura);
		}
		if (venta.isAnulada()) {
			throw new Exception("La venta ya está anulada: ");
		}
		for (DetalleVenta item : venta.getItems()) {
			item.getProducto().setStockActual(item.getProducto().getStockActual() + item.getCantidad());
			gestionProducto.modificarProducto(item.getProducto());
		}
		repositorioVenta.anularVenta(numeroFactura);
	}

	public Venta consultarVentaPorFactura(String numeroFactura) {
		return repositorioVenta.buscarVentaPorNumeroFactura(numeroFactura);
	}

	public List<Venta> obtenerListaVentas() {
		return repositorioVenta.obtenerListaVentas();
	}

	public List<Venta> consultarVentasPorFecha(LocalDate fecha) {
		return repositorioVenta.obtenerListaVentas().stream().filter(v -> v.getFechaHora().toLocalDate().equals(fecha))
				.collect(Collectors.toList());
	}

}
