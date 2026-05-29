package co.edu.uptc.sistienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.sistienda.interfaces.IGestionProveedor;
import co.edu.uptc.sistienda.modelo.Proveedor;

public class ProveedorDAO implements IGestionProveedor {
	private static final String RUTA_ARCHIVO = "datos/proveedores.json";
	private List<Proveedor> listaProveedores;
	private final Gson gson;

	public ProveedorDAO() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		listaProveedores = cargarDesdeArchivo();
	}

	private List<Proveedor> cargarDesdeArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		if (!archivo.exists() || archivo.length() == 0) {
			return new ArrayList<>();
		}
		try (FileReader reader = new FileReader(archivo)) {
			Type tipoLista = new TypeToken<List<Proveedor>>() {
			}.getType();
			List<Proveedor> lista = gson.fromJson(reader, tipoLista);
			return lista != null ? lista : new ArrayList<>();
		} catch (IOException e) {
			System.err.println("Error al leer proveedor.json: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	private void guardarEnArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		try (FileWriter writer = new FileWriter(archivo)) {
			gson.toJson(listaProveedores, writer);

		} catch (IOException e) {
			System.err.println("Error al guardar proveedores.json: " + e.getMessage());
		}
	}

	@Override
	public void guardarProveedor(Proveedor proveedor) {
		listaProveedores.add(proveedor);
		guardarEnArchivo();
	}

	@Override
	public void actualizarProveedor(Proveedor proveedorActualizado) {
		Proveedor existente = buscarProveedorPorCodigo(proveedorActualizado.getCodigoProveedor());
		if (existente != null) {
			existente.setRazonSocial(proveedorActualizado.getRazonSocial());
			existente.setNit(proveedorActualizado.getNit());
			existente.setDireccion(proveedorActualizado.getDireccion());
			existente.setCiudad(proveedorActualizado.getCiudad());
			existente.setTelefono(proveedorActualizado.getTelefono());
			existente.setCorreoElectronico(proveedorActualizado.getCorreoElectronico());
			existente.setResponsabilidadFiscal(proveedorActualizado.getResponsabilidadFiscal());
			existente.setResponsabilidadTributaria(proveedorActualizado.getResponsabilidadTributaria());
			existente.setActividadEcocomica(proveedorActualizado.getActividadEconomica());
		}
	}

	@Override
	public void inactivarProveedor(String codigoProveedor) {
		Proveedor existente = buscarProveedorPorCodigo(codigoProveedor);
		if (existente != null) {
			existente.setActivo(false);
		}
	}

	@Override
	public void activarProveedor(String codigoProveedor) {
		Proveedor existente = buscarProveedorPorCodigo(codigoProveedor);
		if (existente != null) {
			existente.setActivo(true);
		}
	}

	@Override
	public Proveedor buscarProveedorPorCodigo(String codigoProveedor) {
		for (Proveedor proveedor : listaProveedores) {
			if (proveedor.getCodigoProveedor().equalsIgnoreCase(codigoProveedor)) {
				return proveedor;
			}
		}
		return null;
	}

	@Override
	public List<Proveedor> obtenerListaProveedores() {
		return listaProveedores;
	}

}