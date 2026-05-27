package co.edu.uptc.utilidades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Serialización y deserialización JSON mediante Gson.
 */
public final class AdaptadorJson {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private AdaptadorJson() {
    }

    public static <T> String convertirAJson(List<T> lista) {
        if (lista == null) {
            return "[]";
        }
        return GSON.toJson(lista);
    }

    public static <T> List<T> convertirDesdeJson(String json, Class<T[]> claseArreglo) {
        if (json == null || json.isBlank()) {
            return Collections.emptyList();
        }
        if (claseArreglo == null) {
            throw new IllegalArgumentException("La clase del arreglo no puede ser nula.");
        }
        T[] arreglo = GSON.fromJson(json, claseArreglo);
        if (arreglo == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(arreglo);
    }
}
