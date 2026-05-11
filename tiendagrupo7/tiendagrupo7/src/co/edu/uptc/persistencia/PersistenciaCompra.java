package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.utilidades.ManejadorFechas;
import java.io.*;

public class PersistenciaCompra {
    private final String RUTA = "compras.txt";

    public PersistenciaCompra() {
        try {
            File file = new File(RUTA);
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void guardarCompra(Compra compra) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(compra.getFacturaProveedor()).append(";")
              .append(ManejadorFechas.formatearFecha(compra.getFecha())).append(";")
              .append(compra.getProveedor().getIdentificacion()).append(";")
              .append(compra.getTotalCompra()).append("|");

            for (DetalleVenta dv : compra.getProductosComprados()) {
                sb.append(dv.getProducto().getCodigoProducto()).append(",")
                  .append(dv.getCantidad()).append(",")
                  .append(dv.getPrecioUnitario()).append("-");
            }
            pw.println(sb.toString());
        } catch (IOException e) { e.printStackTrace(); }
    }
}