package co.uptc.edu.tienda.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionUsuario;
import co.uptc.edu.tienda.modelo.Usuario;
import co.uptc.edu.tienda.enums.RolEnum;

public class LocalUsuario implements IGestionUsuario {

    private final Gson gson = 
            new GsonBuilder().setPrettyPrinting().create();

    private final String RUTA = "usuarios.json";

    public LocalUsuario() {
        super();
        // Verificamos si el archivo JSON existe. Si no, creamos los genéricos
        File archivo = new File(RUTA);
        if (!archivo.exists()) {
            inicializarUsuariosGenericos();
        }
    }

    private void inicializarUsuariosGenericos() {
        List<Usuario> genericos = new ArrayList<>();
        
        // 1. Crear el usuario ADMIN
        Usuario admin = new Usuario(); // Constructor vacío
        admin.setIdUsuario(1);
        admin.setCorreo("admin@correo.com");
        admin.setPassword("123456");
        admin.setRol(RolEnum.ADMIN);
        genericos.add(admin);

        // 2. Crear el usuario CAJERO
        Usuario cajero = new Usuario(); // Constructor vacío
        cajero.setIdUsuario(2);
        cajero.setCorreo("cajero@correo.com");
        cajero.setPassword("234567");
        cajero.setRol(RolEnum.CAJERO);
        genericos.add(cajero);

        // 3. Crear el usuario ALMACENISTA
        Usuario almacen = new Usuario(); // Constructor vacío
        almacen.setIdUsuario(3);
        almacen.setCorreo("almacen@correo.com");
        almacen.setPassword("345678");
        almacen.setRol(RolEnum.ALMACENISTA);
        genericos.add(almacen);

        // 4. Crear el usuario CONTADOR
        Usuario contador = new Usuario(); // Constructor vacío
        contador.setIdUsuario(4);
        contador.setCorreo("contador@correo.com");
        contador.setPassword("456789");
        contador.setRol(RolEnum.CONTADOR);
        genericos.add(contador);
        
        // Los guardamos en el archivo JSON
        guardar(genericos);
    }


    public void guardar(List<Usuario> usuarios) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(usuarios, writer);
            System.out.println("Usuarios guardados");
        } catch (IOException e) {
            System.out.println(
                    "Error al guardar en " + RUTA + ": " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        List<Usuario> lista = listar();
        if (lista != null) {
            for (Usuario u : lista) {
                if (u.getCorreo().equalsIgnoreCase(correo)) {
                    return u;
                }
            }
        }
        return null;
    }

    public List<Usuario> listar() {
        File archivo = new File(RUTA);

        if (!archivo.exists()) {
            return new ArrayList<Usuario>();
        }

        try {
            FileReader reader = new FileReader(archivo);
            Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> lista = gson.fromJson(reader, tipoLista);
            reader.close();

            if (lista == null) {
                return new ArrayList<Usuario>();
            }

            return lista;

        } catch (IOException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
            return new ArrayList<Usuario>();
        }
    }
}
