package co.edu.uptc.utilidades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static void escribirJsonEnArchivo(Object objeto, String rutaArchivo) {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new IllegalArgumentException("La ruta del archivo no puede estar vacía.");
        }
        try {
            Path ruta = Path.of(rutaArchivo);
            Path directorio = ruta.getParent();
            if (directorio != null) {
                GestorDirectorios.asegurarDirectorios(directorio.toString());
            }
            Files.writeString(ruta, GSON.toJson(objeto), StandardCharsets.UTF_8);
        } catch (IOException excepcion) {
            throw new IllegalStateException("No fue posible escribir el archivo JSON: " + rutaArchivo, excepcion);
        }
    }

    public static <T> T leerJsonDesdeArchivo(String rutaArchivo, Class<T> clase) {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new IllegalArgumentException("La ruta del archivo no puede estar vacía.");
        }
        if (clase == null) {
            throw new IllegalArgumentException("La clase de destino no puede ser nula.");
        }
        try {
            Path ruta = Path.of(rutaArchivo);
            if (!Files.exists(ruta)) {
                throw new IllegalStateException("El archivo JSON no existe: " + rutaArchivo);
            }
            String contenido = Files.readString(ruta, StandardCharsets.UTF_8);
            return GSON.fromJson(contenido, clase);
        } catch (IOException excepcion) {
            throw new IllegalStateException("No fue posible leer el archivo JSON: " + rutaArchivo, excepcion);
        }
    }
}
