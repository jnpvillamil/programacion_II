package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class PanelReporte extends JPanel {
    public PanelReporte() {
        setLayout(new BorderLayout());
        add(new JLabel("Módulo de Reportes - Vista de Utilidad", SwingConstants.CENTER), BorderLayout.CENTER);
        JTextArea t = new JTextArea("Ventas totales: $X\nCompras totales: $Y\nUtilidad: $Z");
        add(new JScrollPane(t), BorderLayout.SOUTH);
    }
}