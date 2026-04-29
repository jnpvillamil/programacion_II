package uptc.edu.co.gui;

import java.io.*;
import java.util.*;


public class GestorUsuarios {

    private static final String ARCHIVO = "usuarios.txt";

    
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
            
            crearArchivoInicial();
            return verificarLogin(usuario, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public static boolean registrarUsuario(String usuario, String password) {
        
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
            
        }
        return false;
    }

    
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
            
        }
        return lista;
    }

    
    private static void crearArchivoInicial() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            bw.write("administrador,0000");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
