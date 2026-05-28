package co.edu.uptc.tiendaminorista.persistencia;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.tiendaminorista.modelo.CompraPro;

public class LocalCompraPro {
    private static final String RUTA = "compras.json";
    private final Gson gson;

    public LocalCompraPro() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public List<CompraPro> leer() {
        File archivo = new File(RUTA);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(RUTA)) {
            Type tipo = new TypeToken<List<CompraPro>>() {}.getType();
            List<CompraPro> lista = gson.fromJson(reader, tipo);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void guardar(CompraPro compraPro) {
        List<CompraPro> lista = leer();
        lista.add(compraPro);
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}