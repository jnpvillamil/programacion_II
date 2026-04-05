package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCompra extends JPanel {

    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;

    private JButton botonNuevaCompra;
    private JButton botonEditar;
    private JButton botonAnular;
    private JButton botonDetalle;

    private JTextField campoBuscar;
    private JComboBox<String> comboProveedor;

    private JTable tablaCompras;
    private DefaultTableModel modeloTabla;

    private JPanel panelSuperior;
    private JPanel panelBotones;
    private JPanel panelFiltros;
    private JPanel panelInferior;

    public PanelCompra() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
    }

    private void inicializarComponentes() {
        etiquetaTitulo = new JLabel("Gestión de Compras");
        etiquetaTotal = new JLabel("Total de compras: 0");

        botonNuevaCompra = new JButton("Nueva Compra");
        botonEditar = new JButton("Editar");
        botonAnular = new JButton("Anular");
        botonDetalle = new JButton("Ver Detalle");

        campoBuscar = new JTextField(20);

        comboProveedor = new JComboBox<>();
        comboProveedor.addItem("Todos");
        comboProveedor.addItem("Proveedor 1");
        comboProveedor.addItem("Proveedor 2");

        String[] columnas = {
            "Factura Proveedor",
            "Fecha",
            "Proveedor",
            "Subtotal",
            "IVA",
            "Total",
            "Estado"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaCompras = new JTable(modeloTabla);

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

        tablaCompras.setRowHeight(25);
        tablaCompras.getTableHeader().setReorderingAllowed(false);
        tablaCompras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void agregarComponentes() {
        panelBotones.add(botonNuevaCompra);
        panelBotones.add(botonEditar);
        panelBotones.add(botonAnular);
        panelBotones.add(botonDetalle);

        panelFiltros.add(new JLabel("Buscar Factura:"));
        panelFiltros.add(campoBuscar);
        panelFiltros.add(new JLabel("Proveedor:"));
        panelFiltros.add(comboProveedor);

        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setBackground(Color.WHITE);

        panelCabecera.add(etiquetaTitulo, BorderLayout.NORTH);
        panelCabecera.add(panelBotones, BorderLayout.CENTER);
        panelCabecera.add(panelFiltros, BorderLayout.SOUTH);
        
        //Colores botones
        botonNuevaCompra.setBackground(Color.WHITE);
        botonEditar.setBackground(Color.WHITE);
        botonAnular.setBackground(Color.WHITE);
        botonDetalle.setBackground(Color.WHITE);

        panelSuperior.add(panelCabecera, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(tablaCompras);

        panelInferior.add(etiquetaTotal);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
}