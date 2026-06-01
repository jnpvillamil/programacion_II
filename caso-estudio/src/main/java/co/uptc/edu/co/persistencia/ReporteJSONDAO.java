package co.uptc.edu.co.persistencia;

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

import co.uptc.edu.co.interfaces.ReporteDAO;
import co.uptc.edu.co.modelo.ResumenProductoDTO;

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
