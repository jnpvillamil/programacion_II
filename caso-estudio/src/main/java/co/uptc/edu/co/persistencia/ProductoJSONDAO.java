package co.uptc.edu.co.persistencia;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.uptc.edu.co.interfaces.ProductoDAO;
import co.uptc.edu.co.modelo.Producto;

public class ProductoJSONDAO implements ProductoDAO {

    private static final String RUTA = "productos.json";
    private final Gson gson;

    public ProductoJSONDAO() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void guardarProducto(Producto producto) throws Exception {

        List<Producto> productos = listarProducto();

        productos.add(producto);

        guardarLista(productos);
    }

    @Override
    public void actualizarProducto(Producto producto) throws Exception {

        List<Producto> productos = listarProducto();

        for (int i = 0; i < productos.size(); i++) {

            if (productos.get(i)
                    .getCodigoProducto()
                    .equalsIgnoreCase(producto.getCodigoProducto())) {

                productos.set(i, producto);

                guardarLista(productos);

                return;
            }
        }

        throw new Exception(
            "No se encontró el producto a actualizar."
        );
    }

    @Override
    public Producto buscarPorCodigo(String codigo)
            throws Exception {

        List<Producto> productos = listarProducto();

        for (Producto producto : productos) {

            if (producto.getCodigoProducto()
                    .equalsIgnoreCase(codigo)) {

                return producto;
            }
        }

        return null;
    }

    @Override
    public List<Producto> listarProducto() throws Exception {

        try (FileReader reader = new FileReader(RUTA)) {

            Type tipoLista =
                    new TypeToken<List<Producto>>() {}.getType();

            List<Producto> productos =
                    gson.fromJson(reader, tipoLista);

            return productos != null
                    ? productos
                    : new ArrayList<>();

        } catch (java.io.FileNotFoundException e) {
        	return new ArrayList<>();
        } catch (Exception e) {
        	throw new Exception ("Error al leer productos JSON" + e.getMessage()
        	);
        }
    }
    private void guardarLista(List<Producto> productos)
            throws Exception {

        try (FileWriter writer = new FileWriter(RUTA)) {

            gson.toJson(productos, writer);
        }
    }
}