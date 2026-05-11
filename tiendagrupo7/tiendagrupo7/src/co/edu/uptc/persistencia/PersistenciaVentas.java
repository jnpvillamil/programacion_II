package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.utilidades.ManejadorFechas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaVentas {

    private final String RUTA_ARCHIVO = "ventas.txt";
    private final String SEPARADOR_CABECERA = ";";
    private final String SEPARADOR_DETALLE = "\\|";
    private final String SEPARADOR_ITEM = ",";

    public PersistenciaVentas() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear archivo de ventas: " + e.getMessage());
            }
        }
    }

    public void guardarVenta(Venta venta) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            StringBuilder sb = new StringBuilder();
            
            // 1. Cabecera
            sb.append(venta.getNumeroFactura()).append(SEPARADOR_CABECERA)
              .append(ManejadorFechas.formatearFecha(venta.getFechaHora())).append(SEPARADOR_CABECERA)
              .append(venta.getCliente().getIdentificacion()).append(SEPARADOR_CABECERA)
              .append(venta.getSubtotal()).append(SEPARADOR_CABECERA)
              .append(venta.getIvaAplicado()).append(SEPARADOR_CABECERA)
              .append(venta.getTotalVenta()).append(SEPARADOR_CABECERA)
              .append(venta.getFormaPago()).append(SEPARADOR_CABECERA);
            
            sb.append("|");

         
            for (int i = 0; i < venta.getProductosVendidos().size(); i++) {
                DetalleVenta dv = venta.getProductosVendidos().get(i);
                sb.append(dv.getProducto().getCodigoProducto()).append(SEPARADOR_ITEM)
                  .append(dv.getCantidad()).append(SEPARADOR_ITEM)
                  .append(dv.getPrecioUnitario()).append(SEPARADOR_ITEM)
                  .append(dv.getSubtotal());
                  
                if (i < venta.getProductosVendidos().size() - 1) {
                    sb.append("-"); 
                }
            }
            
            escritor.println(sb.toString());
        } catch (IOException e) {
            System.err.println("Error al escribir la venta: " + e.getMessage());
        }
    }
}