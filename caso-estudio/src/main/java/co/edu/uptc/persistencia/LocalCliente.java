package co.edu.uptc.persistencia;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import co.edu.uptc.gui.modelo.Cliente;

public class LocalCliente {

    private List<Cliente> clientes;
    private static final String FILE_NAME = "clientes.json"; 
    
    public LocalCliente() {
        clientes = new ArrayList<>();
        clientes=cargarClientesDesdeArchivo();
    }

    public boolean guardarCliente(Cliente cliente) {
        if (buscarCliente(cliente.getCodigo()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public Cliente buscarCliente(String codigoCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo().equalsIgnoreCase(codigoCliente)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean actualizarCliente(Cliente clienteActualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCodigo().equalsIgnoreCase(clienteActualizado.getCodigo())) {
                clientes.set(i, clienteActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarCliente(String codigoCliente) {
        Cliente cliente = buscarCliente(codigoCliente);
        if (cliente != null) {
            clientes.remove(cliente);
            return true;
        }
        return false;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    private void guardarClientesEnArchivo() {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            Gson gson = new Gson();
            gson.toJson(clientes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar clientes desde el archivo JSON
    private List<Cliente> cargarClientesDesdeArchivo() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<List<Cliente>>() {}.getType()); 
        } catch (FileNotFoundException e) {
            return new ArrayList<>();  
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
}
