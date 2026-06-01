package co.edu.uptc.sistienda.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uptc.sistienda.compras.modelo.Compra;
import co.edu.uptc.sistienda.compras.modelo.DetalleCompra;
import co.edu.uptc.sistienda.contabilidad.modelo.MovimientoContable;
import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.Venta;

public class GestionReportes {

	public double calcularTotalVentas(List<Venta> ventas, LocalDate fechaInicio, LocalDate fechaFin) {
		double total = 0;
		for (Venta venta : ventas) {
			if (!venta.isAnulada()
					&& estaFechaDentroDelPeriodo(venta.getFechaHora().toLocalDate(), fechaInicio, fechaFin)) {
				total += venta.getTotal();
			}
		}
		return total;
	}

	public double calcularTotalCompras(List<Compra> compras, LocalDate fechaInicio, LocalDate fechaFin) {
		double total = 0;
		for (Compra compra : compras) {
			if (!compra.isAnulada() && estaFechaDentroDelPeriodo(compra.getFechaCompra(), fechaInicio, fechaFin)) {
				total += compra.getTotalCompra();
			}
		}
		return total;
	}

	public double calcularUtilidadBruta(List<Venta> ventas, LocalDate fechaInicio, LocalDate fechaFin) {
		double utilidad = 0;
		for (Venta venta : ventas) {
			if (venta.isAnulada()
					|| !estaFechaDentroDelPeriodo(venta.getFechaHora().toLocalDate(), fechaInicio, fechaFin)) {
				continue;
			}
			for (DetalleVenta detalle : venta.getItems()) {
				Producto producto = detalle.getProducto();
				double costo = producto != null ? producto.getPrecioCompra() * detalle.getCantidad() : 0;
				utilidad += detalle.getSubtotal() - costo;
			}
		}
		return utilidad;
	}

	public String generarReporteProductosMasVendidos(List<Venta> ventas) {
		Map<String, Integer> cantidadesPorProducto = new HashMap<>();
		for (Venta venta : ventas) {
			if (venta.isAnulada()) {
				continue;
			}
			for (DetalleVenta detalle : venta.getItems()) {
				String nombre = detalle.getProducto().getNombreProducto();
				cantidadesPorProducto.put(nombre,
						cantidadesPorProducto.getOrDefault(nombre, 0) + detalle.getCantidad());
			}
		}
		return convertirMapaAReporte(cantidadesPorProducto, "Productos mas vendidos");
	}

	public String generarReporteClientesConMayorCompra(List<Venta> ventas) {
		Map<String, Double> ventasPorCliente = new HashMap<>();
		for (Venta venta : ventas) {
			if (venta.isAnulada()) {
				continue;
			}
			Cliente cliente = venta.getCliente();
			String nombre = cliente != null ? cliente.getNombreCompletoORazonSocial() : "Cliente sin nombre";
			ventasPorCliente.put(nombre, ventasPorCliente.getOrDefault(nombre, 0.0) + venta.getTotal());
		}
		return convertirMapaDecimalAReporte(ventasPorCliente, "Clientes con mayor volumen de compra");
	}

	public String generarReporteVentasPorFormaPago(List<Venta> ventas) {
		Map<String, Double> ventasPorFormaPago = new HashMap<>();
		for (Venta venta : ventas) {
			if (venta.isAnulada()) {
				continue;
			}
			String formaPago = venta.getFormaPago() != null ? venta.getFormaPago().getDescripcion()
					: "Sin forma de pago";
			ventasPorFormaPago.put(formaPago, ventasPorFormaPago.getOrDefault(formaPago, 0.0) + venta.getTotal());
		}
		return convertirMapaDecimalAReporte(ventasPorFormaPago, "Ventas por forma de pago");
	}

	public double calcularInventarioValorizado(List<Producto> productos) {
		double total = 0;
		for (Producto producto : productos) {
			total += producto.getStockActual() * producto.getPrecioCompra();
		}
		return total;
	}

	public String generarResumenContable(List<MovimientoContable> movimientos, LocalDate fechaInicio,
			LocalDate fechaFin) {
		double debitos = 0;
		double creditos = 0;
		for (MovimientoContable movimiento : movimientos) {
			if (!movimiento.isAnulado()
					&& estaFechaDentroDelPeriodo(movimiento.getFechaMovimiento(), fechaInicio, fechaFin)) {
				debitos += movimiento.getTotalDebito();
				creditos += movimiento.getTotalCredito();
			}
		}
		return "Resumen contable\n" + "Debitos: $" + String.format("%,.0f", debitos) + "\n" + "Creditos: $"
				+ String.format("%,.0f", creditos) + "\n" + "Diferencia: $"
				+ String.format("%,.0f", debitos - creditos);
	}

	public List<Compra> consultarComprasPorProveedorYFechas(List<Compra> compras, String codigoProveedor,
			LocalDate fechaInicio, LocalDate fechaFin) {
		List<Compra> resultado = new ArrayList<>();
		for (Compra compra : compras) {
			boolean coincideProveedor = codigoProveedor == null || codigoProveedor.trim().isEmpty()
					|| (compra.getProveedor() != null
							&& compra.getProveedor().getCodigoProveedor().equalsIgnoreCase(codigoProveedor));
			if (coincideProveedor && estaFechaDentroDelPeriodo(compra.getFechaCompra(), fechaInicio, fechaFin)) {
				resultado.add(compra);
			}
		}
		return resultado;
	}

	private boolean estaFechaDentroDelPeriodo(LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFin) {
		if (fecha == null) {
			return false;
		}
		return (fechaInicio == null || !fecha.isBefore(fechaInicio)) && (fechaFin == null || !fecha.isAfter(fechaFin));
	}

	private String convertirMapaAReporte(Map<String, Integer> datos, String titulo) {
		StringBuilder reporte = new StringBuilder(titulo).append("\n");
		datos.entrySet().stream().sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
				.forEach(e -> reporte.append(e.getKey()).append(": ").append(e.getValue()).append(" unidades\n"));
		return reporte.toString();
	}

	private String convertirMapaDecimalAReporte(Map<String, Double> datos, String titulo) {
		StringBuilder reporte = new StringBuilder(titulo).append("\n");
		datos.entrySet().stream().sorted((a, b) -> Double.compare(b.getValue(), a.getValue())).forEach(e -> reporte
				.append(e.getKey()).append(": $").append(String.format("%,.0f", e.getValue())).append("\n"));
		return reporte.toString();
	}
}
