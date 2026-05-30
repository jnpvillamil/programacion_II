package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Producto;
import co.edu.uptc.gui.negocio.GestionProducto;

@SuppressWarnings("serial")
public class VentanaProducto extends JFrame implements ActionListener {
    
    private JTextField campoCodigo, campoNombre, campoPrecio, campoInventario;
    private JButton botonGuardar, botonActualizar, botonLimpiar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    
    // CAPA DE NEGOCIO
    private GestionProducto logicaproducto;

    public VentanaProducto() {
        setTitle("Módulo de Catálogo e Inventario General - UPTC");
        setSize(800, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        this.logicaproducto = new GestionProducto();

        iniciarComponentesFormulario();
        iniciarComponenteTabla();
        cargarDatosTabla();
    }

    private void iniciarComponentesFormulario() {
        JPanel panelForm = new JPanel(new GridLayout(2, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Control de Artículos de Inventario"));

        panelForm.add(new JLabel("Código de Producto:"));
        campoCodigo = new JTextField();
        panelForm.add(campoCodigo);

        panelForm.add(new JLabel("Nombre / Descripción:"));
        campoNombre = new JTextField();
        panelForm.add(campoNombre);

        panelForm.add(new JLabel("Precio Unitario ($):"));
        campoPrecio = new JTextField();
        panelForm.add(campoPrecio);

        panelForm.add(new JLabel("Cantidad Física Stock:"));
        campoInventario = new JTextField();
        panelForm.add(campoInventario);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botonGuardar = new JButton("Ingresar Artículo");
        botonActualizar = new JButton("Modificar Existencias");
        botonLimpiar = new JButton("Limpiar Filtros");

        botonGuardar.addActionListener(this);
        botonActualizar.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelAcciones.add(botonGuardar);
        panelAcciones.add(botonActualizar);
        panelAcciones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(panelForm, BorderLayout.CENTER);
        contenedorSuperior.add(panelAcciones, BorderLayout.SOUTH);

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarComponenteTabla() {
        String[] columnas = {"Código", "Descripción del Artículo", "Precio Público", "Cantidad Disponible (Inventario)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaProductos.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    campoCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                    campoNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                    campoPrecio.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                    campoInventario.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
                    campoCodigo.setEditable(false);
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Listado Maestro de Inventario Reales"));
        add(scrollTabla, BorderLayout.CENTER);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        Conexion conex = new Conexion();
        try {
            Connection c = conex.getConnection();
            if (c != null) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("SELECT codigo, nombre, precio_venta, stock FROM producto");
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("codigo"), 
                        rs.getString("nombre"),
                        rs.getDouble("precio_venta"), 
                        rs.getInt("stock")           
                    };
                    modeloTabla.addRow(fila);
                }
                st.close();
                conex.desconectar();
            }
        } catch (Exception e) {
            System.out.println("Error al cargar tabla productos: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) {
            limpiarCampos();
            return;
        }

        try {

            double precioDigitado = Double.parseDouble(campoPrecio.getText());
            
            Producto producto = new Producto(
                    campoCodigo.getText(), 
                    campoNombre.getText(),
                    precioDigitado,
                    precioDigitado, 
                    Integer.parseInt(campoInventario.getText())
            );

            if (e.getSource() == botonGuardar) {
                logicaproducto.ejecutarOperacionProducto(producto);
            } else if (e.getSource() == botonActualizar) {
                logicaproducto.ejecutarOperacionProducto(producto);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique el formato numérico de precio y cantidades.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        limpiarCampos();
        cargarDatosTabla();
    }

    private void limpiarCampos() {
        campoCodigo.setText(""); campoNombre.setText(""); campoPrecio.setText(""); campoInventario.setText("");
        campoCodigo.setEditable(true);
        tablaProductos.clearSelection();
    }
}