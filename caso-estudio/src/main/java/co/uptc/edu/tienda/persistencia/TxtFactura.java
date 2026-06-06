package co.uptc.edu.tienda.persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.Venta;

public class TxtFactura {

    private static final String CARPETA = "facturas/";
    private static final String NIT_EMPRESA = "900.123.456-7";
    private static final String NOMBRE_EMPRESA = "TIENDA MINORISTA UPTC";
    private static final String DIRECCION_EMPRESA = "Calle 1 # 2-3, Tunja";
    private static final String TEL_EMPRESA = "601-1234567";

    public void generarFactura(Venta venta) {

        new java.io.File(CARPETA).mkdirs();

        String archivo = CARPETA + venta.getNumeroFactura() + ".txt";

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

            // ENCABEZADO EMPRESA
            pw.println("========================================");
            pw.println("         " + NOMBRE_EMPRESA);
            pw.println("         NIT: " + NIT_EMPRESA);
            pw.println("         " + DIRECCION_EMPRESA);
            pw.println("         Tel: " + TEL_EMPRESA);
            pw.println("========================================");

            // DATOS FACTURA
            pw.println("Factura No: " + venta.getNumeroFactura());
            pw.println("Fecha:      " + venta.getFechaHora());
            pw.println("----------------------------------------");

            // DATOS CLIENTE
            pw.println("CLIENTE:");
            if (venta.getCliente() != null) {
                pw.println("  Nombre:    " + venta.getCliente().getNombreCompleto());
                pw.println("  Doc:       "
                        + venta.getCliente().getTipoDocumento()
                        + " "
                        + venta.getCliente().getNumeroDocumento());
                pw.println("  Teléfono:  " + venta.getCliente().getTelefonoC());
            } else {
                pw.println("  Cliente:   Consumidor final");
            }

            pw.println("----------------------------------------");

            // DETALLE PRODUCTOS
            pw.println(String.format("%-20s %6s %10s %10s",
                    "Producto", "Cant.", "Precio", "Subtotal"));
            pw.println("----------------------------------------");

            for (DetalleVenta d : venta.getDetalles()) {
                pw.println(String.format("%-20s %6d %10.2f %10.2f",
                        d.getProducto().getNombreProducto(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal()));
            }

            // TOTALES
            pw.println("----------------------------------------");
            pw.println(String.format("%-20s %27.2f",
                    "Subtotal:", venta.getTotal() - venta.getImpuestos()));
            pw.println(String.format("%-20s %27.2f",
                    "IVA:", venta.getImpuestos()));
            pw.println(String.format("%-20s %27.2f",
                    "TOTAL:", venta.getTotal()));
            pw.println("Forma pago: " + venta.getFormaPago());
            pw.println("========================================");
            pw.println("       ¡Gracias por su compra!");
            pw.println("========================================");

            System.out.println("Factura generada: " + archivo);

        } catch (IOException e) {
            System.out.println("Error al generar factura: " + e.getMessage());
        }
    }
}