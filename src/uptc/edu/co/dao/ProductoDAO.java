package uptc.edu.co.dao;

import uptc.edu.co.model.Producto;
import java.io.*;
import java.util.*;


public class ProductoDAO {

    private static final String ARCHIVO = "productos.txt";

    
    public boolean guardar(Producto producto) throws IOException {
        if (existe(producto.getCodigo())) {
            return false; 
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(producto.toLineaArchivo());
            bw.newLine();
        }
        return true;
    }

    
    public Producto buscarPorCodigo(String codigo) throws IOException {
        for (Producto p : listarTodos()) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    
    public List<Producto> listarTodos() throws IOException {
        List<Producto> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            archivo.createNewFile(); // crear vacío si no existe
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                Producto p = Producto.desdeLinea(linea); // método en el modelo
                if (p != null) lista.add(p);
            }
        }
        return lista;
    }

    
    public boolean actualizar(Producto productoActualizado) throws IOException {
        List<Producto> todos = listarTodos();
        boolean encontrado = false;

        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getCodigo().equalsIgnoreCase(productoActualizado.getCodigo())) {
                todos.set(i, productoActualizado); 
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            reescribirArchivo(todos);
        }
        return encontrado;
    }

    
    public boolean eliminar(String codigo) throws IOException {
        List<Producto> todos = listarTodos();
        List<Producto> filtrados = new ArrayList<>();
        boolean encontrado = false;

        for (Producto p : todos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = true;
            } else {
                filtrados.add(p); 
            }
        }

        if (encontrado) {
            reescribirArchivo(filtrados);
        }
        return encontrado;
    }

    
    public boolean existe(String codigo) throws IOException {
        return buscarPorCodigo(codigo) != null;
    }

    
    private void reescribirArchivo(List<Producto> lista) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Producto p : lista) {
                bw.write(p.toLineaArchivo());
                bw.newLine();
            }
        }
    }
}
