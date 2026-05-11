package co.edu.uptc.gui;

import co.edu.uptc.controlador.ControladorProveedor;
import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProveedor extends JPanel {
    private ControladorProveedor controlador;
    private JTextField txtCodigo, txtNit, txtRazonSocial, txtCorreo, txtDireccion, txtTelefono, txtRepresentante;
    private DefaultTableModel modeloTabla;
    private JTable tablaProveedores;

    // EL CONSTRUCTOR AHORA RECIBE EL CONTROLADOR CORRECTAMENTE
    public PanelProveedor(ControladorProveedor controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ConstructorComponentes.crearEtiquetaNegrita("GESTIÓN DE PROVEEDORES"), BorderLayout.NORTH);
        
        inicializarFormulario();
        inicializarTabla();
    }

    private void inicializarFormulario() {
        JPanel panelContenedorForm = new JPanel(new BorderLayout());
        panelContenedorForm.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 0.5;

        // Instanciar campos
        txtCodigo = ConstructorComponentes.crearCampoTexto();
        txtNit = ConstructorComponentes.crearCampoTexto();
        txtRazonSocial = ConstructorComponentes.crearCampoTexto();
        txtCorreo = ConstructorComponentes.crearCampoTexto();
        txtRepresentante = ConstructorComponentes.crearCampoTexto();
        txtDireccion = ConstructorComponentes.crearCampoTexto();
        txtTelefono = ConstructorComponentes.crearCampoTexto();

        // Fila 1
        gbc.gridy = 0; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Código Proveedor:"), gbc);
        gbc.gridx = 1; panelForm.add(txtCodigo, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("NIT:"), gbc);
        gbc.gridx = 3; panelForm.add(txtNit, gbc);

        // Fila 2
        gbc.gridy = 1; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Razón Social:"), gbc);
        gbc.gridx = 1; panelForm.add(txtRazonSocial, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Correo Electrónico:"), gbc);
        gbc.gridx = 3; panelForm.add(txtCorreo, gbc);

        // Fila 3
        gbc.gridy = 2; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Rep. Legal (Nombre):"), gbc);
        gbc.gridx = 1; panelForm.add(txtRepresentante, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Teléfono:"), gbc);
        gbc.gridx = 3; panelForm.add(txtTelefono, gbc);

        // Fila 4
        gbc.gridy = 3; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Dirección:"), gbc);
        gbc.gridx = 1; panelForm.add(txtDireccion, gbc);

        // Botones de acción AZULES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        
        JButton btnBuscar = ConstructorComponentes.crearBotonAccion("Buscar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnEditar = ConstructorComponentes.crearBotonAccion("Editar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnInactivar = ConstructorComponentes.crearBotonAccion("Inactivar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnGuardar = ConstructorComponentes.crearBotonAccion("Guardar", ConstructorComponentes.COLOR_AZUL_ACCION);

        btnGuardar.addActionListener(e -> guardarProveedor());
        btnBuscar.addActionListener(e -> buscarProveedor());
        btnEditar.addActionListener(e -> editarProveedor());
        btnInactivar.addActionListener(e -> inactivarProveedor());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnGuardar);

        panelContenedorForm.add(panelForm, BorderLayout.NORTH);
        panelContenedorForm.add(panelBotones, BorderLayout.SOUTH);
        add(panelContenedorForm, BorderLayout.CENTER);
    }

    private void inicializarTabla() {
        String[] columnas = {"NIT", "Razón Social", "Correo", "Teléfono", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProveedores = new JTable(modeloTabla);
        ConstructorComponentes.darEstiloTabla(tablaProveedores);
        
        JScrollPane scroll = new JScrollPane(tablaProveedores);
        scroll.setPreferredSize(new Dimension(0, 250));
        add(scroll, BorderLayout.SOUTH);
    }

    private void guardarProveedor() {
        Proveedor nuevo = new Proveedor(txtRepresentante.getText(), "", "", txtDireccion.getText(), txtTelefono.getText(), 
                                        txtCodigo.getText(), txtRazonSocial.getText(), txtNit.getText(), txtCorreo.getText());
        String msj = controlador.registrarProveedor(nuevo);
        JOptionPane.showMessageDialog(this, msj);
        actualizarTabla();
        limpiarFormulario();
    }

    private void buscarProveedor() {
        String nit = JOptionPane.showInputDialog(this, "Ingrese el NIT a buscar:");
        if (nit != null && !nit.trim().isEmpty()) {
            Proveedor p = controlador.buscarProveedor(nit.trim());
            if (p != null) {
                txtCodigo.setText(p.getCodigoProveedor());
                txtNit.setText(p.getNit());
                txtRazonSocial.setText(p.getRazonSocial());
                txtCorreo.setText(p.getCorreoElectronico());
                txtRepresentante.setText(p.getNombre());
                txtDireccion.setText(p.getDireccion());
                txtTelefono.setText(p.getTelefono());
            } else {
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado.");
            }
        }
    }

    private void editarProveedor() {
        Proveedor p = new Proveedor(txtRepresentante.getText(), "", "", txtDireccion.getText(), txtTelefono.getText(), 
                                    txtCodigo.getText(), txtRazonSocial.getText(), txtNit.getText(), txtCorreo.getText());
        String msj = controlador.modificarProveedor(p);
        JOptionPane.showMessageDialog(this, msj);
        actualizarTabla();
    }

    private void inactivarProveedor() {
        String nit = txtNit.getText();
        if (!nit.isEmpty()) {
            String msj = controlador.inactivarProveedor(nit);
            JOptionPane.showMessageDialog(this, msj);
            actualizarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Busque un proveedor primero para inactivarlo.");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<ProveedorResumenDTO> lista = controlador.obtenerListadoResumen();
        for (ProveedorResumenDTO dto : lista) {
            modeloTabla.addRow(new Object[]{dto.getNit(), dto.getRazonSocial(), dto.getCorreo(), dto.getTelefono(), dto.getEstado()});
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText(""); txtNit.setText("");
        txtRazonSocial.setText(""); txtCorreo.setText("");
        txtRepresentante.setText(""); txtDireccion.setText(""); txtTelefono.setText("");
    }
}