package co.uptc.edu.co.gui;

import javax.swing.*;
import java.awt.*;

import co.uptc.edu.co.config.TiendaConfig;

public class VentanaPrincipal extends JFrame {

    private JPanel panelEncabezado;
    private JPanel panelContenido;

    private JButton botonProductos;
    private JButton botonClientes;
    private JButton botonProveedores;
    private JButton botonVentas;
    private JButton botonCompras;
    private JButton botonContabilidad;
    private JButton botonReportes;
    private JButton botonConsultas;

    private Evento evento;
    private TiendaConfig config;

    private PanelProducto panelProducto;
    private PanelCliente panelCliente;
    private PanelProveedor panelProveedor;
    private PanelVenta panelVenta;
    private PanelCompra panelCompra;
    private PanelContabilidad panelContabilidad;
    private PanelReportes panelReportes;
    private PanelConsultas panelConsultas;
    private PanelInicio panelInicio;

    public VentanaPrincipal() {
    	config = new TiendaConfig();
        inicializarComponentes();
        configurarVentana();
        agregarComponentes();
        inicializarEventos();
        mostrarPanel(panelInicio);
    }

    private void inicializarComponentes() {
        panelEncabezado = new JPanel();
        panelContenido = new JPanel();

        botonProductos = new JButton("Productos");
        botonClientes = new JButton("Clientes");
        botonProveedores = new JButton("Proveedores");
        botonVentas = new JButton("Ventas");
        botonCompras = new JButton("Compras");
        botonContabilidad = new JButton("Contabilidad");
        botonReportes = new JButton("Reportes");
        botonConsultas = new JButton("Consultas del Sistema");

        panelProducto = new PanelProducto();
        panelCliente = new PanelCliente();
        panelProveedor = new PanelProveedor();
        panelVenta = new PanelVenta();
        panelCompra = new PanelCompra();
        panelContabilidad = new PanelContabilidad();
        panelReportes = new PanelReportes();
        panelConsultas = new PanelConsultas();
        panelInicio = new PanelInicio();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestion Contable y Comercial");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void agregarComponentes() {
        panelEncabezado.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelEncabezado.setBackground(new Color(20, 45, 75));
        botonProductos.setForeground(Color.WHITE);
        botonProductos.setBackground(new Color(30, 60, 100));
        panelEncabezado.add(botonProductos);

        botonClientes.setForeground(Color.WHITE);
        botonClientes.setBackground(new Color(30, 60, 100));
        panelEncabezado.add(botonClientes);

        botonProveedores.setForeground(Color.WHITE);
        botonProveedores.setBackground(new Color(30, 60, 100));
        panelEncabezado.add(botonProveedores);

        botonVentas.setForeground(Color.WHITE);
        botonVentas.setBackground(new Color(30, 60, 100));
        panelEncabezado.add(botonVentas);

        botonCompras.setForeground(Color.WHITE);
        botonCompras.setBackground(new Color(30, 60, 100));
        panelEncabezado.add(botonCompras);

        botonContabilidad.setForeground(Color.WHITE);
        botonContabilidad.setBackground(new Color(30, 60, 100));
        panelEncabezado.add(botonContabilidad);

        botonReportes.setForeground(Color.WHITE);
        botonReportes.setBackground(new Color(30, 60, 100));
        panelEncabezado.add(botonReportes);

        botonConsultas.setForeground(Color.WHITE);
        botonConsultas.setBackground(new Color(30, 60, 100));
        panelEncabezado.add(botonConsultas);
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setLayout(new BorderLayout());

        add(panelEncabezado, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);
    }

    private void inicializarEventos() {
        evento = new Evento(this,config);

        botonProductos.setActionCommand(Evento.PRODUCTOS);
        botonProductos.addActionListener(evento);

        botonClientes.setActionCommand(Evento.CLIENTES);
        botonClientes.addActionListener(evento);

        botonProveedores.setActionCommand(Evento.PROVEEDORES);
        botonProveedores.addActionListener(evento);

        botonVentas.setActionCommand(Evento.VENTAS);
        botonVentas.addActionListener(evento);

        botonCompras.setActionCommand(Evento.COMPRAS);
        botonCompras.addActionListener(evento);

        botonContabilidad.setActionCommand(Evento.CONTABILIDAD);
        botonContabilidad.addActionListener(evento);

        botonReportes.setActionCommand(Evento.REPORTES);
        botonReportes.addActionListener(evento);

        botonConsultas.setActionCommand(Evento.CONSULTAS);
        botonConsultas.addActionListener(evento);

        panelProducto.inicializarEventos(evento);
        panelCliente.inicializarEventos(evento);
        panelProveedor.inicializarEventos(evento);
        panelVenta.inicializarEventos(evento);
        panelCompra.inicializarEventos(evento);
        panelContabilidad.inicializarEventos(evento);
        panelReportes.inicializarEventos(evento);
        panelConsultas.inicializarEventos(evento);
        
    }

    public void mostrarPanel(JPanel panel) {
        panelContenido.removeAll();
        panelContenido.add(panel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    public void irProductos() {
        mostrarPanel(panelProducto);
    }

    public void irClientes() {
        mostrarPanel(panelCliente);
    }

    public void irProveedores() {
        mostrarPanel(panelProveedor);
    }

    public void irVentas() {
        mostrarPanel(panelVenta);
    }

    public void irCompras() {
        mostrarPanel(panelCompra);
    }

    public void irContabilidad() {
        mostrarPanel(panelContabilidad);
    }

    public void irReportes() {
        mostrarPanel(panelReportes);
    }

    public void irConsultas() {
        mostrarPanel(panelConsultas);
    }

    public PanelProducto getPanelProducto() {
        return panelProducto;
    }
    
    public PanelCliente getPanelCliente() {
        return panelCliente;
    }

    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }
}