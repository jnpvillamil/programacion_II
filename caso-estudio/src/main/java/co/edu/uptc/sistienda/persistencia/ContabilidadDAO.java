package co.edu.uptc.sistienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
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

import co.edu.uptc.sistienda.contabilidad.modelo.LineaMovimientoContable;
import co.edu.uptc.sistienda.contabilidad.modelo.MovimientoContable;
import co.edu.uptc.sistienda.interfaces.IGestionContabilidad;

public class ContabilidadDAO implements IGestionContabilidad {

	private static final String RUTA_ARCHIVO = "datos/movimientos_contables.json";

	private List<MovimientoContable> listaMovimientos;
	private final Gson gson;

	public ContabilidadDAO() {
		gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
			private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

			@Override
			public void write(JsonWriter out, LocalDate value) throws IOException {
				if (value == null) {
					out.nullValue();
				} else {
					out.value(formatter.format(value));
				}
			}

			@Override
			public LocalDate read(JsonReader in) throws IOException {
				if (in.peek() == JsonToken.NULL) {
					in.nextNull();
					return null;
				}
				return LocalDate.parse(in.nextString(), formatter);
			}
		}).create();
		listaMovimientos = cargarDesdeArchivo();
	}

	private List<MovimientoContable> cargarDesdeArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		if (!archivo.exists() || archivo.length() == 0) {
			return new ArrayList<>();
		}

		try (FileReader reader = new FileReader(archivo)) {
			Type tipoLista = new TypeToken<List<MovimientoContable>>() {
			}.getType();
			List<MovimientoContable> lista = gson.fromJson(reader, tipoLista);
			return lista != null ? lista : new ArrayList<>();
		} catch (IOException e) {
			System.err.println("Error al leer movimientos_contables.json: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	private void guardarEnArchivo() {
		File archivo = new File(RUTA_ARCHIVO);
		archivo.getParentFile().mkdirs();

		try (FileWriter writer = new FileWriter(archivo)) {
			gson.toJson(listaMovimientos, writer);
		} catch (IOException e) {
			System.err.println("Error al guardar movimientos_contables.json: " + e.getMessage());
		}
	}

	@Override
	public void guardarMovimiento(MovimientoContable movimiento) {
		listaMovimientos.add(movimiento);
		guardarEnArchivo();
	}

	@Override
	public void anularMovimientoPorDocumento(String documentoOrigen) {
		for (MovimientoContable movimiento : listaMovimientos) {
			if (documentoOrigen.equals(movimiento.getDocumentoOrigen())) {
				movimiento.setAnulado(true);
			}
		}
		guardarEnArchivo();
	}

	@Override
	public List<MovimientoContable> obtenerListaMovimientos() {
		return new ArrayList<>(listaMovimientos);
	}

	@Override
	public List<MovimientoContable> consultarMovimientos(String cuentaContable, LocalDate fechaInicio,
			LocalDate fechaFin) {
		List<MovimientoContable> resultado = new ArrayList<>();
		for (MovimientoContable movimiento : listaMovimientos) {
			if (!estaDentroDelPeriodo(movimiento, fechaInicio, fechaFin)) {
				continue;
			}
			if (cuentaContable == null || cuentaContable.trim().isEmpty()
					|| "Todas las cuentas".equalsIgnoreCase(cuentaContable)
					|| contieneCuenta(movimiento, cuentaContable)) {
				resultado.add(movimiento);
			}
		}
		return resultado;
	}

	private boolean estaDentroDelPeriodo(MovimientoContable movimiento, LocalDate fechaInicio, LocalDate fechaFin) {
		LocalDate fecha = movimiento.getFechaMovimiento();
		if (fecha == null) {
			return false;
		}
		return (fechaInicio == null || !fecha.isBefore(fechaInicio)) && (fechaFin == null || !fecha.isAfter(fechaFin));
	}

	private boolean contieneCuenta(MovimientoContable movimiento, String cuentaContable) {
		for (LineaMovimientoContable linea : movimiento.getLineas()) {
			if (linea.getCuentaContable() != null
					&& linea.getCuentaContable().toLowerCase().contains(cuentaContable.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
