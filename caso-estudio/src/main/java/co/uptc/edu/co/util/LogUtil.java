package co.uptc.edu.co.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {
	private static final String FILE_NAME = "sistema_contable_logs.txt";

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void info(String mensaje) {
		escribirLog("INFO", mensaje);
	}

	public static void error(String mensaje, Exception ex) {
		String mensajeCompleto = mensaje + (ex != null ? " | Detalle: " + ex.getMessage() : "");
		escribirLog("ERROR", mensajeCompleto);
	}

	private static synchronized void escribirLog(String nivel, String mensaje) {
		String timestamp = LocalDateTime.now().format(FORMATTER);

		String lineaLog = String.format("[%s] [%s] %s", timestamp, nivel, mensaje);

		try (FileWriter fw = new FileWriter(FILE_NAME, true); BufferedWriter bw = new BufferedWriter(fw)) {

			bw.write(lineaLog);
			bw.newLine();

		} catch (IOException e) {

			System.err.println("No se pudo escribir en el archivo de logs: " + e.getMessage());
		}
	}
}
