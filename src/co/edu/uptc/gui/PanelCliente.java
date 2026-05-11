package co.edu.uptc.gui;

import co.edu.uptc.controlador.ControladorCliente;
import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelCliente extends JPanel {
    private ControladorCliente controlador;
    private JTextField txtIdentificacion, txtNombres, txtApellidos, txtDireccion, txtTelefono, txtCodigo;
    private JComboBox<TipoIdentificacion> cbTipoId;
    private JComboBox<TipoCliente> cbTipoCliente;
    private DefaultTableModel modeloTabla;
    private JTable tablaClientes;

    public PanelCliente(ControladorCliente controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ConstructorComponentes.crearEtiquetaNegrita("GESTIÓN DE CLIENTES"), BorderLayout.NORTH);
        
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
        cbTipoId = new JComboBox<>(TipoIdentificacion.values());
        txtIdentificacion = ConstructorComponentes.crearCampoTexto();
        txtNombres = ConstructorComponentes.crearCampoTexto();
        txtApellidos = ConstructorComponentes.crearCampoTexto();
        txtDireccion = ConstructorComponentes.crearCampoTexto();
        txtTelefono = ConstructorComponentes.crearCampoTexto();
        cbTipoCliente = new JComboBox<>(TipoCliente.values());

        // Fila 1
        gbc.gridy = 0; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Código Cliente:"), gbc);
        gbc.gridx = 1; panelForm.add(txtCodigo, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Tipo ID:"), gbc);
        gbc.gridx = 3; panelForm.add(cbTipoId, gbc);

        // Fila 2
        gbc.gridy = 1; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Identificación:"), gbc);
        gbc.gridx = 1; panelForm.add(txtIdentificacion, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Nombres:"), gbc);
        gbc.gridx = 3; panelForm.add(txtNombres, gbc);

        // Fila 3
        gbc.gridy = 2; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Apellidos:"), gbc);
        gbc.gridx = 1; panelForm.add(txtApellidos, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Teléfono:"), gbc);
        gbc.gridx = 3; panelForm.add(txtTelefono, gbc);

        // Fila 4
        gbc.gridy = 3; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Dirección:"), gbc);
        gbc.gridx = 1; panelForm.add(txtDireccion, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Tipo Cliente:"), gbc);
        gbc.gridx = 3; panelForm.add(cbTipoCliente, gbc);

        // Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        
        JButton btnGuardar = ConstructorComponentes.crearBotonAccion("Guardar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnBuscar = ConstructorComponentes.crearBotonAccion("Buscar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnEditar = ConstructorComponentes.crearBotonAccion("Editar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnInactivar = ConstructorComponentes.crearBotonAccion("Inactivar", ConstructorComponentes.COLOR_AZUL_ACCION);

        btnGuardar.addActionListener(e -> guardarCliente());
        btnBuscar.addActionListener(e -> buscarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnInactivar.addActionListener(e -> inactivarCliente());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnGuardar);

        panelContenedorForm.add(panelForm, BorderLayout.CENTER);
        panelContenedorForm.add(panelBotones, BorderLayout.SOUTH);
        add(panelContenedorForm, BorderLayout.CENTER);
    }

    private void inicializarTabla() {
        String[] columnas = {"Código", "Nombre Completo", "Teléfono", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        ConstructorComponentes.darEstiloTabla(tablaClientes);
        
        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setPreferredSize(new Dimension(0, 250));
        add(scroll, BorderLayout.SOUTH);
    }

    private void guardarCliente() {
        Cliente nuevo = new Cliente(txtNombres.getText(), txtApellidos.getText(), txtIdentificacion.getText(), 
                                    txtDireccion.getText(), txtTelefono.getText(), txtCodigo.getText(), 
                                    (TipoIdentificacion) cbTipoId.getSelectedItem(), (TipoCliente) cbTipoCliente.getSelectedItem());
        String msj = controlador.registrarCliente(nuevo);
        JOptionPane.showMessageDialog(this, msj);
        actualizarTabla();
        limpiarFormulario();
    }

    private void buscarCliente() {
        String id = JOptionPane.showInputDialog(this, "Ingrese la identificación a buscar:");
        if (id != null && !id.trim().isEmpty()) {
            Cliente c = controlador.buscarCliente(id.trim());
            if (c != null) {
                txtCodigo.setText(c.getCodigoCliente());
                cbTipoId.setSelectedItem(c.getTipoIdentificacion());
                txtIdentificacion.setText(c.getIdentificacion());
                txtNombres.setText(c.getNombre());
                txtApellidos.setText(c.getApellido());
                txtDireccion.setText(c.getDireccion());
                txtTelefono.setText(c.getTelefono());
                cbTipoCliente.setSelectedItem(c.getTipoCliente());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            }
        }
    }

    private void editarCliente() {
        Cliente c = new Cliente(txtNombres.getText(), txtApellidos.getText(), txtIdentificacion.getText(), 
                                txtDireccion.getText(), txtTelefono.getText(), txtCodigo.getText(), 
                                (TipoIdentificacion) cbTipoId.getSelectedItem(), (TipoCliente) cbTipoCliente.getSelectedItem());
        String msj = controlador.modificarCliente(c);
        JOptionPane.showMessageDialog(this, msj);
        actualizarTabla();
    }

    private void inactivarCliente() {
        String id = txtIdentificacion.getText();
        if (!id.isEmpty()) {
            String msj = controlador.inactivarCliente(id);
            JOptionPane.showMessageDialog(this, msj);
            actualizarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Busque un cliente primero para inactivarlo.");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<ClienteResumenDTO> lista = controlador.obtenerListadoResumen();
        for (ClienteResumenDTO dto : lista) {
            modeloTabla.addRow(new Object[]{dto.getCodigo(), dto.getNombreCompleto(), dto.getTelefono(), dto.getEstado()});
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText(""); txtIdentificacion.setText("");
        txtNombres.setText(""); txtApellidos.setText("");
        txtDireccion.setText(""); txtTelefono.setText("");
    }
}