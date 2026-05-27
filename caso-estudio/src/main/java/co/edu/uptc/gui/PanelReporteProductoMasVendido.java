package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelReporteProductoMasVendido extends PanelBase {

    private JTable tablaReporte;
    private DefaultTableModel modeloTabla;
    private JButton btnGenerar;

    public PanelReporteProductoMasVendido() {

        super();
    }

    @Override
    public void initComponents() {

        this.setLayout(new BorderLayout(10,10));

        // TITULO
        JLabel lblTitulo =
                ConstructorComponentes
                .crearLabelTitulo(
                        "Reporte Producto Más Vendido"
                );

        lblTitulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        this.add(lblTitulo, BorderLayout.NORTH);

        // TABLA
        String[] columnas = {

                "Código",
                "Producto",
                "Cantidad Vendida"
        };

        modeloTabla =
                new DefaultTableModel(columnas,0);

        tablaReporte =
                new JTable(modeloTabla);

        tablaReporte.setRowHeight(25);

        JScrollPane scroll =
                new JScrollPane(tablaReporte);

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

    public JTable getTablaReporte() {

        return tablaReporte;
    }

    public DefaultTableModel getModeloTabla() {

        return modeloTabla;
    }

    public JButton getBtnGenerar() {

        return btnGenerar;
    }
}