package co.edu.uptc.caso.estudio.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.negocio.dto.ventaDto;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {

    private final Gson gson = new Gson();

    @Override
    public void guardarVentas(List<ventaDto> ventas, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(ventas, writer);
            System.out.println("Ventas guardadas en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<ventaDto> leerVentas(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<List<ventaDto>>(){}.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return null;
        }
    }
}