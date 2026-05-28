package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProveedor extends PanelBase {

    private JTextField txtCodigo, txtRazonSocial, txtNit, txtDireccion, txtTelefono, txtCorreo;
    private JButton btnRegistrar, btnEditar, btnBuscar, btnInactivar;
    private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;

    public PanelProveedor() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(20, 20));

        this.add(ConstructorComponentes.crearLabelTitulo("Proveedores"), BorderLayout.NORTH);

        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 15));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setPreferredSize(new Dimension(320, 0));

        JPanel panelForm = new JPanel(new GridLayout(12, 1, 0, 5));
        panelForm.setOpaque(false);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("Código Proveedor:")); 
        txtCodigo = ConstructorComponentes.crearCampoTexto(); 
        panelForm.add(txtCodigo);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("Razón Social:")); 
        txtRazonSocial = ConstructorComponentes.crearCampoTexto(); 
        panelForm.add(txtRazonSocial);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("NIT:")); 
        txtNit = ConstructorComponentes.crearCampoTexto(); 
        panelForm.add(txtNit);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("Dirección:")); 
        txtDireccion = ConstructorComponentes.crearCampoTexto(); 
        panelForm.add(txtDireccion);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("Teléfono:")); 
        txtTelefono = ConstructorComponentes.crearCampoTexto(); 
        panelForm.add(txtTelefono);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("Correo Electrónico:")); 
        txtCorreo = ConstructorComponentes.crearCampoTexto(); 
        panelForm.add(txtCorreo);

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setOpaque(false);
        btnRegistrar = ConstructorComponentes.crearBotonGuardar("REGISTRAR");
        btnEditar = ConstructorComponentes.crearBotonPrimario("EDITAR");
        btnBuscar = ConstructorComponentes.crearBotonPrimario("BUSCAR");
        btnInactivar = ConstructorComponentes.crearBotonPeligro("INACTIVAR");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnInactivar);

        panelIzquierdo.add(panelForm, BorderLayout.NORTH);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        String[] columnas = {"Código", "Razón Social", "NIT", "Teléfono", "Correo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProveedores = new JTable(modeloTabla);
        tablaProveedores.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tablaProveedores);

        this.add(panelIzquierdo, BorderLayout.WEST);
        this.add(scroll, BorderLayout.CENTER);
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtRazonSocial() { return txtRazonSocial; }
    public JTextField getTxtNit() { return txtNit; }
    public JTextField getTxtDireccion() { return txtDireccion; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JTextField getTxtCorreo() { return txtCorreo; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnInactivar() { return btnInactivar; }
    public JTable getTablaProveedores() { return tablaProveedores; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}