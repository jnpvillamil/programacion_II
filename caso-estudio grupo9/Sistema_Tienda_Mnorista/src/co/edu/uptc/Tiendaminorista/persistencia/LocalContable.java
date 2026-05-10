package co.edu.uptc.Tiendaminorista.persistencia;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.Tiendaminorista.interfaces.IGestionContable;
import co.edu.uptc.Tiendaminorista.modelo.MovimientoContable;
import co.edu.uptc.Tiendaminorista.persistencia.LocalDateAdapter;

public class LocalContable implements IGestionContable {

    private final String RUTA = "movimientos.json";
    private final Gson gson;
    private int ultimoCodigo;

    public LocalContable() {

    	this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        calcularUltimoCodigo();
    }

    private void calcularUltimoCodigo() {
        List<MovimientoContable> lista = leer();
        ultimoCodigo = 0;
        if (lista != null) {
            for (MovimientoContable m : lista) {
                if (m != null && m.getCodigo() != null) {
                    try {
                        String cod = m.getCodigo().replace("MOV", "");
                        if (!cod.isEmpty()) {
                            int codNum = Integer.parseInt(cod);
                            if (codNum > ultimoCodigo) {
                                ultimoCodigo = codNum;
                            }
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
    }

    private String generarCodigo() {
        ultimoCodigo++;
        return "MOV" + String.format("%05d", ultimoCodigo);
    }

    private List<MovimientoContable> leer() {
        try (FileReader reader = new FileReader(RUTA)) {
            Type tipoLista = new TypeToken<List<MovimientoContable>>() {}.getType();
            List<MovimientoContable> lista = gson.fromJson(reader, tipoLista);
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
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDate.now());
        }
        lista.add(movimiento);
        escribir(lista);
    }

    @Override
    public List<MovimientoContable> listar() {
        return leer();
    }

    @Override
    public List<MovimientoContable> filtrarPorTipo(String tipo) {
        List<MovimientoContable> lista = leer();
        if (tipo == null || tipo.equals("Todos")) {
            return lista;
        }
        return lista.stream()
                .filter(m -> m.getTipo() != null && m.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoContable> filtrarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        List<MovimientoContable> lista = leer();
        return lista.stream()
                .filter(m -> m.getFecha() != null && !m.getFecha().isBefore(desde) && !m.getFecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoContable> buscarPorCuenta(String cuentaContable) {
        List<MovimientoContable> lista = leer();
        if (cuentaContable == null || cuentaContable.trim().isEmpty()) {
            return lista;
        }
        return lista.stream()
                .filter(m -> m.getCuentaContable() != null && m.getCuentaContable().toLowerCase().contains(cuentaContable.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalIngresos() {
        List<MovimientoContable> lista = leer();
        return lista.stream()
                .filter(m -> m.getTipo() != null && m.getTipo().equals("Ingreso"))
                .mapToDouble(MovimientoContable::getCredito)
                .sum();
    }

    @Override
    public double getTotalEgresos() {
        List<MovimientoContable> lista = leer();
        return lista.stream()
                .filter(m -> m.getTipo() != null && m.getTipo().equals("Egreso"))
                .mapToDouble(MovimientoContable::getDebito)
                .sum();
    }

    @Override
    public double getSaldoActual() {
        return getTotalIngresos() - getTotalEgresos();
    }
}