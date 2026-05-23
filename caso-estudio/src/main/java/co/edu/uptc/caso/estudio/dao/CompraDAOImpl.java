package co.edu.uptc.caso.estudio.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.negocio.dto.compraDto;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class CompraDAOImpl implements CompraDAO {

    private final Gson gson = new Gson();

    @Override
    public void guardarCompras(List<compraDto> compras, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(compras, writer);
            System.out.println("Compras guardadas en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar compras: " + e.getMessage());
        }
    }

    @Override
    public List<compraDto> leerCompras(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<List<compraDto>>(){}.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.out.println("Error al leer compras: " + e.getMessage());
            return null;
        }
    }
}