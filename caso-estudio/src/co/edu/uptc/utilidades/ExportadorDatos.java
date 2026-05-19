package co.edu.uptc.utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExportadorDatos {

    public static <T> boolean exportarATxt(List<T> datos, String ruta) {
        File archivo = new File(ruta);
        try (PrintWriter escritor = new PrintWriter(new FileWriter(archivo))) {
            for (T dato : datos) {
                escritor.println(dato.toString());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar los datos a TXT: " + e.getMessage());
            return false;
        }
    }
}