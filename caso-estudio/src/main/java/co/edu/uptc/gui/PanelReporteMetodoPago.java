package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelReporteMetodoPago extends PanelBase {

    private JTable tablaMetodoPago;
    private DefaultTableModel modeloTabla;
    private JButton btnGenerar;

    public PanelReporteMetodoPago() {

        super();
    }

    @Override
    public void initComponents() {

        this.setLayout(new BorderLayout(10,10));

        // TITULO
        JLabel lblTitulo =
                ConstructorComponentes
                .crearLabelTitulo(
                        "Reporte por Método de Pago"
                );

        lblTitulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        this.add(lblTitulo, BorderLayout.NORTH);

        // TABLA
        String[] columnas = {

                "Método de Pago",
                "Cantidad Ventas",
                "Total Recaudado"
        };

        modeloTabla =
                new DefaultTableModel(columnas,0);

        tablaMetodoPago =
                new JTable(modeloTabla);

        tablaMetodoPago.setRowHeight(25);

        JScrollPane scroll =
                new JScrollPane(tablaMetodoPago);

        this.add(scroll, BorderLayout.CENTER);

        // BOTON
        JPanel panelBoton =
                new JPanel();

        panelBoton.setOpaque(false);

        btnGenerar =
                ConstructorComponentes
                .crearBotonPrimario(
                        "GENERAR REPORTE"
                );

        panelBoton.add(btnGenerar);

        this.add(panelBoton, BorderLayout.SOUTH);
    }

    // GETTERS

    public JTable getTablaMetodoPago() {

        return tablaMetodoPago;
    }

    public DefaultTableModel getModeloTabla() {

        return modeloTabla;
    }

    public JButton getBtnGenerar() {

        return btnGenerar;
    }
}
