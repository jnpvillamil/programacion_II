package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelContabilidad extends JPanel {

    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;

    private JButton botonNuevoMovimiento;
    private JButton botonVerDetalle;
    private JButton botonFiltrar;

    private JTextField campoBuscar;

    private JComboBox<String> comboTipoMovimiento;
    private JComboBox<String> comboCuenta;

    private JTable tablaContabilidad;
    private DefaultTableModel modeloTabla;

    private JPanel panelSuperior;
    private JPanel panelBotones;
    private JPanel panelFiltros;
    private JPanel panelInferior;

    public PanelContabilidad() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
    }

    private void inicializarComponentes() {

        etiquetaTitulo = new JLabel("Gestión Contable");
        etiquetaTotal = new JLabel("Total de movimientos: 0");

        botonNuevoMovimiento = new JButton("Nuevo Movimiento");
        botonVerDetalle = new JButton("Ver Detalle");
        botonFiltrar = new JButton("Filtrar");

        campoBuscar = new JTextField(20);

        comboTipoMovimiento = new JComboBox<>();
        comboTipoMovimiento.addItem("Todos");
        comboTipoMovimiento.addItem("Ingreso");
        comboTipoMovimiento.addItem("Egreso");

        comboCuenta = new JComboBox<>();
        comboCuenta.addItem("Todas");
        comboCuenta.addItem("Caja");
        comboCuenta.addItem("Bancos");
        comboCuenta.addItem("Inventario");
        comboCuenta.addItem("Ingresos por Ventas");
        comboCuenta.addItem("IVA Generado");
        comboCuenta.addItem("IVA Descontable");
        comboCuenta.addItem("Proveedores");

        String[] columnas = {
                "Código Transacción",
                "Fecha",
                "Tipo Movimiento",
                "Cuenta Contable",
                "Valor",
                "Descripción"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaContabilidad = new JTable(modeloTabla);

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
        
        tablaContabilidad.setRowHeight(25);
        tablaContabilidad.getTableHeader().setReorderingAllowed(false);
        tablaContabilidad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void agregarComponentes() {

        panelBotones.add(botonNuevoMovimiento);
        panelBotones.add(botonVerDetalle);
        panelBotones.add(botonFiltrar);

        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(campoBuscar);
        panelFiltros.add(new JLabel("Tipo:"));
        panelFiltros.add(comboTipoMovimiento);
        panelFiltros.add(new JLabel("Cuenta:"));
        panelFiltros.add(comboCuenta);

        JPanel panelCabecera = new JPanel();
        panelCabecera.setLayout(new BorderLayout());
        panelCabecera.setBackground(Color.WHITE);

        panelCabecera.add(etiquetaTitulo, BorderLayout.NORTH);
        panelCabecera.add(panelBotones, BorderLayout.CENTER);
        panelCabecera.add(panelFiltros, BorderLayout.SOUTH);
        
        //Colores Botones
        botonNuevoMovimiento.setBackground(Color.WHITE);
        botonVerDetalle.setBackground(Color.WHITE);
        botonFiltrar.setBackground(Color.WHITE);

        panelSuperior.add(panelCabecera, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(tablaContabilidad);

        panelInferior.add(etiquetaTotal);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
}