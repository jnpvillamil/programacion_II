package co.edu.uptc.tiendaminorista.gui.administrador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import co.edu.uptc.tiendaminorista.modelo.Telefono;
import co.edu.uptc.tiendaminorista.negocio.TelefonoNegocio;

public class TelefonoGUI extends JPanel {
    
    private JTextField txtMarca, txtModelo, txtPrecio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TelefonoNegocio negocio;
    
    public TelefonoGUI() {
        this.negocio = new TelefonoNegocio();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        add(crearPanelFormulario(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
        
        cargarTabla();
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Teléfono Samsung"));
        
        panel.add(new JLabel("Marca:"));
        txtMarca = new JTextField("Samsung");
        txtMarca.setEnabled(false);
        panel.add(txtMarca);
        
        panel.add(new JLabel("Modelo:"));
        txtModelo = new JTextField();
        panel.add(txtModelo);
        
        panel.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panel.add(txtPrecio);
        
        return panel;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = {"Marca", "Modelo", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        tabla.getTableHeader().setReorderingAllowed(false);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnListar = new JButton("Listar");
        JButton btnLimpiar = new JButton("Limpiar");
        
        btnGuardar.addActionListener(e -> guardar());
        btnListar.addActionListener(e -> cargarTabla());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        panel.add(btnGuardar);
        panel.add(btnListar);
        panel.add(btnLimpiar);
        
        return panel;
    }
    
    private void guardar() {
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        
        if (modelo.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Modelo y Precio son obligatorios");
            return;
        }
        
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido");
            return;
        }
        
        Telefono telefono = new Telefono();
        telefono.setMarca(marca);
        telefono.setModelo(modelo);
        telefono.setPrecio(precio);
        
        String resultado = negocio.registrarTelefono(telefono);
        JOptionPane.showMessageDialog(this, resultado);
        
        if (resultado.contains("éxito")) {
            limpiarCampos();
            cargarTabla();
        }
    }
    
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Telefono t : negocio.obtenerTodosLosTelefonos()) {
            modeloTabla.addRow(new Object[]{
                t.getMarca(), 
                t.getModelo(), 
                "$" + String.format("%,.0f", t.getPrecio())
            });
        }
    }
    
    private void limpiarCampos() {
        txtModelo.setText("");
        txtPrecio.setText("");
    }
}