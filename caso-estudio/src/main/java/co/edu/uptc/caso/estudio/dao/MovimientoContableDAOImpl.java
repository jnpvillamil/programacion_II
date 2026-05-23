package co.edu.uptc.caso.estudio.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.negocio.dto.movimientoContableDto;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MovimientoContableDAOImpl implements MovimientoContableDAO {

    private final Gson gson = new Gson();

    @Override
    public void guardarMovimientos(List<movimientoContableDto> movimientos, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(movimientos, writer);
            System.out.println("Movimientos contables guardados en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar movimientos: " + e.getMessage());
        }
    }

    @Override
    public List<movimientoContableDto> leerMovimientos(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<List<movimientoContableDto>>(){}.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.out.println("Error al leer movimientos: " + e.getMessage());
            return null;
        }
    }
}