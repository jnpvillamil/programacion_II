package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelVentas extends JPanel {

    private JButton btnRegistrarVenta;
    private JButton btnConsultarVenta;
    private JButton btnAnularVenta;
    private JButton btnDetalleVenta;
    private JButton btnReporteVentas;
    private JButton btnVolver;

    public PanelVentas(ActionListener evento) {

        setLayout(new GridLayout(2, 2, 10, 10));

        btnRegistrarVenta = new JButton("Registrar Venta");
        btnConsultarVenta = new JButton("Consultar Venta");
        btnAnularVenta = new JButton("Anular Venta");
        btnVolver = new JButton("Volver");

        btnRegistrarVenta.setActionCommand(Evento.REGISTRAR_VENTA);
        btnConsultarVenta.setActionCommand(Evento.CONSULTAR_VENTA);
        btnAnularVenta.setActionCommand(Evento.ANULAR_VENTA);
        btnVolver.setActionCommand(Evento.VOLVER_VENTAS);

        btnRegistrarVenta.addActionListener(evento);
        btnConsultarVenta.addActionListener(evento);
        btnAnularVenta.addActionListener(evento);
        btnVolver.addActionListener(evento);

        add(btnRegistrarVenta);
        add(btnConsultarVenta);
        add(btnAnularVenta); 
        add(btnVolver);
    }
}

