package co.edu.uptc.utilidades;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public final class ExportadorDatos {

    private ExportadorDatos() {
    }

    public static void registrarLog(String linea, String rutaDestino) {
        if (rutaDestino == null || rutaDestino.isBlank()) {
            throw new IllegalArgumentException("La ruta de destino no puede estar vacía.");
        }
        if (linea == null || linea.isBlank()) {
            throw new IllegalArgumentException("La línea de log no puede estar vacía.");
        }

        Path ruta = Paths.get(rutaDestino);
        Path directorioPadre = ruta.getParent();
        String rutaDirectorio = directorioPadre != null ? directorioPadre.toString() : ".";
        GestorDirectorios.asegurarDirectorios(rutaDirectorio);

        try (BufferedWriter escritor = Files.newBufferedWriter(
                ruta,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            escritor.write(linea);
            escritor.newLine();
        } catch (IOException excepcion) {
            throw new IllegalStateException(
                    "No fue posible registrar el log: " + rutaDestino, excepcion);
        }
    }

    public static void exportarPlano(List<Object> lista, String rutaDestino) {
        if (lista == null || lista.isEmpty()) {
            return;
        }
        for (Object registro : lista) {
            String linea = registro != null ? registro.toString() : "(nulo)";
            registrarLog(linea, rutaDestino);
        }
    }
}
