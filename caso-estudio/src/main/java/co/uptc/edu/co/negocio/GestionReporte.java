package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.uptc.edu.co.interfaces.IGestionReporte;
import co.uptc.edu.co.interfaces.dao.ReporteDAO;
import co.uptc.edu.co.interfaces.dao.VentaDAO;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;
import co.uptc.edu.co.modelo.enums.FormaPago;
import co.uptc.edu.co.modelo.dto.DetalleUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.dto.ResumenUtilidadBrutaDTO;
import co.uptc.edu.co.modelo.dto.ResumenVentasDTO;

public class GestionReporte implements IGestionReporte {

	private final VentaDAO ventaDAO;
	private final ReporteDAO reporteDAO;

	public GestionReporte(VentaDAO ventaDAO, ReporteDAO reporteDAO) {
		if (ventaDAO == null) {
			throw new IllegalArgumentException("La ventaDAO no puede ser nula.");
		}
		if (reporteDAO == null) {
			throw new IllegalArgumentException("El reporteDAO no puede ser nulo.");
		}

		this.ventaDAO = ventaDAO;
		this.reporteDAO = reporteDAO;
	}

	@Override
	public String generarReporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
		return reporteDAO.guardarReporteProductosMasVendidos(
				obtenerResumenProductosMasVendidos(fechaInicio, fechaFin), fechaInicio, fechaFin);
	}

	@Override
	public List<ResumenProductoDTO> obtenerResumenProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		List<Venta> ventasActuales = ventaDAO.listarVentas();
		List<ResumenProducto> resumenes = construirResumenProductos(ventasActuales, fechaInicio, fechaFin);
		List<ResumenProductoDTO> dtos = new ArrayList<>();

		for (ResumenProducto resumen : resumenes) {
			dtos.add(new ResumenProductoDTO(resumen.getCodigoProducto(), resumen.getNombreProducto(),
					resumen.getCantidadVendida(), resumen.getTotalVendido()));
		}

		return dtos;
	}

	@Override
	public String generarReporteProducto(String codigoProducto, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		if (codigoProducto == null || codigoProducto.isBlank()) {
			throw new Exception("Debe seleccionar un codigo de producto valido para generar el reporte.");
		}

		List<Venta> ventasActuales = ventaDAO.listarVentas();
		List<ResumenProducto> resumenes = construirResumenProductos(ventasActuales, fechaInicio, fechaFin);
		ResumenProducto encontrado = null;

		for (ResumenProducto resumen : resumenes) {
			if (resumen.getCodigoProducto() != null && resumen.getCodigoProducto().equalsIgnoreCase(codigoProducto)) {
				encontrado = resumen;
				break;
			}
		}

		if (encontrado == null) {
			throw new Exception("No se encontro datos de ventas para el producto seleccionado dentro del rango.");
		}

		ResumenProductoDTO dto = new ResumenProductoDTO(encontrado.getCodigoProducto(), encontrado.getNombreProducto(),
				encontrado.getCantidadVendida(), encontrado.getTotalVendido());
		return reporteDAO.guardarReporteProducto(dto, fechaInicio, fechaFin);
	}

	@Override
	public List<ResumenFormaPagoDTO> obtenerVentasPorFormaPago(LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		List<Venta> ventasActuales = ventaDAO.listarVentas();
		Map<FormaPago, ResumenFormaPago> resumenPorFormaPago = new LinkedHashMap<>();

		for (Venta venta : ventasActuales) {
			if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin) || venta.getFormaPago() == null) {
				continue;
			}

			ResumenFormaPago resumen = resumenPorFormaPago.computeIfAbsent(venta.getFormaPago(),
					formaPago -> new ResumenFormaPago(formaPago));
			resumen.acumularVenta(venta.getTotal());
		}

		List<ResumenFormaPagoDTO> resumenes = new ArrayList<>();
		for (ResumenFormaPago resumen : resumenPorFormaPago.values()) {
			resumenes.add(new ResumenFormaPagoDTO(resumen.formaPago, resumen.cantidadVentas, resumen.valorTotal));
		}
		resumenes.sort(Comparator.comparingDouble(ResumenFormaPagoDTO::getValorTotal).reversed());
		return resumenes;
	}

	@Override
	public ResumenVentasDTO obtenerTotalVentasDiarias(LocalDate fecha) throws Exception {
		if (fecha == null) {
			throw new Exception("La fecha es obligatoria.");
		}

		return construirResumenVentas(fecha.toString(), fecha, fecha);
	}

	@Override
	public ResumenVentasDTO obtenerTotalVentasMensuales(int mes, int anio) throws Exception {
		if (mes < 1 || mes > 12) {
			throw new Exception("El mes debe estar entre 1 y 12.");
		}

		if (anio <= 0) {
			throw new Exception("El aÃ±o debe ser valido.");
		}

		LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
		LocalDate fechaFin = fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());
		String periodo = String.format("%02d/%d", mes, anio);
		return construirResumenVentas(periodo, fechaInicio, fechaFin);
	}

	@Override
	public ResumenVentasDTO obtenerTotalVentasAnuales(int anio) throws Exception {
		if (anio <= 0) {
			throw new Exception("El aÃ±o debe ser valido.");
		}

		LocalDate fechaInicio = LocalDate.of(anio, 1, 1);
		LocalDate fechaFin = LocalDate.of(anio, 12, 31);
		return construirResumenVentas(String.valueOf(anio), fechaInicio, fechaFin);
	}

	@Override
	public ResumenUtilidadBrutaDTO obtenerUtilidadBruta(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
		validarRangoFechas(fechaInicio, fechaFin);

		List<Venta> ventasActuales = ventaDAO.listarVentas();
		Map<String, ResumenUtilidadProducto> resumenPorProducto = new LinkedHashMap<>();
		double totalVentas = 0;
		double costoVentas = 0;
		int cantidadVentas = 0;
		int cantidadVendida = 0;

		for (Venta venta : ventasActuales) {
			if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin)) {
				continue;
			}

			totalVentas += venta.getSubTotal();
			costoVentas += calcularCostoVenta(venta);
			cantidadVentas++;
			cantidadVendida += calcularCantidadVendida(venta);
			acumularUtilidadPorProducto(resumenPorProducto, venta);
		}

		double utilidadBruta = totalVentas - costoVentas;
		return new ResumenUtilidadBrutaDTO(construirPeriodo(fechaInicio, fechaFin),
				construirDetallesUtilidad(resumenPorProducto), totalVentas, costoVentas, utilidadBruta, cantidadVentas,
				cantidadVendida);
	}

	private ResumenVentasDTO construirResumenVentas(String periodo, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		List<Venta> ventasActuales = ventaDAO.listarVentas();
		List<Venta> ventasDelDia = new ArrayList<>();
		double subtotalVentas = 0;
		double totalVentas = 0;
		double impuestos = 0;
		int cantidadVentas = 0;

		for (Venta venta : ventasActuales) {
			if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin)) {
				continue;
			}

			ventasDelDia.add(venta);
			subtotalVentas += venta.getSubTotal();
			totalVentas += venta.getTotal();
			impuestos += venta.getImpuestos();
			cantidadVentas++;
		}

		return new ResumenVentasDTO(periodo, ventasDelDia, subtotalVentas, totalVentas, cantidadVentas, impuestos);
	}

	private List<ResumenProducto> construirResumenProductos(List<Venta> ventasFuente, LocalDate fechaInicio,
			LocalDate fechaFin) {
		Map<String, ResumenProducto> resumenPorProducto = new LinkedHashMap<>();

		for (Venta venta : ventasFuente) {
			if (!debeIncluirVentaEnReporte(venta, fechaInicio, fechaFin) || venta.getDetalles() == null) {
				continue;
			}

			for (DetalleVenta detalle : venta.getDetalles()) {
				if (detalle == null || detalle.getProducto() == null) {
					continue;
				}

				String codigoProducto = detalle.getProducto().getCodigoProducto();
				if (codigoProducto == null || codigoProducto.isBlank()) {
					codigoProducto = "SIN_CODIGO";
				}

				String nombreProducto = detalle.getProducto().getNombreProducto();
				if (nombreProducto == null || nombreProducto.isBlank()) {
					nombreProducto = "Producto sin nombre";
				}

				final String codigoProductoFinal = codigoProducto;
				final String nombreProductoFinal = nombreProducto;
				ResumenProducto resumen = resumenPorProducto.computeIfAbsent(codigoProducto,
						clave -> new ResumenProducto(codigoProductoFinal, nombreProductoFinal));
				resumen.acumular(detalle.getCantidad(), detalle.getSubtotal());
			}
		}

		List<ResumenProducto> resumenes = new ArrayList<>(resumenPorProducto.values());
		resumenes.sort(Comparator.comparingInt(ResumenProducto::getCantidadVendida).reversed()
				.thenComparing(ResumenProducto::getNombreProducto, String.CASE_INSENSITIVE_ORDER));
		return resumenes;
	}

	private boolean debeIncluirVentaEnReporte(Venta venta, LocalDate fechaInicio, LocalDate fechaFin) {
		if (venta == null || venta.getEstado() == EstadoVentaEnum.ANULADA || venta.getFechaHora() == null) {
			return false;
		}

		LocalDate fechaVenta = venta.getFechaHora().toLocalDate();
		if (fechaInicio != null && fechaVenta.isBefore(fechaInicio)) {
			return false;
		}
		if (fechaFin != null && fechaVenta.isAfter(fechaFin)) {
			return false;
		}

		return true;
	}

	private double calcularCostoVenta(Venta venta) {
		double costoVenta = 0;

		if (venta.getDetalles() == null) {
			return costoVenta;
		}

		for (DetalleVenta detalle : venta.getDetalles()) {
			if (detalle == null || detalle.getProducto() == null) {
				continue;
			}

			costoVenta += detalle.getCantidad() * detalle.getProducto().getPrecioCompra();
		}

		return costoVenta;
	}

	private int calcularCantidadVendida(Venta venta) {
		int cantidadVendida = 0;

		if (venta.getDetalles() == null) {
			return cantidadVendida;
		}

		for (DetalleVenta detalle : venta.getDetalles()) {
			if (detalle != null) {
				cantidadVendida += detalle.getCantidad();
			}
		}

		return cantidadVendida;
	}

	private void acumularUtilidadPorProducto(Map<String, ResumenUtilidadProducto> resumenPorProducto, Venta venta) {
		if (venta.getDetalles() == null) {
			return;
		}

		for (DetalleVenta detalle : venta.getDetalles()) {
			if (detalle == null || detalle.getProducto() == null) {
				continue;
			}

			String codigoProducto = detalle.getProducto().getCodigoProducto();
			if (codigoProducto == null || codigoProducto.isBlank()) {
				codigoProducto = "SIN_CODIGO";
			}

			String nombreProducto = detalle.getProducto().getNombreProducto();
			if (nombreProducto == null || nombreProducto.isBlank()) {
				nombreProducto = "Producto sin nombre";
			}

			final String codigoProductoFinal = codigoProducto;
			final String nombreProductoFinal = nombreProducto;
			ResumenUtilidadProducto resumen = resumenPorProducto.computeIfAbsent(codigoProducto,
					clave -> new ResumenUtilidadProducto(codigoProductoFinal, nombreProductoFinal));
			resumen.acumular(detalle.getCantidad(), detalle.getSubtotal(),
					detalle.getCantidad() * detalle.getProducto().getPrecioCompra());
		}
	}

	private List<DetalleUtilidadBrutaDTO> construirDetallesUtilidad(
			Map<String, ResumenUtilidadProducto> resumenPorProducto) {
		List<DetalleUtilidadBrutaDTO> detalles = new ArrayList<>();

		for (ResumenUtilidadProducto resumen : resumenPorProducto.values()) {
			detalles.add(new DetalleUtilidadBrutaDTO(resumen.codigoProducto, resumen.nombreProducto,
					resumen.cantidadVendida, resumen.ventas, resumen.costoVenta,
					resumen.ventas - resumen.costoVenta));
		}

		detalles.sort(Comparator.comparingDouble(DetalleUtilidadBrutaDTO::getUtilidad).reversed()
				.thenComparing(DetalleUtilidadBrutaDTO::getNombreProducto, String.CASE_INSENSITIVE_ORDER));
		return detalles;
	}

	private void validarRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
		if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
			throw new Exception("La fecha de inicio no puede ser posterior a la fecha final.");
		}
	}

	private String construirPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
		if (fechaInicio == null && fechaFin == null) {
			return "Todos";
		}

		if (fechaInicio != null && fechaFin != null) {
			return fechaInicio + " a " + fechaFin;
		}

		if (fechaInicio != null) {
			return "Desde " + fechaInicio;
		}

		return "Hasta " + fechaFin;
	}

	private static final class ResumenProducto {
		private final String codigoProducto;
		private final String nombreProducto;
		private int cantidadVendida;
		private double totalVendido;

		private ResumenProducto(String codigoProducto, String nombreProducto) {
			this.codigoProducto = codigoProducto;
			this.nombreProducto = nombreProducto;
		}

		private void acumular(int cantidad, double valorVendido) {
			this.cantidadVendida += cantidad;
			this.totalVendido += valorVendido;
		}

		private String getCodigoProducto() {
			return codigoProducto;
		}

		private String getNombreProducto() {
			return nombreProducto;
		}

		private int getCantidadVendida() {
			return cantidadVendida;
		}

		private double getTotalVendido() {
			return totalVendido;
		}
	}

	private static final class ResumenUtilidadProducto {
		private final String codigoProducto;
		private final String nombreProducto;
		private int cantidadVendida;
		private double ventas;
		private double costoVenta;

		private ResumenUtilidadProducto(String codigoProducto, String nombreProducto) {
			this.codigoProducto = codigoProducto;
			this.nombreProducto = nombreProducto;
		}

		private void acumular(int cantidad, double venta, double costo) {
			this.cantidadVendida += cantidad;
			this.ventas += venta;
			this.costoVenta += costo;
		}
	}

	private static final class ResumenFormaPago {
		private final FormaPago formaPago;
		private int cantidadVentas;
		private double valorTotal;

		private ResumenFormaPago(FormaPago formaPago) {
			this.formaPago = formaPago;
		}

		private void acumularVenta(double valorVenta) {
			this.cantidadVentas++;
			this.valorTotal += valorVenta;
		}
	}
}
