package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class PanelComprasPro extends JPanel {

    public PanelComprasPro(Evento e) {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Compras a Proveedores", JLabel.CENTER);
        add(titulo, BorderLayout.NORTH);

        JTextArea area = new JTextArea("Aquí irán las compras");
        area.setEditable(false);

        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton volver = new JButton("Volver");
        volver.addActionListener(e);
        volver.setActionCommand(Evento.CANCELAR);

        add(volver, BorderLayout.SOUTH);
    }
}