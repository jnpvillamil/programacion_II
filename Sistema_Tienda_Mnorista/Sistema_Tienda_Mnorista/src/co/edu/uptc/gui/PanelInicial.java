package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class PanelInicial extends JPanel {

    public PanelInicial(Evento e) {

        setLayout(new BorderLayout());

        JTabbedPane pestanas = new JTabbedPane();

        pestanas.addTab("Cliente", new PanelCliente(e));
        pestanas.addTab("Producto", new PanelProductos());
        pestanas.addTab("Proveedores", new PanelProveedores(e));

        add(pestanas, BorderLayout.CENTER);
    }
}