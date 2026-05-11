package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Administrador;
import co.edu.uptc.modelo.Cajero;
import co.edu.uptc.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario {

    private final String RUTA_ARCHIVO = "usuarios.txt";
    private final String SEPARADOR = ";";

    public PersistenciaUsuario() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                crearUsuariosPorDefecto();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de usuarios: " + e.getMessage());
            }
        }
    }

    private void crearUsuariosPorDefecto() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Administrador("Admin General", "123456", "Calle 1", "555-0000", "admin", "admin123")); //credenciales here
        usuarios.add(new Cajero("Cajero Principal", "654321", "Calle 2", "555-1111", "cajero", "cajero123"));
        sobrescribirArchivo(usuarios);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(SEPARADOR);
                    String rol = datos[6];
                    if (rol.equals("ADMINISTRADOR")) {
                        usuarios.add(new Administrador(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]));
                    } else if (rol.equals("CAJERO")) {
                        usuarios.add(new Cajero(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    private void sobrescribirArchivo(List<Usuario> usuarios) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Usuario u : usuarios) {
                escritor.println(
                        u.getNombre() + SEPARADOR +
                        u.getIdentificacion() + SEPARADOR +
                        u.getDireccion() + SEPARADOR +
                        u.getTelefono() + SEPARADOR +
                        u.getUsuario() + SEPARADOR +
                        u.getClave() + SEPARADOR +
                        u.obtenerRol()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en usuarios: " + e.getMessage());
        }
    }
}