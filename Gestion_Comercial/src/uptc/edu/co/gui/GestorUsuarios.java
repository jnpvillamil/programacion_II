package uptc.edu.co.gui;

import java.io.*;
import java.util.*;

/**
 * Maneja el almacenamiento de usuarios y contraseñas
 * en un archivo de texto plano (usuarios.txt).
 *
 * Formato de cada línea: usuario,contraseña
 * Ejemplo: administrador,0000
 */
public class GestorUsuarios {

    private static final String ARCHIVO = "usuarios.txt";

    // ── Verificar si usuario+contraseña existen ───────────────────────────
    public static boolean verificarLogin(String usuario, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String u = partes[0].trim();
                    String p = partes[1].trim();
                    if (u.equals(usuario) && p.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Si no existe el archivo, crearlo con el usuario por defecto
            crearArchivoInicial();
            return verificarLogin(usuario, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ── Registrar un nuevo usuario ────────────────────────────────────────
    public static boolean registrarUsuario(String usuario, String password) {
        // No permitir usuarios duplicados
        if (usuarioExiste(usuario)) {
            return false;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(usuario + "," + password);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── Verificar si un usuario ya existe ─────────────────────────────────
    public static boolean usuarioExiste(String usuario) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 1 && partes[0].trim().equals(usuario)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // archivo no existe aún
        }
        return false;
    }

    // ── Listar todos los usuarios (sin contraseñas) ───────────────────────
    public static List<String> listarUsuarios() {
        List<String> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 1 && !partes[0].trim().isEmpty()) {
                    lista.add(partes[0].trim());
                }
            }
        } catch (IOException e) {
            // archivo no existe aún
        }
        return lista;
    }

    // ── Crear archivo inicial con usuario por defecto ─────────────────────
    private static void crearArchivoInicial() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            bw.write("administrador,0000");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
