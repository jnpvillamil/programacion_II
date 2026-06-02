package co.edu.uptc.utilidades;

import co.edu.uptc.persistencia.ConexionSql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class EjecutorScriptSql {

    private EjecutorScriptSql() {
    }

    public static void ejecutarArchivo(Path archivoSql) throws IOException, SQLException {
        List<String> sentencias = parsearSentencias(Files.readString(archivoSql));
        try (Connection conexion = ConexionSql.getConexion();
             Statement sentencia = conexion.createStatement()) {
            for (String sql : sentencias) {
                sentencia.execute(sql);
            }
        }
    }

    static List<String> parsearSentencias(String contenido) {
        StringBuilder limpio = new StringBuilder();
        for (String linea : contenido.split("\\R")) {
            String recortada = linea.trim();
            if (recortada.isEmpty() || recortada.startsWith("--")) {
                continue;
            }
            limpio.append(linea).append('\n');
        }

        List<String> sentencias = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        for (String linea : limpio.toString().split("\\R")) {
            actual.append(linea).append('\n');
            if (linea.trim().endsWith(";")) {
                String sql = actual.toString().trim();
                sql = sql.substring(0, sql.length() - 1).trim();
                if (!sql.isEmpty() && !esDirectivaIgnorada(sql)) {
                    sentencias.add(sql);
                }
                actual.setLength(0);
            }
        }
        return sentencias;
    }

    private static boolean esDirectivaIgnorada(String sql) {
        String normalizada = sql.trim().toUpperCase();
        return normalizada.startsWith("USE ");
    }

    public static void main(String[] args) throws Exception {
        Path script = Path.of(System.getProperty("user.dir"), "bd", "02_reparacion_accesos.sql");
        if (args.length > 0) {
            script = Path.of(args[0]);
        }
        System.out.println("Ejecutando: " + script.toAbsolutePath());
        ejecutarArchivo(script);
        System.out.println("Script ejecutado correctamente.");
        DiagnosticoBd.main(new String[0]);
    }
}
