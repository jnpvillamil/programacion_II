package co.uptc.edu.co.persistencia.archivo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.table.DefaultTableModel;

import co.uptc.edu.co.interfaces.dao.ReporteDAO;
import co.uptc.edu.co.modelo.Venta;
import co.uptc.edu.co.modelo.dto.ResumenInventarioValorizadoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.dto.ResumenVentasDTO;

public class ReporteJSONDAO implements ReporteDAO {

	private static final String CARPETA_REPORTES = "reportes";
	private static final DateTimeFormatter FORMATO_ARCHIVO = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

	@Override
	public String guardarReporteProductosMasVendidos(List<ResumenProductoDTO> resumenes, LocalDate fechaInicio,
			LocalDate fechaFin) throws Exception {
		File archivo = crearArchivo("productos_mas_vendidos_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "productos_mas_vendidos");
		raiz.addProperty("generado_en", LocalDateTime.now().toString());
		raiz.addProperty("fecha_inicio", fechaInicio != null ? fechaInicio.toString() : null);
		raiz.addProperty("fecha_fin", fechaFin != null ? fechaFin.toString() : null);
		raiz.addProperty("total_productos", resumenes != null ? resumenes.size() : 0);

		JsonArray productos = new JsonArray();
		if (resumenes != null) {
			for (ResumenProductoDTO resumen : resumenes) {
				JsonObject item = new JsonObject();
				item.addProperty("codigo_producto", resumen.getCodigoProducto());
				item.addProperty("nombre_producto", resumen.getNombreProducto());
				item.addProperty("cantidad_vendida", resumen.getCantidadVendida());
				item.addProperty("total_vendido", resumen.getTotalVendido());
				productos.add(item);
			}
		}
		raiz.add("productos", productos);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteProducto(ResumenProductoDTO resumen, LocalDate fechaInicio, LocalDate fechaFin)
			throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos del producto para guardar el reporte.");
		}

		String nombreArchivo = "producto_" + resumen.getCodigoProducto() + "_"
				+ LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json";
		File archivo = crearArchivo(nombreArchivo);

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "producto_mas_vendido");
		raiz.addProperty("generado_en", LocalDateTime.now().toString());
		raiz.addProperty("fecha_inicio", fechaInicio != null ? fechaInicio.toString() : null);
		raiz.addProperty("fecha_fin", fechaFin != null ? fechaFin.toString() : null);
		raiz.addProperty("codigo_producto", resumen.getCodigoProducto());
		raiz.addProperty("nombre_producto", resumen.getNombreProducto());
		raiz.addProperty("cantidad_vendida", resumen.getCantidadVendida());
		raiz.addProperty("total_vendido", resumen.getTotalVendido());

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteVentasDiarias(ResumenVentasDTO resumen, LocalDate fecha) throws Exception {
		if (resumen == null) {
			throw new Exception("No hay datos de ventas diarias para guardar el reporte.");
		}

		File archivo = crearArchivo("ventas_diarias_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".json");

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", "ventas_diarias");
		raiz.addProperty("generado_en", LocalDateTime.now().toString());
		raiz.addProperty("fecha_reporte", fecha != null ? fecha.toString() : resumen.getPeriodo());
		raiz.addProperty("cantidad_registros", resumen.getCantidadVentas());
		raiz.addProperty("subtotal_total", resumen.getSubtotalVentas());
		raiz.addProperty("impuestos_total", resumen.getImpuestos());
		raiz.addProperty("total_ventas", resumen.getTotalVentas());

		JsonArray ventas = new JsonArray();
		for (Venta venta : resumen.getVentas()) {
			JsonObject item = new JsonObject();
			item.addProperty("factura", venta.getNumeroFactura());
			item.addProperty("fecha", venta.getFechaHora() != null ? venta.getFechaHora().toLocalDate().toString() : "");
			item.addProperty("cliente", venta.getCliente() != null && !venta.getCliente().isBlank() ? venta.getCliente()
					: "ANONIMO");
			item.addProperty("codigo_cliente", venta.getCodigoCliente());
			item.addProperty("forma_pago", venta.getFormaPago() != null ? venta.getFormaPago().toString() : "");
			item.addProperty("subtotal", venta.getSubTotal());
			item.addProperty("impuestos", venta.getImpuestos());
			item.addProperty("total", venta.getTotal());
			item.addProperty("estado", venta.getEstado() != null ? venta.getEstado().toString() : "");
			ventas.add(item);
		}
		raiz.add("ventas", ventas);

		JsonObject resumenJson = new JsonObject();
		resumenJson.addProperty("factura", "TOTAL");
		resumenJson.addProperty("fecha", fecha != null ? fecha.toString() : resumen.getPeriodo());
		resumenJson.addProperty("cliente", resumen.getCantidadVentas() + " ventas");
		resumenJson.addProperty("forma_pago", "");
		resumenJson.addProperty("subtotal", resumen.getSubtotalVentas());
		resumenJson.addProperty("impuestos", resumen.getImpuestos());
		resumenJson.addProperty("total", resumen.getTotalVentas());
		raiz.add("resumen", resumenJson);

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	@Override
	public String guardarReporteTabla(String tipoReporte, DefaultTableModel modeloTabla) throws Exception {
		if (modeloTabla == null || modeloTabla.getRowCount() == 0) {
			throw new Exception("No hay datos en la tabla para generar el reporte JSON.");
		}

		File archivo = crearArchivo(obtenerPrefijoArchivo(tipoReporte) + "_" + LocalDateTime.now().format(FORMATO_ARCHIVO)
				+ ".json");

		JsonObject raiz = new JsonObject();
		raiz.addProperty("tipo_reporte", normalizarTipoReporte(tipoReporte));
		raiz.addProperty("generado_en", LocalDateTime.now().toString());

		JsonArray columnas = new JsonArray();
		for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
			columnas.add(modeloTabla.getColumnName(i));
		}
		raiz.add("columnas", columnas);

		JsonArray registros = new JsonArray();
		for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
			JsonObject registro = new JsonObject();
			for (int columna = 0; columna < modeloTabla.getColumnCount(); columna++) {
				String nombreColumna = modeloTabla.getColumnName(columna);
				Object valor = modeloTabla.getValueAt(fila, columna);
				registro.addProperty(normalizarClaveJson(nombreColumna), valor != null ? valor.toString() : "");
			}
			registros.add(registro);
		}
		raiz.add("registros", registros);
		raiz.addProperty("cantidad_registros", modeloTabla.getRowCount());

		escribirJson(archivo, raiz);
		return archivo.getPath();
	}

	private String obtenerPrefijoArchivo(String tipoReporte) {
		String normalizado = normalizarTipoReporte(tipoReporte);
		if (normalizado.isBlank()) {
			return "reporte";
		}
		return normalizado;
	}

	private String normalizarTipoReporte(String tipoReporte) {
		if (tipoReporte == null || tipoReporte.isBlank()) {
			return "reporte";
		}

		String normalizado = tipoReporte.trim().toLowerCase();
		normalizado = normalizado.replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o')
				.replace('ú', 'u').replace('ñ', 'n');
		normalizado = normalizado.replaceAll("[^a-z0-9]+", "_");
		normalizado = normalizado.replaceAll("_+", "_");
		return normalizado.replaceAll("^_|_$", "");
	}

	private String normalizarClaveJson(String texto) {
		return normalizarTipoReporte(texto);
	}

	private File crearArchivo(String nombreArchivo) throws Exception {
		File carpeta = new File(CARPETA_REPORTES);
		if (!carpeta.exists() && !carpeta.mkdirs()) {
			throw new Exception("No se pudo crear la carpeta de reportes.");
		}

		return new File(carpeta, nombreArchivo);
	}

	private void escribirJson(File archivo, JsonObject contenido) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
			writer.print(gson.toJson(contenido));
		}
	}

	
}
