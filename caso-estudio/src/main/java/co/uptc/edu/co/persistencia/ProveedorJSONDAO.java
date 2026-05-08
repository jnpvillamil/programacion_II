package co.uptc.edu.co.persistencia;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.uptc.edu.co.interfaces.ProveedorDAO;
import co.uptc.edu.co.modelo.Proveedor;

public class ProveedorJSONDAO implements ProveedorDAO {
	private static final String RUTA = "proveedores.json";
	private final Gson gson;

	public ProveedorJSONDAO() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@Override
	public void guardarProveedor(Proveedor proveedor) throws Exception {
		List<Proveedor> proveedores = listarProveedor();

		proveedores.add(proveedor);
		guardarLista(proveedores);

	}

	@Override
	public void actualizarProveedor(Proveedor proveedorActualizado) throws Exception {
		List<Proveedor> proveedores = listarProveedor();

		for (int i = 0; i < proveedores.size(); i++) {

			if (proveedores.get(i).getCodigoProveedor().equalsIgnoreCase(proveedorActualizado.getCodigoProveedor())) {

				proveedores.set(i, proveedorActualizado);

				guardarLista(proveedores);

				return;
			}
		}

		throw new Exception("No se encontró el perfil de proveedor a actualizar.");
	}

	@Override
	public Proveedor buscarProveedorPorCodigo(String codigo) throws Exception {
		List<Proveedor> proveedores = listarProveedor();

		for (Proveedor proveedor : proveedores) {

			if (proveedor.getCodigoProveedor().equalsIgnoreCase(codigo)) {

				return proveedor;
			}
		}

		return null;
	}

	@Override
	public List<Proveedor> listarProveedor() throws Exception {

		try (FileReader reader = new FileReader(RUTA)) {

			Type tipoLista = new TypeToken<List<Proveedor>>() {
			}.getType();

			List<Proveedor> proveedores = gson.fromJson(reader, tipoLista);

			return proveedores != null ? proveedores : new ArrayList<>();

		} catch (java.io.FileNotFoundException e) {
			return new ArrayList<>();
		} catch (Exception e) {
			throw new Exception("Error al leer proveedor JSON" + e.getMessage());
		}
	}

	private void guardarLista(List<Proveedor> proveedores) throws Exception {

		try (FileWriter writer = new FileWriter(RUTA)) {

			gson.toJson(proveedores, writer);
		}
	}
}
