package co.uptc.edu.tienda.persistencia;

import java.io.FileWriter;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.uptc.edu.tienda.interfaces.IGestionReporte;
import co.uptc.edu.tienda.modelo.ResumenFinanciero;

public class LocalReporte implements IGestionReporte {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void guardar(ResumenFinanciero resumen) {
        // Genera un archivo por cada reporte con la fecha actual
        String nombreArchivo = "reporte_" + LocalDate.now().toString() + ".json";
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gson.toJson(resumen, writer);
            System.out.println("Reporte generado: " + nombreArchivo);
        } catch (Exception e) {
            System.out.println("Error al guardar reporte: " + e.getMessage());
        }
    }
}