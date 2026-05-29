package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IRepositorioVenta;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.utilidades.ManejadorFechas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaVentasTXT implements IRepositorioVenta {

    private final String RUTA_ARCHIVO = "ventas.txt";
    private final String SEPARADOR_CABECERA = ";";
    private final String SEPARADOR_ITEM = ",";
    private static final String ESTADO_ANULADA = "ANULADA";

    public PersistenciaVentasTXT() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear archivo de ventas: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean guardarVenta(Venta venta) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(venta.getNumeroFactura()).append(SEPARADOR_CABECERA)
              .append(ManejadorFechas.formatearFecha(venta.getFechaHora())).append(SEPARADOR_CABECERA)
              .append(venta.getCliente().getIdentificacion()).append(SEPARADOR_CABECERA)
              .append(venta.getSubtotal()).append(SEPARADOR_CABECERA)
              .append(venta.getIvaAplicado()).append(SEPARADOR_CABECERA)
              .append(venta.getTotalVenta()).append(SEPARADOR_CABECERA)
              .append(venta.getFormaPago() != null ? venta.getFormaPago().name() : FormaPago.EFECTIVO.name()).append(SEPARADOR_CABECERA)
              .append("|");

            List<DetalleVenta> detalles = venta.getProductosVendidos();
            for (int i = 0; i < detalles.size(); i++) {
                DetalleVenta dv = detalles.get(i);
                sb.append(dv.getProducto().getCodigoProducto()).append(SEPARADOR_ITEM)
                  .append(dv.getCantidad()).append(SEPARADOR_ITEM)
                  .append(dv.getPrecioUnitario()).append(SEPARADOR_ITEM)
                  .append(dv.getSubtotal());
                if (i < detalles.size() - 1) {
                    sb.append("-");
                }
            }
            escritor.println(sb);
            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir venta TXT: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Venta> consultarHistorialCliente(String identificacion) {
        List<Venta> lista = new ArrayList<>();

        for (String linea : leerLineas()) {
            String[] cabecera = obtenerCabecera(linea);
            if (cabecera == null || estaAnulada(linea)) {
                continue;
            }

            if (identificacion.equals(cabecera[2])) {
                lista.add(mapearVentaBasica(cabecera));
            }
        }

        return lista;
    }

    @Override
    public List<Venta> consultarVentasPorFecha(String fecha) {
        List<Venta> lista = new ArrayList<>();
        String fechaBusqueda = normalizarFechaConsulta(fecha);

        for (String linea : leerLineas()) {
            String[] cabecera = obtenerCabecera(linea);
            if (cabecera == null || estaAnulada(linea)) {
                continue;
            }

            if (cabecera[1].startsWith(fechaBusqueda) || cabecera[1].equals(fecha)) {
                lista.add(mapearVentaBasica(cabecera));
            }
        }

        return lista;
    }

    @Override
    public Venta buscarVentaPorFactura(String numeroFactura) {
        for (String linea : leerLineas()) {
            String[] cabecera = obtenerCabecera(linea);
            if (cabecera == null || estaAnulada(linea)) {
                continue;
            }

            if (cabecera[0].equals(numeroFactura)) {
                Venta venta = new Venta();
                venta.setNumeroFactura(cabecera[0]);
                venta.setSubtotal(Double.parseDouble(cabecera[3]));
                venta.setIvaAplicado(Double.parseDouble(cabecera[4]));
                venta.setTotalVenta(Double.parseDouble(cabecera[5]));
                venta.setFormaPago(FormaPago.desdeTexto(cabecera[6]));

                Cliente cliente = new Cliente();
                cliente.setIdentificacion(cabecera[2]);
                venta.setCliente(cliente);
                venta.setProductosVendidos(parsearDetalles(linea));
                return venta;
            }
        }

        return null;
    }

    @Override
    public boolean anularVenta(String numeroFactura) {
        List<String> nuevasLineas = new ArrayList<>();
        boolean encontrada = false;

        for (String linea : leerLineas()) {
            if (linea.startsWith(numeroFactura + SEPARADOR_CABECERA) && !estaAnulada(linea)) {
                nuevasLineas.add(linea + SEPARADOR_CABECERA + ESTADO_ANULADA);
                encontrada = true;
            } else {
                nuevasLineas.add(linea);
            }
        }

        if (!encontrada) {
            return false;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (String l : nuevasLineas) {
                pw.println(l);
            }
        } catch (IOException e) {
            System.err.println("Error al anular venta TXT: " + e.getMessage());
            return false;
        }

        return true;
    }

    private List<String> leerLineas() {
        List<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer ventas TXT: " + e.getMessage());
        }

        return lineas;
    }

    private String[] obtenerCabecera(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length == 0) {
            return null;
        }

        String[] cabecera = partes[0].split(SEPARADOR_CABECERA);
        if (cabecera.length < 7) {
            return null;
        }

        return cabecera;
    }

    private boolean estaAnulada(String linea) {
        return linea.contains(SEPARADOR_CABECERA + ESTADO_ANULADA);
    }

    private Venta mapearVentaBasica(String[] cabecera) {
        Venta venta = new Venta();
        venta.setNumeroFactura(cabecera[0]);
        return venta;
    }

    private List<DetalleVenta> parsearDetalles(String linea) {
        List<DetalleVenta> detalles = new ArrayList<>();
        String[] partes = linea.split("\\|");
        if (partes.length < 2 || partes[1].isBlank()) {
            return detalles;
        }

        String[] items = partes[1].split("-");
        for (String item : items) {
            String[] datos = item.split(SEPARADOR_ITEM);
            if (datos.length < 4) {
                continue;
            }
            Producto producto = new Producto();
            producto.setCodigoProducto(datos[0]);
            detalles.add(new DetalleVenta(
                    producto,
                    Integer.parseInt(datos[1]),
                    Double.parseDouble(datos[2]),
                    Double.parseDouble(datos[3])));
        }
        return detalles;
    }

    private String normalizarFechaConsulta(String fecha) {
        if (fecha == null || fecha.isBlank()) {
            return "";
        }
        String valor = fecha.trim();
        if (valor.length() >= 10) {
            return valor.substring(0, 10);
        }
        return valor;
    }
}
