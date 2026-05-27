package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelReporteMejorCliente extends PanelBase {

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnGenerar;

    public PanelReporteMejorCliente() {

        super();
    }

    @Override
    public void initComponents() {

        this.setLayout(new BorderLayout(10,10));

        // TITULO
        JLabel lblTitulo =
                ConstructorComponentes
                .crearLabelTitulo(
                        "Reporte Mejor Cliente"
                );

        lblTitulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        this.add(lblTitulo, BorderLayout.NORTH);

        // TABLA
        String[] columnas = {

                "Cliente",
                "Cantidad Compras",
                "Total Gastado"
        };

        modeloTabla =
                new DefaultTableModel(columnas,0);

        tablaClientes =
                new JTable(modeloTabla);

        tablaClientes.setRowHeight(25);

        JScrollPane scroll =
                new JScrollPane(tablaClientes);

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

    public JTable getTablaClientes() {

        return tablaClientes;
    }

    public DefaultTableModel getModeloTabla() {

        return modeloTabla;
    }

    public JButton getBtnGenerar() {

        return btnGenerar;
    }
}