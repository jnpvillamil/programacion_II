package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCliente extends JPanel {
	

    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;
    
    private JButton botonNuevo;
    private JButton botonEditar;
    private JButton botonInactivar;
    private JButton botonHistorial;
    
    private JTextField campoBuscar;
    private JComboBox<String> comboTipo;
    
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    
    
    private JPanel panelSuperior;
    private JPanel panelBotones;
    private JPanel panelFiltros;
    private JPanel panelInferior;
    
    
    public PanelCliente() {
    	inicializarComponentes();
    	configurarPanel();
    	agregarComponentes();
    	
    }
    
    private void inicializarComponentes(){
    	
    	etiquetaTitulo = new JLabel("Gestión de Clientes");
    	etiquetaTotal = new JLabel ("Total de clientes: 0");
    	
    	botonNuevo = new JButton("Nuevo");
    	botonEditar = new JButton("Editar");
    	botonInactivar = new JButton("Inactivar");
    	botonHistorial = new JButton("Ver Historial");
    	
    	campoBuscar = new JTextField(20);
    	
    	comboTipo = new JComboBox<>();
    	comboTipo.addItem("Todos");
    	comboTipo.addItem("Mayorista");
    	comboTipo.addItem("Minorista");
    	
    	String[] columnas = {
    		    "Código",
    		    "Nombre/Razón Social",
    		    "Tipo ID",
    		    "N° Identificación",
    		    "Dirección",
    		    "Teléfono",
    		    "Tipo Cliente",
    		    "Estado"
    		};
    	modeloTabla = new DefaultTableModel(columnas,0);
    	tablaClientes = new JTable(modeloTabla);
    	
    	panelSuperior = new JPanel();
    	panelBotones = new JPanel();
    	panelFiltros = new JPanel();
    	panelInferior = new JPanel();
    	
    }
    private void configurarPanel(){
    	
    	setLayout(new BorderLayout(10,10));
    	setBackground(Color.WHITE);
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
    	etiquetaTitulo.setFont(new Font("Arial",Font.BOLD,22));
    	
    	panelSuperior.setLayout(new BorderLayout());
    	panelSuperior.setBackground(Color.WHITE);
    	
    	panelBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
    	panelBotones.setBackground(Color.WHITE);
    	
    	panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT));
    	panelFiltros.setBackground(Color.WHITE);
    	
    	panelInferior.setLayout(new FlowLayout(FlowLayout.LEFT));
    	panelInferior.setBackground(Color.WHITE);
    	
    	tablaClientes.setRowHeight(25);
        tablaClientes.getTableHeader().setReorderingAllowed(false);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    	
    }
    private void agregarComponentes(){

    	panelBotones.add(botonNuevo);
    	panelBotones.add(botonEditar);
    	panelBotones.add(botonInactivar);
    	panelBotones.add(botonHistorial);

    	panelFiltros.add(new JLabel("Buscar:"));
    	panelFiltros.add(campoBuscar);
    	panelFiltros.add(new JLabel("Tipo:"));
    	panelFiltros.add(comboTipo);

    	JPanel panelCabecera = new JPanel();
    	panelCabecera.setLayout(new BorderLayout());
    	panelCabecera.setBackground(Color.WHITE);

    	panelCabecera.add(etiquetaTitulo, BorderLayout.NORTH);
        panelCabecera.add(panelBotones, BorderLayout.CENTER);
    	panelCabecera.add(panelFiltros, BorderLayout.SOUTH);
    	
    	 // Colores botones
    	 botonNuevo.setBackground(Color.WHITE);
    	 botonEditar.setBackground(Color.WHITE);
    	 botonInactivar.setBackground(Color.WHITE);
    	 botonHistorial.setBackground(Color.WHITE);

    	panelSuperior.add(panelCabecera, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(tablaClientes);

        panelInferior.add(etiquetaTotal);

    	add(panelSuperior, BorderLayout.NORTH);
    	add(scroll, BorderLayout.CENTER);
    	add(panelInferior, BorderLayout.SOUTH);
    	}
}