package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelVenta extends JPanel {

    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;

    private JButton botonNuevaVenta;
    private JButton botonAnular;
    private JButton botonDetalle;

    private JTextField campoBuscar;

    private JComboBox<String> comboCliente;
    private JComboBox<String> comboPago;

    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;

    private JPanel panelSuperior;
    private JPanel panelBotones;
    private JPanel panelFiltros;
    private JPanel panelInferior;

    public PanelVenta() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
    }

    private void inicializarComponentes() {

        etiquetaTitulo = new JLabel("Gestión de Ventas");
        etiquetaTotal = new JLabel("Total de ventas: 0");

        botonNuevaVenta = new JButton("Nueva Venta");
        botonAnular = new JButton("Anular");
        botonDetalle = new JButton("Ver Detalle");

        campoBuscar = new JTextField(20);

        comboCliente = new JComboBox<>();
        comboCliente.addItem("Todos");
        comboCliente.addItem("Cliente 1");
        comboCliente.addItem("Cliente 2");

        comboPago = new JComboBox<>();
        comboPago.addItem("Todos");
        comboPago.addItem("Efectivo");
        comboPago.addItem("Transferencia");
        comboPago.addItem("Tarjeta");
        comboPago.addItem("Crédito");

        String[] columnas = {
                "Factura",
                "Fecha",
                "Hora",
                "Cliente",
                "Forma de Pago",
                "IVA",
                "Total",
                "Estado"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaVentas = new JTable(modeloTabla);

        panelSuperior = new JPanel();
        panelBotones = new JPanel();
        panelFiltros = new JPanel();
        panelInferior = new JPanel();
    }

    private void configurarPanel() {

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);

        panelBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBotones.setBackground(Color.WHITE);

        panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(Color.WHITE);

        panelInferior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(Color.WHITE);
        
        tablaVentas.setRowHeight(25);
        tablaVentas.getTableHeader().setReorderingAllowed(false);
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    }

    private void agregarComponentes() {

        panelBotones.add(botonNuevaVenta);
        panelBotones.add(botonAnular);
        panelBotones.add(botonDetalle);

        panelFiltros.add(new JLabel("Buscar Factura:"));
        panelFiltros.add(campoBuscar);
        panelFiltros.add(new JLabel("Cliente:"));
        panelFiltros.add(comboCliente);
        panelFiltros.add(new JLabel("Pago:"));
        panelFiltros.add(comboPago);

        JPanel panelCabecera = new JPanel();
        panelCabecera.setLayout(new BorderLayout());
        panelCabecera.setBackground(Color.WHITE);

        panelCabecera.add(etiquetaTitulo, BorderLayout.NORTH);
        panelCabecera.add(panelBotones, BorderLayout.CENTER);
        panelCabecera.add(panelFiltros, BorderLayout.SOUTH);
        
        //Colores Botones
        botonNuevaVenta.setBackground(Color.WHITE);
        botonAnular.setBackground(Color.WHITE);
        botonDetalle.setBackground(Color.WHITE);

        panelSuperior.add(panelCabecera, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(tablaVentas);

        panelInferior.add(etiquetaTotal);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
}