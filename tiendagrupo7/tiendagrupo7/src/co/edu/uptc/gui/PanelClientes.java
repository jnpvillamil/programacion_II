package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelClientes extends PanelBase {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JComboBox<String> cbTipoIdentificacion;
    private JComboBox<String> cbTipoCliente;
    
    private JButton btnRegistrar;
    private JButton btnInactivar;
    
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public PanelClientes() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(20, 20));

        JLabel lblTitulo = ConstructorComponentes.crearLabelTitulo("Gestión de Clientes");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 15));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setPreferredSize(new Dimension(300, 0));

        JPanel panelFormulario = new JPanel(new GridLayout(14, 1, 0, 5));
        panelFormulario.setOpaque(false);

        panelFormulario.add(new JLabel("Código Cliente:"));
        txtCodigo = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre Completo:"));
        txtNombre = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Tipo Identificación:"));
        cbTipoIdentificacion = new JComboBox<>(new String[]{"CC", "NIT", "CE", "PA"});
        cbTipoIdentificacion.setBackground(Color.WHITE);
        panelFormulario.add(cbTipoIdentificacion);

        panelFormulario.add(new JLabel("Número Identificación:"));
        txtIdentificacion = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtIdentificacion);

        panelFormulario.add(new JLabel("Dirección:"));
        txtDireccion = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = ConstructorComponentes.crearCampoTexto();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Tipo Cliente:"));
        cbTipoCliente = new JComboBox<>(new String[]{"MINORISTA", "MAYORISTA"});
        cbTipoCliente.setBackground(Color.WHITE);
        panelFormulario.add(cbTipoCliente);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setOpaque(false);
        btnRegistrar = ConstructorComponentes.crearBotonPrimario("REGISTRAR");
        btnInactivar = ConstructorComponentes.crearBotonPrimario("INACTIVAR");
        btnInactivar.setBackground(Color.decode("#C62828"));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnInactivar);

        panelIzquierdo.add(panelFormulario, BorderLayout.NORTH);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        String[] columnas = {"Código", "Nombre", "Tipo ID", "Identificación", "Teléfono", "Tipo", "Activo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.getTableHeader().setBackground(ConstructorComponentes.AZUL_OSCURO);
        tablaClientes.getTableHeader().setForeground(Color.WHITE);
        tablaClientes.setRowHeight(25);
        
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        this.add(panelIzquierdo, BorderLayout.WEST);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtIdentificacion() { return txtIdentificacion; }
    public JTextField getTxtDireccion() { return txtDireccion; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JComboBox<String> getCbTipoIdentificacion() { return cbTipoIdentificacion; }
    public JComboBox<String> getCbTipoCliente() { return cbTipoCliente; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnInactivar() { return btnInactivar; }
    public JTable getTablaClientes() { return tablaClientes; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}