package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProducto implements Repositorio<Producto> {

    private final String RUTA_ARCHIVO = "productos.txt";
    private final String SEPARADOR = ";";

    public PersistenciaProducto() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de productos: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardar(Producto producto) {
        List<Producto> productos = listar();
        productos.add(producto);
        sobrescribirArchivo(productos);
    }

    @Override
    public void eliminar(String id) {
        List<Producto> productos = listar();
        productos.removeIf(p -> p.getCodigoProducto().equals(id));
        sobrescribirArchivo(productos);
    }

    @Override
    public Producto buscarPorId(String id) {
        List<Producto> productos = listar();
        for (Producto p : productos) {
            if (p.getCodigoProducto().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(SEPARADOR);
                    Producto p = new Producto(
                            datos[0], // codigoProducto
                            datos[1], // nombreProducto
                            datos[2], // categoria
                            Double.parseDouble(datos[3]), // precioCompra
                            Double.parseDouble(datos[4]), // precioVenta
                            Integer.parseInt(datos[5]), // stockActual
                            Integer.parseInt(datos[6]), // stockMinimo
                            Integer.parseInt(datos[7]), // stockMaximo
                            Boolean.parseBoolean(datos[8]) // activo
                    );
                    productos.add(p);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer productos: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public void actualizar(Producto productoActualizado) {
        List<Producto> productos = listar();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigoProducto().equals(productoActualizado.getCodigoProducto())) {
                productos.set(i, productoActualizado);
                break;
            }
        }
        sobrescribirArchivo(productos);
    }

    private void sobrescribirArchivo(List<Producto> productos) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Producto p : productos) {
                escritor.println(
                        p.getCodigoProducto() + SEPARADOR +
                        p.getNombreProducto() + SEPARADOR +
                        p.getCategoria() + SEPARADOR +
                        p.getPrecioCompra() + SEPARADOR +
                        p.getPrecioVenta() + SEPARADOR +
                        p.getStockActual() + SEPARADOR +
                        p.getStockMinimo() + SEPARADOR +
                        p.getStockMaximo() + SEPARADOR +
                        p.isActivo()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en productos: " + e.getMessage());
        }
    }
}