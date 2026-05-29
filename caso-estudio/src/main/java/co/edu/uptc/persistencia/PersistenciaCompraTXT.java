package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IRepositorioCompra;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.utilidades.ManejadorFechas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCompraTXT implements IRepositorioCompra {

    private final String RUTA = "compras.txt";

    public PersistenciaCompraTXT() {
        try {
            File file = new File(RUTA);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo compras TXT: " + e.getMessage());
        }
    }

    @Override
    public boolean guardarCompra(Compra compra) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(compra.getFacturaProveedor()).append(";")
              .append(ManejadorFechas.formatearFecha(compra.getFecha())).append(";")
              .append(compra.getProveedor().getIdentificacion()).append(";")
              .append(compra.getTotalCompra()).append("|");

            if (compra.getProductosComprados() != null) {
                for (DetalleVenta dv : compra.getProductosComprados()) {
                    sb.append(dv.getProducto().getCodigoProducto()).append(",")
                      .append(dv.getCantidad()).append(",")
                      .append(dv.getPrecioUnitario()).append("-");
                }
            }
            pw.println(sb);
            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir compra TXT: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Compra> consultarPorProveedor(String codigoProveedor) {
        return new ArrayList<>();
    }

    @Override
    public void guardar(Compra objeto) {
        guardarCompra(objeto);
    }

    @Override
    public void eliminar(String id) {
    }

    @Override
    public Compra buscarPorId(String id) {
        return null;
    }

    @Override
    public List<Compra> listar() {
        return new ArrayList<>();
    }

    @Override
    public void actualizar(Compra objeto) {
    }
}
