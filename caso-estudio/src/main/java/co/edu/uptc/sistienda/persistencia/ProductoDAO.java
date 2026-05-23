package co.edu.uptc.sistienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.sistienda.interfaces.IGestionProducto;
import co.edu.uptc.sistienda.modelo.Producto;

public class ProductoDAO implements IGestionProducto {
	private static final String RUTA_ARCHIVO = "datos/productos.json";
	private List<Producto> listaProductos;
	private final Gson gson;

	public ProductoDAO() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		listaProductos = cargarDesdeArchivo();
	}

	private List<Producto> cargarDesdeArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		if (!archivo.exists() || archivo.length() == 0) {
			return new ArrayList<>();
		}
		try (FileReader reader = new FileReader(archivo)) {
			Type tipoLista = new TypeToken<List<Producto>>() {
			}.getType();
			List<Producto> lista = gson.fromJson(reader, tipoLista);
			return lista != null ? lista : new ArrayList<>();
		} catch (IOException e) {
			System.err.println("Error al leer producto.json: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	private void guardarEnArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		try (FileWriter writer = new FileWriter(archivo)) {
			gson.toJson(listaProductos, writer);

		} catch (IOException e) {
			System.err.println("Error al guardar productos.json: " + e.getMessage());
		}
	}

	@Override
	public void guardarProducto(Producto producto) {
		listaProductos.add(producto);
		guardarEnArchivo();
	}

	@Override
	public void actualizarProducto(Producto productoActualizado) {
		Producto existente = buscarProductoPorCodigo(productoActualizado.getCodigoInterno());
		if (existente != null) {
			existente.setNombreProducto(productoActualizado.getNombreProducto());
			existente.setCategoria(productoActualizado.getCategoria());
			existente.setTipoImpuesto(productoActualizado.getTipoImpuesto());
			existente.setPrecioCompra(productoActualizado.getPrecioCompra());
			existente.setPrecioVenta(productoActualizado.getPrecioVenta());
			existente.setStockActual(productoActualizado.getStockActual());
			existente.setStockMinimo(productoActualizado.getStockMinimo());
		}
	}

	@Override
	public void inactivarProducto(String codigoInterno) {
		Producto existente = buscarProductoPorCodigo(codigoInterno);
		if (existente != null) {
			existente.setActivo(false);
		}
	}

	@Override
	public void activarProducto(String codigoInterno) {
		Producto existente = buscarProductoPorCodigo(codigoInterno);
		if (existente != null) {
			existente.setActivo(true);
		}
	}

	@Override
	public Producto buscarProductoPorCodigo(String codigoInterno) {
		for (Producto producto : listaProductos) {
			if (producto.getCodigoInterno().equalsIgnoreCase(codigoInterno)) {
				return producto;
			}
		}
		return null;
	}

	@Override
	public List<Producto> obtenerListaProductos() {
		return listaProductos;
	}

	@Override
	public List<Producto> obtenerProductosConStockBajoMinimo() {
		return listaProductos.stream().filter(p -> p.isActivo() && p.tieneStockBajoMinimo())
				.collect(Collectors.toList());
	}

}