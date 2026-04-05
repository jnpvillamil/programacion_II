package co.uptc.edu.co.gui;

import javax.swing.*;
import java.awt.*;

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
   
   
   public VentanaPrincipal() {
	   inicializarComponentes();
	   configurarVentana();
	   agregarComponentes();
	   inicializarEventos();
	   mostrarPanel(new PanelInicio());
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
   }
   
   private void configurarVentana(){
	   
	   setTitle("Sistema de Gestion Contable Y Comercial");
	   setSize(1200,700);
	   setLocationRelativeTo(null);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setLayout(new BorderLayout());
   }
   
   private void agregarComponentes(){
	   
	   panelEncabezado.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
	   panelEncabezado.setBackground(new Color(20,45,75));
	   
	   // Color de letra botones
	   botonProductos.setForeground(Color.WHITE);
	   botonClientes.setForeground(Color.WHITE);
	   botonProveedores.setForeground(Color.WHITE);
	   botonVentas.setForeground(Color.WHITE);
	   botonCompras.setForeground(Color.WHITE);
	   botonContabilidad.setForeground(Color.WHITE);
	   botonReportes.setForeground(Color.WHITE);
	   botonConsultas.setForeground(Color.WHITE);
	   
	   // Color del relleno del boton
	   botonProductos.setBackground(new Color(30,60,100));
	   botonClientes.setBackground(new Color(30,60,100));
	   botonProveedores.setBackground(new Color(30,60,100));
	   botonVentas.setBackground(new Color(30,60,100));
	   botonCompras.setBackground(new Color(30,60,100));
	   botonContabilidad.setBackground(new Color(30,60,100));
	   botonReportes.setBackground(new Color(30,60,100));
	   botonConsultas.setBackground(new Color(30,60,100));
	   
	   // Agregar botones al panel 
	   panelEncabezado.add(botonProductos);
	   panelEncabezado.add(botonClientes);
	   panelEncabezado.add(botonProveedores);
	   panelEncabezado.add(botonVentas);
	   panelEncabezado.add(botonCompras);
	   panelEncabezado.add(botonContabilidad);
	   panelEncabezado.add(botonReportes);
	   panelEncabezado.add(botonConsultas);
	   
	   panelContenido.setBackground(Color.WHITE);
	   panelContenido.setLayout(new BorderLayout());
	   
	   add(panelEncabezado,BorderLayout.NORTH);
	   add(panelContenido,BorderLayout.CENTER);
   }
   
   private void inicializarEventos(){
	   
	   Evento evento = new Evento(this);
	   
	   botonProductos.setActionCommand("Productos");
	   botonProductos.addActionListener(evento);
	   
	   botonClientes.setActionCommand("Clientes");
	   botonClientes.addActionListener(evento);
	   
	   botonProveedores.setActionCommand("Proveedores");
	   botonProveedores.addActionListener(evento);
	   
	   botonVentas.setActionCommand("Ventas");
	   botonVentas.addActionListener(evento);
	   
	   botonCompras.setActionCommand("Compras");
	   botonCompras.addActionListener(evento);
	   
	   botonContabilidad.setActionCommand("Contabilidad");
	   botonContabilidad.addActionListener(evento);
	   
	   botonReportes.setActionCommand("Reportes");
	   botonReportes.addActionListener(evento);
	   
	   botonConsultas.setActionCommand("Consultas");
	   botonConsultas.addActionListener(evento);
   }
   
   // metodo cambio de pantalla
   public void mostrarPanel(JPanel panel){
	   panelContenido.removeAll();
	   panelContenido.add(panel, BorderLayout.CENTER);
	   panelContenido.revalidate();
	   panelContenido.repaint(); 
   }
   
   public static void main(String[] args){
	   VentanaPrincipal ventana = new VentanaPrincipal();
	   ventana.setVisible(true);
   }
}