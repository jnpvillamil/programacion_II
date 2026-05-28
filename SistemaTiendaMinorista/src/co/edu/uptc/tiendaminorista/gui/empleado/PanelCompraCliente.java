package co.edu.uptc.tiendaminorista.gui.empleado;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.edu.uptc.tiendaminorista.gui.Evento;

public class PanelCompraCliente extends JPanel {
    private JTable tablaCompras;
    private JTable tablaProductos;
    private DefaultTableModel modeloCompras;
    private DefaultTableModel modeloProductos;

    public PanelCompraCliente(Evento e) {

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        

        JLabel titulo = new JLabel("Compra", SwingConstants.CENTER);
        this.add(titulo, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new GridLayout(1, 2, 20, 0));

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        
        JTextField buscarCliente = new JTextField("Buscar Cliente");
        buscarCliente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel lblCompras = new JLabel("Compras seleccionadas");
        lblCompras.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] columnasCompras = {"Producto"};
        modeloCompras = new DefaultTableModel(columnasCompras, 0);
        tablaCompras = new JTable(modeloCompras);
        JScrollPane scrollCompras = new JScrollPane(tablaCompras);
        
        panelIzquierdo.add(buscarCliente);
        panelIzquierdo.add(Box.createVerticalStrut(10)); 
        panelIzquierdo.add(lblCompras);
        panelIzquierdo.add(Box.createVerticalStrut(5));
        panelIzquierdo.add(scrollCompras);
        

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Seleccionar productos"));
        
        JTextField buscarProducto = new JTextField("Buscar");
        buscarProducto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        String[] columnasProductos = {"Productos"};
        modeloProductos = new DefaultTableModel(columnasProductos, 0);
        tablaProductos = new JTable(modeloProductos);
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        
        panelDerecho.add(buscarProducto);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add(scrollProductos);
        

        panelContenido.add(panelIzquierdo);
        panelContenido.add(panelDerecho);
        
        this.add(panelContenido, BorderLayout.CENTER);
    }
}