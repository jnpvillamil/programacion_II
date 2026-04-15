package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelFacturas extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnVolver;

    public PanelFacturas(java.awt.event.ActionListener evento) {

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Venta");
        modelo.addColumn("Fecha");
        modelo.addColumn("Total");
        modelo.addColumn("Pago");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        btnVolver = new JButton("Volver");
        btnVolver.setActionCommand(Evento.VOLVER_REPORTES);
        btnVolver.addActionListener(evento);

        add(scroll, BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}
