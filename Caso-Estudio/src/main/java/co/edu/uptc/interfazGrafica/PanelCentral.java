package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public abstract class PanelCentral extends JPanel {
    
    protected JTable tabla;
    protected DefaultTableModel modelo;
    protected Evento evento;
    protected JTextField txtBuscar;
    
    protected JButton btnNuevo;
    protected JButton btnModificar;
    protected JButton btnEliminar;
    protected JButton btnLimpiar;
    protected JButton btnBuscar;
    
    public PanelCentral(Evento evento, String titulo) {
        this.evento = evento;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
       
        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelNorte.add(lblTitulo, BorderLayout.WEST);
        
       
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtBuscar = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setActionCommand(getComandoBuscar());
        btnBuscar.addActionListener(evento);
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelNorte.add(panelBusqueda, BorderLayout.EAST);
        
        add(panelNorte, BorderLayout.NORTH);
        
       
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        
       
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));
        
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setActionCommand(getComandoNuevo());
        btnNuevo.addActionListener(evento);
        
        btnModificar = new JButton("Modificar");
        btnModificar.setActionCommand(getComandoActualizar());
        btnModificar.addActionListener(evento);
        
        btnEliminar = new JButton("Inactivar");
        btnEliminar.setActionCommand(getComandoEliminar());
        btnEliminar.addActionListener(evento);
        
        btnLimpiar = new JButton("Limpiar Tabla");
        btnLimpiar.setActionCommand(getComandoLimpiar());
        btnLimpiar.addActionListener(evento);
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        panelCentro.add(panelBotones, BorderLayout.NORTH);
        
      
        configurarTabla();
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        
        add(panelCentro, BorderLayout.CENTER);
        
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccion = tabla.getSelectedRow() != -1;
            btnModificar.setEnabled(seleccion);
            btnEliminar.setEnabled(seleccion);
        });
        
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
    
    protected abstract void configurarTabla();
    public abstract void poblarTabla();
    
    protected abstract String getComandoNuevo();
    protected abstract String getComandoActualizar();
    protected abstract String getComandoEliminar();
    protected abstract String getComandoBuscar();
    protected abstract String getComandoLimpiar();
    
    public String getCodigoSeleccionado() {
        int fila = tabla.getSelectedRow();
        if(fila != -1) {
            return tabla.getValueAt(fila, 0).toString();
        }
        return null;
    }
    
    public void limpiarTabla() {
        modelo.setRowCount(0);
    }
    
    public String getTextoBuscar() {
        return txtBuscar.getText().trim();
    }
    
    public void limpiarBusqueda() {
        txtBuscar.setText("");
    }
    
    public void realizarBusqueda() {
        poblarTabla();
    }
}