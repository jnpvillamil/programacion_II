package co.edu.uptc.sistienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import co.edu.uptc.sistienda.interfaces.IGestionVenta;
import co.edu.uptc.sistienda.modelo.Proveedor;
import co.edu.uptc.sistienda.modelo.Venta;

public class VentaDAO implements IGestionVenta {
	private static final String RUTA_ARCHIVO = "datos/ventas.json";
	private List<Venta> listaVentas;
	private final Gson gson;

	public VentaDAO() {
		gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
					private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

					@Override
					public void write(JsonWriter out, LocalDateTime value) throws IOException {
						if (value == null) {
							out.nullValue();
						} else {
							out.value(formatter.format(value));
						}
					}

					@Override
					public LocalDateTime read(JsonReader in) throws IOException {
						if (in.peek() == JsonToken.NULL) {
							in.nextNull();
							return null;
						}
						return LocalDateTime.parse(in.nextString(), formatter);
					}
				})
				.create();
		listaVentas = cargarDesdeArchivo();
	}

	private List<Venta> cargarDesdeArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		if (!archivo.exists() || archivo.length() == 0) {
			return new ArrayList<>();
		}
		try (FileReader reader = new FileReader(archivo)) {
			Type tipoLista = new TypeToken<List<Venta>>() {
			}.getType();
			List<Venta> lista = gson.fromJson(reader, tipoLista);
			return lista != null ? lista : new ArrayList<>();
		} catch (IOException e) {
			System.err.println("Error al leer ventas.json: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	private void guardarEnArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		try (FileWriter writer = new FileWriter(archivo)) {
			gson.toJson(listaVentas, writer);

		} catch (IOException e) {
			System.err.println("Error al guardar ventas.json: " + e.getMessage());
		}
	}

	@Override
	public void guardarVenta(Venta venta) {
		listaVentas.add(venta);
		guardarEnArchivo();
	}

	@Override
	public void anularVenta(String numeroFactura) {
		for (Venta v : listaVentas) {
			if (v.getNumeroFactura().equals(numeroFactura)) {
				v.setAnulada(true);
				return;
			}
		}
	}

	@Override
	public Venta buscarVentaPorNumeroFactura(String numeroFactura) {
		for (Venta v : listaVentas) {
			if (v.getNumeroFactura().equals(numeroFactura)) {
				return v;
			}
		}
		return null;
	}

	@Override
	public List<Venta> obtenerListaVentas() {
		return new ArrayList<>(listaVentas);
	}

}
