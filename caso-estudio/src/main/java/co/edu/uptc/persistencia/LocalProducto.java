package co.edu.uptc.persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.dto.Producto;

public class LocalProducto {

    private List<Producto> productos;
    private static final String FILE_NAME = "productos.json"; 

    public LocalProducto() {
        productos = cargarProductosDesdeArchivo();  
    }

    public boolean guardarProducto(Producto producto) {
 
        if (producto.getCodigoProducto() == null || producto.getCodigoProducto().trim().isEmpty()) {
            return false;
        }

        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            return false;
        }

        if (producto.getPrecioVenta() <= 0) {
            return false;
        }

        if (producto.getStock() < 0) {
            return false;
        }

        // Verificamos si el producto ya existe
        if (buscarProducto(producto.getCodigoProducto()) != null) {
            return false;  
        }

        productos.add(producto);  
        guardarProductosEnArchivo();  
        return true;
    }

    public Producto buscarProducto(String codigoProducto) {
        for (Producto producto : productos) {
            if (producto.getCodigoProducto().equalsIgnoreCase(codigoProducto)) {
                return producto;
            }
        }
        return null;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    
    private void guardarProductosEnArchivo() {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            Gson gson = new Gson();
            gson.toJson(productos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar productos desde archivo JSON
    private List<Producto> cargarProductosDesdeArchivo() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<List<Producto>>() {}.getType()); 
        } catch (FileNotFoundException e) {
            return new ArrayList<>();  
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}