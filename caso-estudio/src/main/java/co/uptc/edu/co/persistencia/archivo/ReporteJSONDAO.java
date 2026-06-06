package co.uptc.edu.co.persistencia.archivo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.uptc.edu.co.interfaces.IGestionReporte;

public class ReporteJSONDAO implements IGestionReporte {

	private static final String CARPETA_REPORTES = "reportes";

	@Override
	public void guardar(String nombreReporte, Object datos) throws Exception {
		if (nombreReporte == null || nombreReporte.trim().isEmpty()) {
			throw new Exception("El nombre del reporte es obligatorio.");
		}

		if (datos == null) {
			throw new Exception("No hay datos para guardar el reporte.");
		}

		File archivo = crearArchivo(nombreReporte + ".json");

		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();

		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
			writer.print(gson.toJson(datos));
		}
	}

	@Override
	public List<String> listar() throws Exception {
		File carpeta = new File(CARPETA_REPORTES);
		List<String> reportes = new ArrayList<>();

		if (!carpeta.exists()) {
			return reportes;
		}

		File[] archivos = carpeta.listFiles();

		if (archivos == null) {
			return reportes;
		}

		for (File archivo : archivos) {
			if (archivo.isFile() && archivo.getName().endsWith(".json")) {
				reportes.add(archivo.getName());
			}
		}

		return reportes;
	}

	private File crearArchivo(String nombreArchivo) throws Exception {
		File carpeta = new File(CARPETA_REPORTES);

		if (!carpeta.exists() && !carpeta.mkdirs()) {
			throw new Exception("No se pudo crear la carpeta de reportes.");
		}

		return new File(carpeta, nombreArchivo);
	}
}