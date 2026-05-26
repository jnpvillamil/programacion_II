package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelReporteInventario;
import co.edu.uptc.gui.PanelReporteMejorCliente;
import co.edu.uptc.gui.PanelReporteMetodoPago;
import co.edu.uptc.gui.PanelReporteProductoMasVendido;
import co.edu.uptc.persistencia.PersistenciaReportes;

import java.sql.ResultSet;

public class ControladorReportes {

    private PersistenciaReportes persistencia;

    public ControladorReportes() {

        persistencia =
                new PersistenciaReportes();
    }

    // CUS22
    public void cargarReporteProductoMasVendido(
            PanelReporteProductoMasVendido panel) {

        try {

            ResultSet rs =
                    persistencia
                    .reporteProductoMasVendido();

            while(rs.next()) {

                Object[] fila = {

                        rs.getString("nombre"),
                        rs.getInt("stock_actual")
                };

                panel.getModeloTabla()
                        .addRow(fila);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // CUS23
    public void cargarReporteMejorCliente(
            PanelReporteMejorCliente panel) {

        try {

            ResultSet rs =
                    persistencia
                    .reporteMejorCliente();

            while(rs.next()) {

                Object[] fila = {

                        rs.getString("nombre"),
                        rs.getString("correo")
                };

                panel.getModeloTabla()
                        .addRow(fila);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // CUS24
    public void cargarReporteMetodoPago(
            PanelReporteMetodoPago panel) {

        try {

            ResultSet rs =
                    persistencia
                    .reporteMetodoPago();

            while(rs.next()) {

                Object[] fila = {

                        rs.getString("forma_pago"),
                        rs.getDouble("total")
                };

                panel.getModeloTabla()
                        .addRow(fila);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // CUS25
    public void cargarReporteInventario(
            PanelReporteInventario panel) {

        try {

            ResultSet rs =
                    persistencia
                    .reporteInventario();

            while(rs.next()) {

                Object[] fila = {

                        rs.getString("nombre"),
                        rs.getInt("stock_actual")
                };

                panel.getModeloTabla()
                        .addRow(fila);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}