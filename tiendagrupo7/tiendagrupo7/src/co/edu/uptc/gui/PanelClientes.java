package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelClientes extends PanelBase {

    private JTextField txtCodigo, txtNombre, txtIdentificacion, txtDireccion, txtTelefono;
    private JComboBox<String> cbTipoIdentificacion, cbTipoCliente;
    private JButton btnRegistrar, btnEditar, btnBuscar, btnInactivar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public PanelClientes() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(20, 20));


        JLabel lblTitulo = ConstructorComponentes.crearLabelTitulo("GESTIÓN DE CLIENTES");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);


        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 15));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setPreferredSize(new Dimension(320, 0));

        JPanel panelForm = new JPanel(new GridLayout(14, 1, 0, 5));
        panelForm.setOpaque(false);

        panelForm.add(new JLabel("Código Cliente:")); txtCodigo = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtCodigo);
        panelForm.add(new JLabel("Nombre Completo:")); txtNombre = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtNombre);
        
        panelForm.add(new JLabel("Tipo Identificación:"));
        cbTipoIdentificacion = new JComboBox<>(new String[]{"CC", "NIT", "CE", "PA"});
        cbTipoIdentificacion.setBackground(Color.WHITE);
        panelForm.add(cbTipoIdentificacion);

        panelForm.add(new JLabel("Número Identificación:")); txtIdentificacion = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtIdentificacion);
        panelForm.add(new JLabel("Dirección:")); txtDireccion = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtDireccion);
        panelForm.add(new JLabel("Teléfono:")); txtTelefono = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Tipo Cliente:"));
        cbTipoCliente = new JComboBox<>(new String[]{"MINORISTA", "MAYORISTA"});
        cbTipoCliente.setBackground(Color.WHITE);
        panelForm.add(cbTipoCliente);


        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setOpaque(false);
        btnRegistrar = ConstructorComponentes.crearBotonPrimario("REGISTRAR");
        btnEditar = ConstructorComponentes.crearBotonPrimario("EDITAR");
        btnBuscar = ConstructorComponentes.crearBotonPrimario("BUSCAR");
        btnInactivar = ConstructorComponentes.crearBotonPrimario("INACTIVAR");
        btnInactivar.setBackground(new Color(198, 40, 40));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnInactivar);

        panelIzquierdo.add(panelForm, BorderLayout.NORTH);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);


        String[] columnas = {"Código", "Nombre", "ID", "Teléfono", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tablaClientes);

        this.add(panelIzquierdo, BorderLayout.WEST);
        this.add(scroll, BorderLayout.CENTER);
    }


    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtIdentificacion() { return txtIdentificacion; }
    public JTextField getTxtDireccion() { return txtDireccion; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JComboBox<String> getCbTipoIdentificacion() { return cbTipoIdentificacion; }
    public JComboBox<String> getCbTipoCliente() { return cbTipoCliente; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnInactivar() { return btnInactivar; }
    public JTable getTablaClientes() { return tablaClientes; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}