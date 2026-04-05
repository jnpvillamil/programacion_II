package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProducto extends JPanel {
	

    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;
   
    
    private JButton botonNuevo;
    private JButton botonEditar;
    private JButton botonInactivar;
    private JButton botonActualizarPrecio;
    private JButton botonMovimientoInventario;
    
    private JTextField campoBuscar;
    private JComboBox<String> comboCategoria;
    
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    
    
    private JPanel panelSuperior;
    private JPanel panelBotones;
    private JPanel panelFiltros;
    private JPanel panelInferior;
    
    
    public PanelProducto() {
    	inicializarComponentes();
    	configurarPanel();
    	agregarComponentes();
    	
    }
    
    private void inicializarComponentes(){
    	
    	etiquetaTitulo = new JLabel("Gestión de Productos");
    	etiquetaTotal = new JLabel("Total de productos: 0");
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
    	botonNuevo = new JButton("Nuevo");
    	botonEditar = new JButton("Editar");
    	botonInactivar = new JButton("Inactivar");
    	botonActualizarPrecio = new JButton("Actualizar Precio");
    	botonMovimientoInventario = new JButton("Movimiento Inventario");
    	
    	campoBuscar = new JTextField(20);
    	
    	comboCategoria = new JComboBox<>();
    	comboCategoria.addItem("Todos");
    	
    	
    	String[] columnas = {
    			"Codigo",
    			"Nombre",
    			"Categoria",
    			"P.Compra",
    			"P.Venta",
    			"Stock",
    			"Min"
    			
    	};
    	
    	modeloTabla = new DefaultTableModel(columnas,0);
    	tablaProductos = new JTable(modeloTabla);
    	
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
    	
    	tablaProductos.setRowHeight(25);
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	
    	
    }
    private void agregarComponentes(){

    	panelBotones.add(botonNuevo);
    	panelBotones.add(botonEditar);
    	panelBotones.add(botonInactivar);
    	panelBotones.add(botonActualizarPrecio);
    	panelBotones.add(botonMovimientoInventario);
    	

    	panelFiltros.add(new JLabel("Buscar:"));
    	panelFiltros.add(campoBuscar);
    	panelFiltros.add(new JLabel("Categoria:"));
    	panelFiltros.add(comboCategoria);

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
    	botonActualizarPrecio.setBackground(Color.WHITE);
    	botonMovimientoInventario.setBackground(Color.WHITE);
    	 
    	panelSuperior.add(panelCabecera, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(tablaProductos);

        panelInferior.add(etiquetaTotal);

    	add(panelSuperior, BorderLayout.NORTH);
    	add(scroll, BorderLayout.CENTER);
    	add(panelInferior, BorderLayout.SOUTH);
    	}
}