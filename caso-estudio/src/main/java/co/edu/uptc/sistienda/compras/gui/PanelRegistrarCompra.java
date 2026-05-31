package co.edu.uptc.sistienda.compras.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanelRegistrarCompra extends JPanel {

    private JTable tablaCompras;

    private JButton botonGuardar;

    private JLabel lblTitulo;

    public PanelRegistrarCompra() {

        setLayout(new BorderLayout());

        lblTitulo =
                new JLabel("REGISTRAR COMPRA");

        add(lblTitulo, BorderLayout.NORTH);

        tablaCompras = new JTable();

        add(new JScrollPane(tablaCompras),
                BorderLayout.CENTER);

        botonGuardar =
                new JButton("Guardar Compra");

        add(botonGuardar,
                BorderLayout.SOUTH);
    }
}
