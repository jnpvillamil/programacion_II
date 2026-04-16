package co.edu.uptc.gui;

import javax.swing.*;

public class PanelConsultas extends JPanel {

    JTextField txtFechaInicio;
    JTextField txtFechaFin;
    JTable tablaResultados;
    JButton btnBuscar;

    public PanelConsultas(){

        txtFechaInicio = new JTextField(10);
        txtFechaFin = new JTextField(10);
        tablaResultados = new JTable();
        btnBuscar = new JButton("Buscar");

        add(new JLabel("Fecha Inicio"));
        add(txtFechaInicio);

        add(new JLabel("Fecha Fin"));
        add(txtFechaFin);

        add(new JScrollPane(tablaResultados));

        add(btnBuscar);
    }

    public void consultarVentas(){}

    public void consultarCompras(){}
}