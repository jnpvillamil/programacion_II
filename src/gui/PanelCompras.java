package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelCompras extends JPanel {

    private JButton btnRegistrarCompra;
    private JButton btnConsultarCompra;
    private JButton btnAnularCompra;
    private JButton btnDetalleCompra;
    private JButton btnReporteCompras;
    private JButton btnVolver;

    public PanelCompras(ActionListener evento) {

        setLayout(new GridLayout(2, 2, 10, 10));

        btnRegistrarCompra = new JButton("Registrar Compra");
        btnConsultarCompra = new JButton("Consultar Compra");
        btnAnularCompra = new JButton("Anular Compra");
        btnVolver = new JButton("Volver");

        btnRegistrarCompra.setActionCommand(Evento.REGISTRAR_COMPRA);
        btnConsultarCompra.setActionCommand(Evento.CONSULTAR_COMPRA);
        btnAnularCompra.setActionCommand(Evento.ANULAR_COMPRA);
        btnVolver.setActionCommand(Evento.VOLVER_COMPRAS);

        btnRegistrarCompra.addActionListener(evento);
        btnConsultarCompra.addActionListener(evento);
        btnAnularCompra.addActionListener(evento);
        btnVolver.addActionListener(evento);

        add(btnRegistrarCompra);
        add(btnConsultarCompra);
        add(btnAnularCompra);
        add(btnVolver);
    }
}