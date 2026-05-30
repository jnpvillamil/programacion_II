package co.edu.uptc.sistienda.negocio;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorLog {

	private static final String RUTA_ARCHIVO = "logs/sistienda_log.txt";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void registrar(String usuario, String accion, String detalle) {
		new java.io.File("logs").mkdirs();
		String fechaActual = LocalDateTime.now().format(FORMATO_FECHA);
		String lineaLog = fechaActual + " | " + usuario + " | " + accion + " | " + detalle;
		try (PrintWriter escritorArchivo = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
			escritorArchivo.println(lineaLog);
		} catch (IOException e) {
			System.err.println("Error al escribir en el log: " + e.getMessage());
		}
	}
}