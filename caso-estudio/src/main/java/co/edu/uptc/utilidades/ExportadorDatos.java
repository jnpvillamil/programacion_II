package co.edu.uptc.utilidades;

import co.edu.uptc.dto.ReporteConsolidadoDiarioDTO;
import co.edu.uptc.dto.ResumenDiarioJSONDTO;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExportadorDatos {

    public static <T> boolean exportarATxt(List<T> datos, String ruta) {
        try (java.io.PrintWriter escritor = new java.io.PrintWriter(new java.io.FileWriter(ruta))) {
            for (T dato : datos) {
                escritor.println(dato.toString());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar los datos a TXT: " + e.getMessage());
            return false;
        }
    }

    public static boolean exportarTablaAJson(DefaultTableModel modelo, String nombreReporte, String ruta) {
        List<Map<String, Object>> filas = new ArrayList<>();

        for (int fila = 0; fila < modelo.getRowCount(); fila++) {
            Map<String, Object> registro = new LinkedHashMap<>();
            for (int columna = 0; columna < modelo.getColumnCount(); columna++) {
                registro.put(modelo.getColumnName(columna), modelo.getValueAt(fila, columna));
            }
            filas.add(registro);
        }

        Map<String, Object> documento = new LinkedHashMap<>();
        documento.put("reporte", nombreReporte);
        documento.put("datos", filas);

        try (FileWriter escritor = new FileWriter(ruta)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(documento, escritor);
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar JSON: " + e.getMessage());
            return false;
        }
    }

    public static boolean exportarConsolidadoDiario(ReporteConsolidadoDiarioDTO consolidado, String ruta) {
        if (consolidado == null) {
            return false;
        }

        try (FileWriter escritor = new FileWriter(ruta)) {
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setPrettyPrinting()
                    .create()
                    .toJson(consolidado, escritor);
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar JSON consolidado: " + e.getMessage());
            return false;
        }
    }


    public static boolean escribirResumenDiarioJSON(ResumenDiarioJSONDTO resumen, String rutaDestino) {
        if (resumen == null || rutaDestino == null || rutaDestino.isBlank()) {
            return false;
        }

        try (FileWriter escritor = new FileWriter(rutaDestino)) {
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setPrettyPrinting()
                    .create()
                    .toJson(resumen, escritor);
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar resumen diario JSON: " + e.getMessage());
            return false;
        }
    }
}
