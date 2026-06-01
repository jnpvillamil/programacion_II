package co.uptc.edu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.persistencia.ProveedorDAO;

public class GestionProveedores extends JFrame {

    private JTextField txtCodigo, txtRazon, txtNit, txtDireccion, txtTelefono, txtCorreo;
    private JComboBox<String> cbEstado;
    private JTable tabla;
    private DefaultTableModel modelo;
    

    private co.uptc.edu.negocio.GestionProveedores gestion;
    private ProveedorDAO proveedorDAO;

    public GestionProveedores(
    		co.uptc.edu.negocio.GestionProveedores gestion) {

    	this.gestion = gestion;
    	this.proveedorDAO = new ProveedorDAO();

        

        setTitle("Gestión de Proveedores");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15,15));

        add(crearTabla(), BorderLayout.CENTER);
        add(crearPanelSuperior(), BorderLayout.NORTH);
        cargarProveedoresTabla();
    }

    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Datos del Proveedor"));

        JPanel formulario = new JPanel(new GridLayout(3,4,15,10));

        txtCodigo = new JTextField();
        txtRazon = new JTextField();
        txtNit = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();

        cbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});

        formulario.add(new JLabel("Código:"));
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Razón Social:"));
        formulario.add(txtRazon);

        formulario.add(new JLabel("NIT:"));
        formulario.add(txtNit);

        formulario.add(new JLabel("Dirección:"));
        formulario.add(txtDireccion);

        formulario.add(new JLabel("Teléfono:"));
        formulario.add(txtTelefono);

        formulario.add(new JLabel("Correo:"));
        formulario.add(txtCorreo);

        panel.add(formulario, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnInactivar = new JButton("Inactivar");
        JButton btnActivar = new JButton("Activar");

        botones.add(btnActivar);
        botones.add(btnNuevo);
        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnInactivar);
        botones.add(btnActivar);
        btnActivar.setBackground(Color.GREEN);
        btnInactivar.setBackground(Color.RED);

        // REGISTRAR
        btnRegistrar.addActionListener(e -> {

            if(txtCodigo.getText().isEmpty() || txtRazon.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Campos obligatorios vacíos");
                return;
            }

            co.uptc.edu.modelo.Proveedor p = new co.uptc.edu.modelo.Proveedor(
                    txtCodigo.getText(),
                    txtRazon.getText(),
                    txtNit.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    txtCorreo.getText()
            );

            if(proveedorDAO.guardarProveedor(p)){

                modelo.addRow(new Object[]{
                        txtCodigo.getText(),
                        txtRazon.getText(),
                        txtNit.getText(),
                        txtDireccion.getText(),
                        txtTelefono.getText(),
                        txtCorreo.getText(),
                        "Activo"
                });

                JOptionPane.showMessageDialog(this, "Proveedor registrado");
                limpiar();

            } else {
                JOptionPane.showMessageDialog(this, "Proveedor ya existe");
            }
            cargarProveedoresTabla();
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila == -1){
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
                return;
            }

            co.uptc.edu.modelo.Proveedor p = new co.uptc.edu.modelo.Proveedor(
                    txtCodigo.getText(),
                    txtRazon.getText(),
                    txtNit.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    txtCorreo.getText()
            );

            if(proveedorDAO.modificarProveedor(p)){

                modelo.setValueAt(txtRazon.getText(), fila, 1);
                modelo.setValueAt(txtNit.getText(), fila, 2);
                modelo.setValueAt(txtDireccion.getText(), fila, 3);
                modelo.setValueAt(txtTelefono.getText(), fila, 4);
                modelo.setValueAt(txtCorreo.getText(), fila, 5);

                JOptionPane.showMessageDialog(this, "Proveedor modificado");

            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar");
            }
            
            cargarProveedoresTabla();
        });

        //  INACTIVAR
        btnInactivar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila == -1){
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
                return;
            }

            if(proveedorDAO.inactivarProveedor(txtCodigo.getText())){

                modelo.setValueAt("Inactivo", fila, 6);
                JOptionPane.showMessageDialog(this, "Proveedor inactivado");
                
                cargarProveedoresTabla();
            }
            
        });
        
        //ACTIVAR
        btnActivar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila == -1){
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
                return;
            }

            if(proveedorDAO.activarProveedor(txtCodigo.getText())){

                modelo.setValueAt("Activo", fila, 6);

                JOptionPane.showMessageDialog(this, "Proveedor activado");

            } else {
                JOptionPane.showMessageDialog(this, "Error al activar");
            }
            cargarProveedoresTabla();
        });

        btnNuevo.addActionListener(e -> limpiar());

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private JScrollPane crearTabla() {

        String[] columnas = {
                "Código", "Razón Social", "NIT",
                "Dirección", "Teléfono", "Correo", "Estado"
        };

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila >= 0){
                txtCodigo.setText(modelo.getValueAt(fila, 0).toString());
                txtRazon.setText(modelo.getValueAt(fila, 1).toString());
                txtNit.setText(modelo.getValueAt(fila, 2).toString());
                txtDireccion.setText(modelo.getValueAt(fila, 3).toString());
                txtTelefono.setText(modelo.getValueAt(fila, 4).toString());
                txtCorreo.setText(modelo.getValueAt(fila, 5).toString());
            }
        });

        return new JScrollPane(tabla);
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtRazon.setText("");
        txtNit.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }
    
    public void cargarProveedoresTabla(){

        modelo.setRowCount(0);

        for(co.uptc.edu.modelo.Proveedor proveedor :
                proveedorDAO.obtenerProveedores()){

            modelo.addRow(new Object[]{

                    proveedor.getCodigo(),
                    proveedor.getRazonSocial(),
                    proveedor.getNit(),
                    proveedor.getDireccion(),
                    proveedor.getTelefono(),
                    proveedor.getCorreo(),
                    proveedor.isActivo() ? "Activo" : "Inactivo"
            });
        }
    }

    public static void main(String[] args) {
    	  co.uptc.edu.negocio.GestionProveedores gestion =
    	            new co.uptc.edu.negocio.GestionProveedores();
        new GestionProveedores(gestion).setVisible(true);
    }
}