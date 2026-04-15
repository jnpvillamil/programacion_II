package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelReportes extends JPanel {

    private JButton btnReporteVentas;
    private JButton btnReporteCompras;
    private JButton btnReporteInventario;
    private JButton btnMovimientocontables;
    private JButton btnReporteUtilidad;
    private JButton btnReporteProductos;
    private JButton btnReporteClientes;
    private JButton btnVolver;

    public PanelReportes(ActionListener evento) {

        setLayout(new GridLayout(4, 5, 10, 10));

        btnReporteVentas = new JButton("Reporte Ventas");
        btnReporteCompras = new JButton("Reporte Compras");
        btnReporteInventario = new JButton("Reporte Inventario");
        btnMovimientocontables = new JButton("Reporte Movimientos Contables");
        btnReporteUtilidad = new JButton("Reporte Utilidad");
        btnReporteProductos = new JButton("Reporte Productos");
        btnReporteClientes = new JButton("Reporte Clientes");
        btnVolver = new JButton("Volver");

        btnReporteVentas.setActionCommand(Evento.REPORTE_VENTAS_GENERAL);
        btnReporteCompras.setActionCommand(Evento.REPORTE_COMPRAS_GENERAL);
        btnReporteInventario.setActionCommand(Evento.REPORTE_INVENTARIO);
        btnVolver.setActionCommand(Evento.VOLVER_REPORTES);

        btnReporteVentas.addActionListener(evento);
        btnReporteCompras.addActionListener(evento);
        btnReporteInventario.addActionListener(evento);
        btnVolver.addActionListener(evento);

        add(btnReporteVentas);
        add(btnReporteCompras);
        add(btnReporteInventario);
        add(btnMovimientocontables);
        add(btnReporteUtilidad);
        add(btnReporteProductos);
        add(btnReporteClientes);
        
        add(btnVolver);
    }
}