package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProveedor extends JPanel {
	

    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;
    
    private JButton botonNuevo;
    private JButton botonEditar;
    private JButton botonInactivar;
    private JButton botonRegistrarCompra;
    
    private JTextField campoBuscar;
   
    
    private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;
    
    
    private JPanel panelSuperior;
    private JPanel panelBotones;
    private JPanel panelFiltros;
    private JPanel panelInferior;
    
    
    public PanelProveedor() {
    	inicializarComponentes();
    	configurarPanel();
    	agregarComponentes();
    	
    }
    
    private void inicializarComponentes(){
    	
    	etiquetaTitulo = new JLabel("Gestión de Proveedores");
    	etiquetaTotal = new JLabel("Total de proveedores: 0");
    	
    	botonNuevo = new JButton("Nuevo");
    	botonEditar = new JButton("Editar");
    	botonInactivar = new JButton("Inactivar");
    	botonRegistrarCompra = new JButton("Registrar Compra");
    	
    	campoBuscar = new JTextField(20);
    	
    	String[] columnas = {
    		    "Código",
    		    "Razón Social",
    		    "NIT",
    		    "Dirección",
    		    "Teléfono",
    		    "Correo Electrónico",
    		    "Estado"
    		};
    	
    	modeloTabla = new DefaultTableModel(columnas,0);
    	tablaProveedores = new JTable(modeloTabla);
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
    	panelSuperior = new JPanel();
    	panelBotones = new JPanel();
    	panelFiltros = new JPanel();
    	panelInferior = new JPanel();
    	
    }
    private void configurarPanel(){
    	
    	setLayout(new BorderLayout(10,10));
    	setBackground(Color.WHITE);
    	
    	etiquetaTitulo.setFont(new Font("Arial",Font.BOLD,22));
    	
    	panelSuperior.setLayout(new BorderLayout());
    	panelSuperior.setBackground(Color.WHITE);
    	
    	panelBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
    	panelBotones.setBackground(Color.WHITE);
    	
    	panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT));
    	panelFiltros.setBackground(Color.WHITE);
    	
    	panelInferior.setLayout(new FlowLayout(FlowLayout.LEFT));
    	panelInferior.setBackground(Color.WHITE);
    	
    	tablaProveedores.setRowHeight(25);
        tablaProveedores.getTableHeader().setReorderingAllowed(false);
        tablaProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	
    }
    private void agregarComponentes(){

    	panelBotones.add(botonNuevo);
    	panelBotones.add(botonEditar);
    	panelBotones.add(botonInactivar);
    	panelBotones.add(botonRegistrarCompra);

    	panelFiltros.add(new JLabel("Buscar:"));
    	panelFiltros.add(campoBuscar);
    

    	JPanel panelCabecera = new JPanel();
    	panelCabecera.setLayout(new BorderLayout());
    	panelCabecera.setBackground(Color.WHITE);

    	panelCabecera.add(etiquetaTitulo, BorderLayout.NORTH);
        panelCabecera.add(panelBotones, BorderLayout.CENTER);
    	panelCabecera.add(panelFiltros, BorderLayout.SOUTH);
    	
    	//Colores Botones
    	botonNuevo.setBackground(Color.WHITE);
    	botonEditar.setBackground(Color.WHITE);
        botonInactivar.setBackground(Color.WHITE);
        botonRegistrarCompra.setBackground(Color.WHITE);
    	 
    	panelSuperior.add(panelCabecera, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(tablaProveedores);

        panelInferior.add(etiquetaTotal);

    	add(panelSuperior, BorderLayout.NORTH);
    	add(scroll, BorderLayout.CENTER);
    	add(panelInferior, BorderLayout.SOUTH);
    	}
}