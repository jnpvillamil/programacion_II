package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import java.awt.*;

public class PanelCerrarSesion extends PanelBase {

    private JButton btnCerrarSesion;

    public PanelCerrarSesion() {

        super();
    }

    @Override
    public void initComponents() {

        this.setLayout(new BorderLayout(10,10));

        // TITULO
        JLabel lblTitulo =
                ConstructorComponentes
                .crearLabelTitulo(
                        "Cerrar Sesión"
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
                        "¿Desea cerrar la sesión actual?"
                );

        panelCentral.add(lblInfo);

        this.add(panelCentral, BorderLayout.CENTER);

        // BOTON
        JPanel panelBoton =
                new JPanel();

        panelBoton.setOpaque(false);

        btnCerrarSesion =
                ConstructorComponentes
                .crearBotonPrimario(
                        "CERRAR SESIÓN"
                );

        btnCerrarSesion.setBackground(Color.RED);

        panelBoton.add(btnCerrarSesion);

        this.add(panelBoton, BorderLayout.SOUTH);
    }

    // GETTER

    public JButton getBtnCerrarSesion() {

        return btnCerrarSesion;
    }
}
