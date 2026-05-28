package co.edu.uptc.persistencia;

import co.edu.uptc.dto.ResumenDiarioJSONDTO;
import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.IRepositorioContable;
import co.edu.uptc.modelo.MovimientoContable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistenciaContableTXT implements IRepositorioContable {

    private static final String ARCHIVO = "movimientos_contables.txt";
    private static final String SEPARADOR = ";";
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PersistenciaContableTXT() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear archivo contable TXT: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardar(MovimientoContable mov) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(formatearLinea(mov));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar movimiento contable TXT: " + e.getMessage());
        }
    }

    @Override
    public List<MovimientoContable> consultarPorTransaccion(String codigoTransaccion) {
        List<MovimientoContable> movimientos = new ArrayList<>();

        for (MovimientoContable mov : leerTodos()) {
            if (mov.getCodigoTransaccion().equals(codigoTransaccion)) {
                movimientos.add(mov);
            }
        }

        return movimientos;
    }

    @Override
    public Map<String, Double> resumenContablePorPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        Map<String, Double> resumen = new HashMap<>();

        for (MovimientoContable mov : leerTodos()) {
            LocalDateTime fecha = mov.getFechaMovimiento();
            if (fecha.isBefore(inicio) || fecha.isAfter(fin)) {
                continue;
            }

            String cuenta = mov.getCuentaContable();
            double saldoActual = resumen.getOrDefault(cuenta, 0.0);

            if (mov.getTipoMovimiento() == TipoMovimiento.EGRESO) {
                resumen.put(cuenta, saldoActual - mov.getValorMovimiento());
            } else {
                resumen.put(cuenta, saldoActual + mov.getValorMovimiento());
            }
        }

        return resumen;
    }

    @Override
    public ResumenDiarioJSONDTO.ResumenContable resumenFinancieroPorPeriodo(
            LocalDateTime inicio, LocalDateTime fin) {
        ResumenDiarioJSONDTO.ResumenContable resumen = new ResumenDiarioJSONDTO.ResumenContable();

        for (MovimientoContable mov : leerTodos()) {
            LocalDateTime fecha = mov.getFechaMovimiento();
            if (fecha.isBefore(inicio) || fecha.isAfter(fin)) {
                continue;
            }
            acumularResumenFinanciero(resumen, mov.getCuentaContable(),
                    mov.getTipoMovimiento().name(), mov.getValorMovimiento());
        }

        return resumen;
    }

    private void acumularResumenFinanciero(ResumenDiarioJSONDTO.ResumenContable resumen,
                                           String cuenta, String tipo, double total) {
        if (cuenta == null || tipo == null) {
            return;
        }

        switch (cuenta) {
            case "Ingresos por ventas" -> {
                if ("EGRESO".equalsIgnoreCase(tipo)) {
                    resumen.setIngresos(resumen.getIngresos() + total);
                } else {
                    resumen.setIngresos(resumen.getIngresos() - total);
                }
            }
            case "Proveedores" -> {
                if ("EGRESO".equalsIgnoreCase(tipo)) {
                    resumen.setEgresos(resumen.getEgresos() + total);
                } else {
                    resumen.setEgresos(resumen.getEgresos() - total);
                }
            }
            case "IVA generado" -> {
                if ("EGRESO".equalsIgnoreCase(tipo)) {
                    resumen.setIvaGenerado(resumen.getIvaGenerado() + total);
                } else {
                    resumen.setIvaGenerado(resumen.getIvaGenerado() - total);
                }
            }
            case "IVA descontable" -> {
                if ("INGRESO".equalsIgnoreCase(tipo)) {
                    resumen.setIvaDescontable(resumen.getIvaDescontable() + total);
                } else {
                    resumen.setIvaDescontable(resumen.getIvaDescontable() - total);
                }
            }
            default -> {
            }
        }
    }

    @Override
    public void eliminar(String id) {
    }

    @Override
    public void actualizar(MovimientoContable objeto) {
    }

    @Override
    public MovimientoContable buscarPorId(String id) {
        return null;
    }

    @Override
    public List<MovimientoContable> listar() {
        return leerTodos();
    }

    private List<MovimientoContable> leerTodos() {
        List<MovimientoContable> movimientos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }

                MovimientoContable mov = parsearLinea(linea);
                if (mov != null) {
                    movimientos.add(mov);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer movimientos contables TXT: " + e.getMessage());
        }

        return movimientos;
    }

    private String formatearLinea(MovimientoContable mov) {
        return mov.getCodigoTransaccion() + SEPARADOR
                + mov.getFechaMovimiento().format(FORMATO_FECHA) + SEPARADOR
                + mov.getTipoMovimiento().name() + SEPARADOR
                + mov.getCuentaContable() + SEPARADOR
                + mov.getValorMovimiento() + SEPARADOR
                + mov.getDescripcion();
    }

    private MovimientoContable parsearLinea(String linea) {
        String[] partes = linea.split(SEPARADOR, 6);
        if (partes.length < 6) {
            return null;
        }

        try {
            return new MovimientoContable(
                    partes[0],
                    LocalDateTime.parse(partes[1], FORMATO_FECHA),
                    TipoMovimiento.valueOf(partes[2]),
                    partes[3],
                    Double.parseDouble(partes[4]),
                    partes[5]);
        } catch (Exception e) {
            System.err.println("Error al parsear movimiento contable TXT: " + e.getMessage());
            return null;
        }
    }
}
