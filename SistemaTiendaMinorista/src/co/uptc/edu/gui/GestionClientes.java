package co.uptc.edu.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class GestionClientes extends JFrame {

    
    private JTextField txtCodigo, txtNombre, txtNumero, txtDireccion, txtTelefono;
    private JComboBox<String> cbTipoId, cbTipoCliente, cbEstado;
    private JTable tabla;
    private DefaultTableModel modelo;

   
    private co.uptc.edu.negocio.GestionClientes gestion;

    public GestionClientes() {

        gestion = new co.uptc.edu.negocio.GestionClientes();

        setTitle("Gestión de Clientes");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15,15));

        add(crearTabla(), BorderLayout.CENTER);
        add(crearPanelSuperior(), BorderLayout.NORTH);
        
        add(crearPanelHistorial(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
    	
    	

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Datos del Cliente"));

        JPanel formulario = new JPanel(new GridLayout(4,4,15,10));

        // 🔥 INICIALIZAR CAMPOS
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtNumero = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();

        cbTipoId = new JComboBox<>(new String[]{"CC", "NIT", "CE", "PA"});
        cbTipoCliente = new JComboBox<>(new String[]{"Minorista", "Mayorista"});
        cbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});

        formulario.add(new JLabel("Código Único:"));
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Nombre / Razón Social:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Tipo Identificación:"));
        formulario.add(cbTipoId);

        formulario.add(new JLabel("Número Identificación:"));
        formulario.add(txtNumero);

        formulario.add(new JLabel("Dirección:"));
        formulario.add(txtDireccion);

        formulario.add(new JLabel("Teléfono:"));
        formulario.add(txtTelefono);

        formulario.add(new JLabel("Tipo Cliente:"));
        formulario.add(cbTipoCliente);

        formulario.add(new JLabel("Estado:"));
        formulario.add(cbEstado);

        panel.add(formulario, BorderLayout.CENTER);

        // 🔥 BOTONES
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnInactivar = new JButton("Inactivar");
        JButton btnHistorial = new JButton("Consultar Historial");

        botones.add(btnNuevo);
        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnInactivar);
        botones.add(btnHistorial);

        // 🔥 EVENTO REGISTRAR
        btnRegistrar.addActionListener(e -> {

            if(txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Debe llenar los campos obligatorios");
                return;
            }

            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();

            co.uptc.edu.modelo.Cliente cliente = new co.uptc.edu.modelo.Cliente(
                    codigo,
                    nombre,
                    cbTipoId.getSelectedItem().toString(),
                    txtNumero.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    cbTipoCliente.getSelectedItem().toString()
            );

            boolean resultado = gestion.registrarCliente(cliente);

            if(resultado){
                modelo.addRow(new Object[]{
                        codigo,
                        nombre,
                        cbTipoId.getSelectedItem(),
                        txtNumero.getText(),
                        txtDireccion.getText(),
                        txtTelefono.getText(),
                        cbTipoCliente.getSelectedItem(),
                        "Activo"
                });

                JOptionPane.showMessageDialog(this, "Cliente registrado");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "El cliente ya existe");
            }
        });

        btnNuevo.addActionListener(e -> limpiarCampos());
        
        btnModificar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila == -1){
                JOptionPane.showMessageDialog(this, "Seleccione un cliente");
                return;
            }

            co.uptc.edu.modelo.Cliente cliente = new co.uptc.edu.modelo.Cliente(
                    txtCodigo.getText(),
                    txtNombre.getText(),
                    cbTipoId.getSelectedItem().toString(),
                    txtNumero.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    cbTipoCliente.getSelectedItem().toString()
            );

            boolean resultado = gestion.modificarCliente(cliente);

            if(resultado){

                modelo.setValueAt(txtNombre.getText(), fila, 1);
                modelo.setValueAt(cbTipoId.getSelectedItem(), fila, 2);
                modelo.setValueAt(txtNumero.getText(), fila, 3);
                modelo.setValueAt(txtDireccion.getText(), fila, 4);
                modelo.setValueAt(txtTelefono.getText(), fila, 5);
                modelo.setValueAt(cbTipoCliente.getSelectedItem(), fila, 6);

                JOptionPane.showMessageDialog(this, "Cliente modificado");

            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar");
            }
        });
        btnInactivar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila == -1){
                JOptionPane.showMessageDialog(this, "Seleccione un cliente");
                return;
            }

            String codigo = txtCodigo.getText();

            boolean resultado = gestion.inactivarCliente(codigo);

            if(resultado){

                modelo.setValueAt("Inactivo", fila, 7);

                JOptionPane.showMessageDialog(this, "Cliente inactivado");

            } else {
                JOptionPane.showMessageDialog(this, "Error al inactivar");
            }
        });

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
        
        
    }
    
  

    private JScrollPane crearTabla() {
    	
    	

        String[] columnas = {
                "Código", "Nombre",
                "Tipo ID", "Número ID",
                "Dirección", "Teléfono",
                "Tipo Cliente", "Estado"
        };

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila >= 0){

                txtCodigo.setText(modelo.getValueAt(fila, 0).toString());
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                cbTipoId.setSelectedItem(modelo.getValueAt(fila, 2).toString());
                txtNumero.setText(modelo.getValueAt(fila, 3).toString());
                txtDireccion.setText(modelo.getValueAt(fila, 4).toString());
                txtTelefono.setText(modelo.getValueAt(fila, 5).toString());
                cbTipoCliente.setSelectedItem(modelo.getValueAt(fila, 6).toString());
                cbEstado.setSelectedItem(modelo.getValueAt(fila, 7).toString());
            }
        });

        return new JScrollPane(tabla);
    }

    private JPanel crearPanelHistorial() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Historial de Compras del Cliente"));

        JTextArea historial = new JTextArea(4, 50);
        historial.setEditable(false);

        panel.add(new JScrollPane(historial), BorderLayout.CENTER);

        return panel;
    }

    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtNumero.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        cbTipoId.setSelectedIndex(0);
        cbTipoCliente.setSelectedIndex(0);
        cbEstado.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new GestionClientes().setVisible(true);
    }
}