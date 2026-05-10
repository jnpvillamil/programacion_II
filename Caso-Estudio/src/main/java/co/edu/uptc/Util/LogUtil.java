package co.edu.uptc.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {
    
    private static final String RUTA_LOGS = "logs/";
    private static final String ARCHIVO_LOG = "sistema.log";
    
    static {
        new File(RUTA_LOGS).mkdirs();
    }
    
    public static void info(String mensaje) {
        escribirLog("[INFO] " + mensaje);
    }
    
    public static void error(String mensaje) {
        escribirLog("[ERROR] " + mensaje);
    }
    
    public static void warning(String mensaje) {
        escribirLog("[WARNING] " + mensaje);
    }
    
    public static void debug(String mensaje) {
        escribirLog("[DEBUG] " + mensaje);
    }
    
    private static void escribirLog(String mensaje) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA_LOGS + ARCHIVO_LOG, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println(timestamp + " - " + mensaje);
        } catch (Exception e) {
            System.err.println("Error al escribir log: " + e.getMessage());
        }
    }
    
    public static void registrarOperacion(String usuario, String operacion, String detalles) {
        info("Usuario: " + usuario + " | Operación: " + operacion + " | Detalles: " + detalles);
    }
}