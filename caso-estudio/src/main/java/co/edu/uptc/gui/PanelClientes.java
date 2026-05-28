package co.edu.uptc.gui;

import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelClientes extends PanelBase {

    private JTextField txtCodigo, txtNombre, txtIdentificacion, txtDireccion, txtTelefono;
    private JComboBox<TipoIdentificacion> cbTipoIdentificacion;
    private JComboBox<TipoCliente> cbTipoCliente;
    private JButton btnRegistrar, btnEditar, btnBuscar, btnInactivar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public PanelClientes() {
        super();
    }

    @Override
    public void initComponents() {
        this.setLayout(new BorderLayout(20, 20));


        this.add(ConstructorComponentes.crearLabelTitulo("Clientes"), BorderLayout.NORTH);


        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 15));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setPreferredSize(new Dimension(320, 0));

        JPanel panelForm = new JPanel(new GridLayout(14, 1, 0, 5));
        panelForm.setOpaque(false);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("Código Cliente:")); txtCodigo = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtCodigo);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Nombre Completo:")); txtNombre = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtNombre);
        
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Tipo Identificación:"));
        cbTipoIdentificacion = ConstructorComponentes.crearComboBoxEnum(TipoIdentificacion.values());
        panelForm.add(cbTipoIdentificacion);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("Número Identificación:")); txtIdentificacion = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtIdentificacion);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Dirección:")); txtDireccion = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtDireccion);
        panelForm.add(ConstructorComponentes.crearLabelFormulario("Teléfono:")); txtTelefono = ConstructorComponentes.crearCampoTexto(); panelForm.add(txtTelefono);

        panelForm.add(ConstructorComponentes.crearLabelFormulario("Tipo Cliente:"));
        cbTipoCliente = ConstructorComponentes.crearComboBoxEnum(TipoCliente.values());
        panelForm.add(cbTipoCliente);


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


        String[] columnas = {"Código", "Nombre", "Teléfono"};
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
    public JComboBox<TipoIdentificacion> getCbTipoIdentificacion() { return cbTipoIdentificacion; }
    public JComboBox<TipoCliente> getCbTipoCliente() { return cbTipoCliente; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnInactivar() { return btnInactivar; }
    public JTable getTablaClientes() { return tablaClientes; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}