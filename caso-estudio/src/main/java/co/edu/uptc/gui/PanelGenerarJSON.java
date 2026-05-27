package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import java.awt.*;

public class PanelGenerarJSON extends PanelBase {

    private JButton btnGenerarJSON;

    public PanelGenerarJSON() {

        super();
    }

    @Override
    public void initComponents() {

        this.setLayout(new BorderLayout(10,10));

        // TITULO
        JLabel lblTitulo =
                ConstructorComponentes
                .crearLabelTitulo(
                        "Generar Archivo JSON"
                );

        lblTitulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        this.add(lblTitulo, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel panelCentral =
                new JPanel();

        panelCentral.setOpaque(false);

        JLabel lblInfo =
                new JLabel(
                        "Exportar información del sistema a JSON"
                );

        panelCentral.add(lblInfo);

        this.add(panelCentral, BorderLayout.CENTER);

        // BOTON
        JPanel panelBoton =
                new JPanel();

        panelBoton.setOpaque(false);

        btnGenerarJSON =
                ConstructorComponentes
                .crearBotonPrimario(
                        "GENERAR JSON"
                );

        panelBoton.add(btnGenerarJSON);

        this.add(panelBoton, BorderLayout.SOUTH);
    }

    // GETTER

    public JButton getBtnGenerarJSON() {

        return btnGenerarJSON;
    }
}