package co.uptc.edu.co.gui;

import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel {

    private static final String TEXTO_TITULO = "Sistema de Gestión Contable y Comercial";
    private static final String TEXTO_BIENVENIDA = "Bienvenido";
    private static final String TEXTO_INSTRUCCION = "Seleccione un modulo en la barra superior para comenzar";

    private JLabel etiquetaTitulo;
    private JLabel etiquetaBienvenida;
    private JLabel etiquetaInstruccion;

    private JPanel panelCentral;

    public PanelInicio() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
    }

    private void inicializarComponentes() {
        etiquetaTitulo = new JLabel(TEXTO_TITULO);
        etiquetaBienvenida = new JLabel(TEXTO_BIENVENIDA);
        etiquetaInstruccion = new JLabel(TEXTO_INSTRUCCION);

        panelCentral = new JPanel();
    }

    private void configurarPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        etiquetaTitulo.setForeground(new Color(20, 45, 75));

        etiquetaBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        etiquetaBienvenida.setForeground(Color.DARK_GRAY);

        etiquetaInstruccion.setFont(new Font("Arial", Font.PLAIN, 18));
        etiquetaInstruccion.setForeground(Color.GRAY);

        panelCentral.setLayout(new GridBagLayout());
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        panelCentral.setPreferredSize(new Dimension(700, 250));
    }

    private void agregarComponentes() {
        GridBagConstraints posicionPanel = new GridBagConstraints();
        posicionPanel.gridx = 0;
        posicionPanel.gridy = 0;
        add(panelCentral, posicionPanel);

        GridBagConstraints posicionTexto = new GridBagConstraints();
        posicionTexto.gridx = 0;
        posicionTexto.insets = new Insets(10, 10, 10, 10);

        posicionTexto.gridy = 0;
        panelCentral.add(etiquetaTitulo, posicionTexto);

        posicionTexto.gridy = 1;
        panelCentral.add(etiquetaBienvenida, posicionTexto);

        posicionTexto.gridy = 2;
        panelCentral.add(etiquetaInstruccion, posicionTexto);
    }
}