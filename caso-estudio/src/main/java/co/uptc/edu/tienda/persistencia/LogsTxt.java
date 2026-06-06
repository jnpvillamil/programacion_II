package co.uptc.edu.tienda.persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import co.uptc.edu.tienda.modelo.Usuario;

public class LogsTxt {

    private static final String RUTA = "logs.txt";

    public void registrarLogin(Usuario usuario) {

    try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA, true))) {
        pw.println("========================================");
        pw.println("Fecha:   " + LocalDateTime.now().toString());
        pw.println("Correo:  " + usuario.getCorreo());
        pw.println("Rol:     " + usuario.getRol());
        pw.println("Acción:  Inicio de sesión exitoso");
        pw.println("========================================");

        System.out.println("Log registrado correctamente");

    } catch (IOException e) {
        System.out.println("Error al escribir log: " + e.getMessage());
    }
}
    public void registrarLogout(Usuario usuario) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA, true))) {
            pw.println("========================================");
            pw.println("Fecha:   " + LocalDateTime.now().toString());
            pw.println("Correo:  " + usuario.getCorreo());
            pw.println("Rol:     " + usuario.getRol());
            pw.println("Acción:  Cierre de sesión");
            pw.println("========================================");
        } catch (IOException e) {
            System.out.println("Error al escribir log: " + e.getMessage());
        }
    }
}