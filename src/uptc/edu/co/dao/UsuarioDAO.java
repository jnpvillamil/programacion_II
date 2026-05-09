package uptc.edu.co.dao;

import uptc.edu.co.model.Usuario;
import java.io.*;
import java.util.*;


public class UsuarioDAO {

    //Aqui se aloja los usuarios creaddos
	
    private static final String ARCHIVO = "usuarios.txt";

    
    public boolean guardar(Usuario usuario) throws IOException {
        
        if (existe(usuario.getNombre())) {
            return false;
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(usuario.getNombre() + "," + usuario.getPassword());
            bw.newLine(); 
        }
        return true;
    }

    
    public Usuario buscarPorNombre(String nombre) throws IOException {
        for (Usuario u : listarTodos()) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }

   
    public List<Usuario> listarTodos() throws IOException {
        List<Usuario> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        
        if (!archivo.exists()) {
            crearArchivoConDefecto();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue; // saltar líneas vacías
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    lista.add(new Usuario(partes[0].trim(), partes[1].trim()));
                }
            }
        }
        return lista;
    }

    
    public boolean actualizar(String nombre, String nuevaPass) throws IOException {
        List<Usuario> todos = listarTodos();
        boolean encontrado = false;

        for (Usuario u : todos) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                u.setPassword(nuevaPass); 
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            reescribirArchivo(todos); 
        }
        return encontrado;
    }

    
    public boolean eliminar(String nombre) throws IOException {
        
        if ("administrador".equalsIgnoreCase(nombre)) {
            return false;
        }
        List<Usuario> todos = listarTodos();
        
        
        List<Usuario> filtrados = new ArrayList<>();
        boolean encontrado = false;
        for (Usuario u : todos) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                encontrado = true; 
            } else {
                filtrados.add(u);
            }
        }

        if (encontrado) {
            reescribirArchivo(filtrados);
        }
        return encontrado;
    }

    
    public boolean existe(String nombre) throws IOException {
        return buscarPorNombre(nombre) != null;
    }

    
    
    private void reescribirArchivo(List<Usuario> lista) throws IOException {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Usuario u : lista) {
                bw.write(u.getNombre() + "," + u.getPassword());
                bw.newLine();
            }
        }
    }

    
    
    private void crearArchivoConDefecto() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            bw.write("administrador,0000");
            bw.newLine();
        }
    }
}
