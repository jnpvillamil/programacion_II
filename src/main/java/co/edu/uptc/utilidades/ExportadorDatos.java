package co.edu.uptc.utilidades;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Exportación de registros a archivos de log en texto plano (UTF-8).
 */
public final class ExportadorDatos {

    private static final String FORMATO_ENCABEZADO = "=== Exportación %s ===%n";

    private ExportadorDatos() {
    }

    public static void exportarPlano(List<Object> lista, String rutaDestino) {
        if (rutaDestino == null || rutaDestino.isBlank()) {
            throw new IllegalArgumentException("La ruta de destino no puede estar vacía.");
        }

        Path ruta = Paths.get(rutaDestino);
        Path directorioPadre = ruta.getParent();
        String rutaDirectorio = directorioPadre != null ? directorioPadre.toString() : ".";
        GestorDirectorios.asegurarDirectorios(rutaDirectorio);

        String marcaTiempo = ManejadorFechas.formatearFecha(LocalDateTime.now());

        try (BufferedWriter escritor = Files.newBufferedWriter(
                ruta,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            escritor.write(String.format(FORMATO_ENCABEZADO, marcaTiempo));

            if (lista != null) {
                for (Object registro : lista) {
                    String linea = registro != null ? registro.toString() : "(nulo)";
                    escritor.write(linea);
                    escritor.newLine();
                }
            }

            escritor.newLine();
        } catch (IOException excepcion) {
            throw new IllegalStateException(
                    "No fue posible escribir el archivo de log: " + rutaDestino, excepcion);
        }
    }
}
