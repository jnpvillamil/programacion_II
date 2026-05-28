package co.edu.uptc.utilidades;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LogSistema {

    private static final String RUTA_LOG = "logs/sistema.txt";
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private LogSistema() {
    }

    public static void registrar(String tipo, String mensaje) {
        try {
            Files.createDirectories(Paths.get("logs"));
            try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_LOG, true))) {
                escritor.println("[" + LocalDateTime.now().format(FORMATO) + "] [" + tipo + "] " + mensaje);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir log del sistema: " + e.getMessage());
        }
    }

    public static void loginExitoso(String usuario) {
        registrar("LOGIN", "Acceso exitoso. Usuario=" + usuario);
    }

    public static void loginFallido(String usuario) {
        registrar("LOGIN", "Acceso fallido. Usuario=" + usuario);
    }

    public static void ventaRegistrada(String numeroFactura, double total) {
        registrar("VENTA", "Venta registrada. Factura=" + numeroFactura + ", total=" + total);
    }

    public static void ventaAnulada(String numeroFactura) {
        registrar("ANULACION", "Venta anulada. Factura=" + numeroFactura);
    }

    public static void sesionCerrada(String usuario) {
        registrar("LOGOUT", "Sesion cerrada. Usuario=" + usuario);
    }

    public static void error(String contexto, String mensaje) {
        registrar("ERROR", contexto + " -> " + mensaje);
    }
}
