package co.uptc.edu.co.negocio;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import co.uptc.edu.co.interfaces.IGestionReporte;
import co.uptc.edu.co.interfaces.VentaDAO;
import co.uptc.edu.co.modelo.DetalleVenta;
import co.uptc.edu.co.modelo.ResumenProductoDTO;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.enums.EstadoVentaEnum;

public class GestionReporte implements IGestionReporte {

	private static final String CARPETA_REPORTES = "reportes";
	private static final DateTimeFormatter FORMATO_ARCHIVO = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

	private final VentaDAO ventaDAO;

	public GestionReporte(VentaDAO ventaDAO) {
		if (ventaDAO == null) {
			throw new IllegalArgumentException("La ventaDAO no puede ser nula.");
		}

		this.ventaDAO = ventaDAO;
	}

	@Override
	public String generarReporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
		List<Venta> ventasActuales = ventaDAO.listarVentas();
		List<ResumenProducto> resumenProductos = construirResumenProductos(ventasActuales, fechaInicio, fechaFin);
		return guardarReporteProductosMasVendidos(resumenProductos, fechaInicio, fechaFin);
	}

	@Override
	public List<ResumenProductoDTO> obtenerResumenProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		List<Venta> ventasActuales = ventaDAO.listarVentas();
		List<ResumenProducto> resumenes = construirResumenProductos(ventasActuales, fechaInicio, fechaFin);
		List<ResumenProductoDTO> dtos = new ArrayList<>();

		for (ResumenProducto resumen : resumenes) {
			dtos.add(new ResumenProductoDTO(resumen.getCodigoProducto(), resumen.getNombreProducto(),
					resumen.getCantidadVendida()));
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

		File carpeta = new File(CARPETA_REPORTES);
		if (!carpeta.exists() && !carpeta.mkdirs()) {
			throw new Exception("No se pudo crear la carpeta de reportes.");
		}

		String nombreArchivo = "producto_" + encontrado.getCodigoProducto() + "_"
				+ LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json";
		File archivo = new File(carpeta, nombreArchivo);

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "producto_mas_vendido");
		raiz.addProperty("generado_en", LocalDateTime.now().toString());
		raiz.addProperty("fecha_inicio", fechaInicio != null ? fechaInicio.toString() : null);
		raiz.addProperty("fecha_fin", fechaFin != null ? fechaFin.toString() : null);
		raiz.addProperty("codigo_producto", encontrado.getCodigoProducto());
		raiz.addProperty("nombre_producto", encontrado.getNombreProducto());
		raiz.addProperty("cantidad_vendida", encontrado.getCantidadVendida());

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
			writer.print(gson.toJson(raiz));
		}

		return archivo.getPath();
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
				resumen.sumarCantidad(detalle.getCantidad());
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

	private String guardarReporteProductosMasVendidos(List<ResumenProducto> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception {
		File carpeta = new File(CARPETA_REPORTES);
		if (!carpeta.exists() && !carpeta.mkdirs()) {
			throw new Exception("No se pudo crear la carpeta de reportes.");
		}

		String nombreArchivo = "productos_mas_vendidos_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json";
		File archivo = new File(carpeta, nombreArchivo);

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "productos_mas_vendidos");
		raiz.addProperty("generado_en", LocalDateTime.now().toString());
		raiz.addProperty("fecha_inicio", fechaInicio != null ? fechaInicio.toString() : null);
		raiz.addProperty("fecha_fin", fechaFin != null ? fechaFin.toString() : null);
		raiz.addProperty("total_productos", resumenes.size());

		JsonArray productos = new JsonArray();
		for (ResumenProducto resumen : resumenes) {
			JsonObject item = new JsonObject();
			item.addProperty("codigo_producto", resumen.getCodigoProducto());
			item.addProperty("nombre_producto", resumen.getNombreProducto());
			item.addProperty("cantidad_vendida", resumen.getCantidadVendida());
			productos.add(item);
		}
		raiz.add("productos", productos);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
			writer.print(gson.toJson(raiz));
		}

		return archivo.getPath();
	}

	private static final class ResumenProducto {
		private final String codigoProducto;
		private final String nombreProducto;
		private int cantidadVendida;

		private ResumenProducto(String codigoProducto, String nombreProducto) {
			this.codigoProducto = codigoProducto;
			this.nombreProducto = nombreProducto;
		}

		private void sumarCantidad(int cantidad) {
			this.cantidadVendida += cantidad;
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
	}
}
