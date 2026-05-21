package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import java.awt.*;

public class PanelHome extends JPanel {
    public PanelHome() {
        setLayout(new BorderLayout());
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JLabel titulo = new JLabel("Bienvenido al Sistema de Gestión", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(ConstructorComponentes.COLOR_MENU_OSCURO);

        JLabel subtitulo = new JLabel("Tienda Minorista", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitulo.setForeground(Color.DARK_GRAY);

        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        panelTextos.add(titulo);
        panelTextos.add(subtitulo);

        add(panelTextos, BorderLayout.CENTER);
    }
}