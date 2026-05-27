package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelReporteInventario extends PanelBase {

    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;
    private JButton btnGenerar;

    public PanelReporteInventario() {

        super();
    }

    @Override
    public void initComponents() {

        this.setLayout(new BorderLayout(10,10));

        // TITULO
        JLabel lblTitulo =
                ConstructorComponentes
                .crearLabelTitulo(
                        "Reporte Estado de Inventario"
                );

        lblTitulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        this.add(lblTitulo, BorderLayout.NORTH);

        // TABLA
        String[] columnas = {

                "Código",
                "Producto",
                "Stock Actual",
                "Stock Mínimo",
                "Estado"
        };

        modeloTabla =
                new DefaultTableModel(columnas,0);

        tablaInventario =
                new JTable(modeloTabla);

        tablaInventario.setRowHeight(25);

        JScrollPane scroll =
                new JScrollPane(tablaInventario);

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

    public JTable getTablaInventario() {

        return tablaInventario;
    }

    public DefaultTableModel getModeloTabla() {

        return modeloTabla;
    }

    public JButton getBtnGenerar() {

        return btnGenerar;
    }
}
