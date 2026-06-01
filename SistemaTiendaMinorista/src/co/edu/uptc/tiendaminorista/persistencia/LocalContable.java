package co.edu.uptc.tiendaminorista.persistencia;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.tiendaminorista.interfaces.IGestionContable;
import co.edu.uptc.tiendaminorista.modelo.MovimientoContable;

public class LocalContable implements IGestionContable {
    private static final String RUTA = "movimientos.json";
    private final Gson gson;
    private int ultimoCodigo;

    public LozzcalContable() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        calcularUltimoCodigo();
    }

    private void calcularUltimoCodigo() {
        List<MovimientoContable> lista = leer();
        ultimoCodigo = 0;
        for (MovimientoContable m : lista) {
            if (m != null && m.getCodigo() != null) {
                try {
                    int num = Integer.parseInt(m.getCodigo().replace("MOV", ""));
                    if (num > ultimoCodigo) ultimoCodigo = num;
                } catch (NumberFormatException ignored) {}
            }
        }
    }

    private String generarCodigo() {
        ultimoCodigo++;
        return "MOV" + String.format("%05d", ultimoCodigo);
    }

    private List<MovimientoContable> leer() {
        try (FileReader reader = new FileReader(RUTA)) {
            Type tipo = new TypeToken<List<MovimientoContable>>() {}.getType();
            List<MovimientoContable> lista = gson.fromJson(reader, tipo);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void escribir(List<MovimientoContable> lista) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(MovimientoContable movimiento) {
        List<MovimientoContable> lista = leer();
        if (movimiento.getCodigo() == null || movimiento.getCodigo().isEmpty()) {
            movimiento.setCodigo(generarCodigo());
        }
        if (movimiento.getFecha() == null) movimiento.setFecha(LocalDate.now());
        lista.add(movimiento);
        escribir(lista);
    }

    @Override
    public List<MovimientoContable> listar() { return leer(); }

    @Override
    public List<MovimientoContable> filtrarPorTipo(String tipo) {
        if (tipo == null || tipo.equals("Todos")) return leer();
        return leer().stream()
                .filter(m -> m.getTipo() != null && m.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoContable> filtrarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return leer().stream()
                .filter(m -> m.getFecha() != null
                        && !m.getFecha().isBefore(desde)
                        && !m.getFecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoContable> buscarPorCuenta(String cuenta) {
        if (cuenta == null || cuenta.trim().isEmpty()) return leer();
        return leer().stream()
                .filter(m -> m.getCuentaContable() != null
                        && m.getCuentaContable().toLowerCase().contains(cuenta.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalIngresos() {
        return leer().stream()
                .filter(m -> "Ingreso".equals(m.getTipo()))
                .mapToDouble(MovimientoContable::getCredito).sum();
    }

    @Override
    public double getTotalEgresos() {
        return leer().stream()
                .filter(m -> "Egreso".equals(m.getTipo()))
                .mapToDouble(MovimientoContable::getDebito).sum();
    }

    @Override
    public double getSaldoActual() { return getTotalIngresos() - getTotalEgresos(); }
}
