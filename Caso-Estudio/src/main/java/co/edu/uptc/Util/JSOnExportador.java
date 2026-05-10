package co.edu.uptc.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JSOnExportador {
    
    private static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .create();
    
    private static final String RUTA_REPORTES = "reportes/";
    private static final String RUTA_JSON = "json/";
    
    static {
        
        new File(RUTA_REPORTES).mkdirs();
        new File(RUTA_JSON).mkdirs();
    }
    
    
    public static void exportar(Object objeto, String archivo) {
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(objeto, writer);
            System.out.println("✅ Archivo JSON generado: " + archivo);
        } catch (IOException e) {
            System.err.println("❌ Error al generar JSON: " + e.getMessage());
        }
    }
    
   
    public static void exportarReporte(Object objeto, String nombreArchivo) {
        String rutaCompleta = RUTA_REPORTES + nombreArchivo;
        exportar(objeto, rutaCompleta);
    }
    
    // Exportar a carpeta JSON
    public static void exportarJSON(Object objeto, String nombreArchivo) {
        String rutaCompleta = RUTA_JSON + nombreArchivo;
        exportar(objeto, rutaCompleta);
    }
    

    public static <T> T leer(String archivo, Class<T> clase) {
        try (FileReader reader = new FileReader(archivo)) {
            return gson.fromJson(reader, clase);
        } catch (IOException e) {
            System.err.println("❌ Error al leer JSON: " + e.getMessage());
            return null;
        }
    }
    
    public static String toJson(Object objeto) {
        return gson.toJson(objeto);
    }
}