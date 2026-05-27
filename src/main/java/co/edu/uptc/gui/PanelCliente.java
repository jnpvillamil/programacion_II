package co.edu.uptc.gui;

import co.edu.uptc.controlador.ControladorCliente;
import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelCliente extends JPanel {

    private static final Color COLOR_FONDO_PANEL = new Color(0xECF0F1);
    private static final Color COLOR_BOTON_AZUL = new Color(0x1A5274);

    private final ControladorCliente controlador;

    private JTextField txtCodigo;
    private JTextField txtIdentificacion;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JComboBox<TipoIdentificacion> comboTipoIdentificacion;
    private JComboBox<TipoCliente> comboTipoCliente;
    private DefaultTableModel modeloTabla;
    private JTable tablaCliente;

    public PanelCliente(ControladorCliente controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(20, 20));
        setBackground(COLOR_FONDO_PANEL);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearEtiquetaNegrita("GESTIÓN DE CLIENTES");
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        inicializarFormulario();
        inicializarTabla();
        actualizarTabla();
    }

    private void inicializarFormulario() {
        JPanel panelContenedorFormulario = new JPanel(new BorderLayout());
        panelContenedorFormulario.setBackground(COLOR_FONDO_PANEL);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(COLOR_FONDO_PANEL);
        GridBagConstraints restriccion = new GridBagConstraints();
        restriccion.fill = GridBagConstraints.HORIZONTAL;
        restriccion.insets = new Insets(8, 10, 8, 10);
        restriccion.weightx = 0.5;

        txtCodigo = ConstructorComponentes.crearCampoTexto();
        comboTipoIdentificacion = new JComboBox<>(TipoIdentificacion.values());
        txtIdentificacion = ConstructorComponentes.crearCampoTexto();
        txtNombre = ConstructorComponentes.crearCampoTexto();
        txtApellido = ConstructorComponentes.crearCampoTexto();
        txtDireccion = ConstructorComponentes.crearCampoTexto();
        txtTelefono = ConstructorComponentes.crearCampoTexto();
        comboTipoCliente = new JComboBox<>(TipoCliente.values());

        estilizarCombo(comboTipoIdentificacion);
        estilizarCombo(comboTipoCliente);

        restriccion.gridy = 0;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Código Cliente:"), restriccion);
        restriccion.gridx = 1;
        panelFormulario.add(txtCodigo, restriccion);
        restriccion.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Tipo ID:"), restriccion);
        restriccion.gridx = 3;
        panelFormulario.add(comboTipoIdentificacion, restriccion);

        restriccion.gridy = 1;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Identificación:"), restriccion);
        restriccion.gridx = 1;
        panelFormulario.add(txtIdentificacion, restriccion);
        restriccion.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Nombres:"), restriccion);
        restriccion.gridx = 3;
        panelFormulario.add(txtNombre, restriccion);

        restriccion.gridy = 2;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Apellidos:"), restriccion);
        restriccion.gridx = 1;
        panelFormulario.add(txtApellido, restriccion);
        restriccion.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Teléfono:"), restriccion);
        restriccion.gridx = 3;
        panelFormulario.add(txtTelefono, restriccion);

        restriccion.gridy = 3;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Dirección:"), restriccion);
        restriccion.gridx = 1;
        panelFormulario.add(txtDireccion, restriccion);
        restriccion.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Tipo Cliente:"), restriccion);
        restriccion.gridx = 3;
        panelFormulario.add(comboTipoCliente, restriccion);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBoton.setBackground(COLOR_FONDO_PANEL);

        JButton botonBuscar = ConstructorComponentes.crearBotonAccion("Buscar", COLOR_BOTON_AZUL);
        JButton botonEditar = ConstructorComponentes.crearBotonAccion("Editar", COLOR_BOTON_AZUL);
        JButton botonInactivar = ConstructorComponentes.crearBotonAccion("Inactivar", COLOR_BOTON_AZUL);
        JButton botonActivar = ConstructorComponentes.crearBotonAccion("Activar", COLOR_BOTON_AZUL);
        JButton botonLimpiar = ConstructorComponentes.crearBotonAccion("Limpiar", COLOR_BOTON_AZUL);
        JButton botonGuardar = ConstructorComponentes.crearBotonAccion("Guardar", COLOR_BOTON_AZUL);

        botonBuscar.addActionListener(evento -> buscarCliente());
        botonEditar.addActionListener(evento -> editarCliente());
        botonInactivar.addActionListener(evento -> inactivarCliente());
        botonActivar.addActionListener(evento -> activarCliente());
        botonLimpiar.addActionListener(evento -> limpiarFormulario());
        botonGuardar.addActionListener(evento -> guardarCliente());

        panelBoton.add(botonBuscar);
        panelBoton.add(botonEditar);
        panelBoton.add(botonInactivar);
        panelBoton.add(botonActivar);
        panelBoton.add(botonLimpiar);
        panelBoton.add(botonGuardar);

        panelContenedorFormulario.add(panelFormulario, BorderLayout.CENTER);
        panelContenedorFormulario.add(panelBoton, BorderLayout.SOUTH);
        add(panelContenedorFormulario, BorderLayout.CENTER);
    }

    private void inicializarTabla() {
        String[] columna = {"Código", "Nombre Completo", "Teléfono", "Estado"};
        modeloTabla = new DefaultTableModel(columna, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaCliente = new JTable(modeloTabla);
        ConstructorComponentes.darEstiloTabla(tablaCliente);
        tablaCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCliente.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting() && tablaCliente.getSelectedRow() >= 0) {
                cargarFilaSeleccionada(tablaCliente.getSelectedRow());
            }
        });

        JScrollPane scroll = new JScrollPane(tablaCliente);
        scroll.setPreferredSize(new Dimension(0, 250));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7)));
        add(scroll, BorderLayout.SOUTH);
    }

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setBackground(Color.WHITE);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }

    private Cliente extraerClienteFormulario() {
        Cliente cliente = new Cliente(
                txtNombre.getText().trim(),
                txtApellido.getText().trim(),
                txtIdentificacion.getText().trim(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim(),
                txtCodigo.getText().trim(),
                (TipoIdentificacion) comboTipoIdentificacion.getSelectedItem(),
                (TipoCliente) comboTipoCliente.getSelectedItem()
        );
        cliente.setActivo(true);
        return cliente;
    }

    private void guardarCliente() {
        if (!validarFormularioBasico()) {
            return;
        }
        String mensaje = controlador.registrarCliente(extraerClienteFormulario());
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Cliente registrado")) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void buscarCliente() {
        String identificacion = txtIdentificacion.getText().trim();
        if (ValidadorEntradas.esNuloOVacio(identificacion)) {
            identificacion = JOptionPane.showInputDialog(this, "Ingrese la identificación a buscar:");
            if (ValidadorEntradas.esNuloOVacio(identificacion)) {
                return;
            }
        }

        try {
            Cliente cliente = controlador.buscarPorIdentificacion(identificacion.trim());
            if (cliente != null) {
                cargarClienteEnFormulario(cliente);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            mostrarErrorBaseDatos(excepcion);
        }
    }

    private void editarCliente() {
        if (!validarFormularioBasico()) {
            return;
        }
        String mensaje = controlador.modificarCliente(extraerClienteFormulario());
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Cliente actualizado")) {
            actualizarTabla();
        }
    }

    private void inactivarCliente() {
        String identificacion = txtIdentificacion.getText().trim();
        if (ValidadorEntradas.esNuloOVacio(identificacion)) {
            JOptionPane.showMessageDialog(this, "Busque un cliente primero o ingrese la identificación.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea inactivar al cliente con identificación " + identificacion + "?",
                "Confirmar inactivación",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        String mensaje = controlador.inactivarCliente(identificacion);
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Cliente inactivado")) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void activarCliente() {
        String identificacion = txtIdentificacion.getText().trim();
        if (ValidadorEntradas.esNuloOVacio(identificacion)) {
            JOptionPane.showMessageDialog(this, "Busque un cliente inactivo primero o ingrese la identificación.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea activar al cliente con identificación " + identificacion + "?",
                "Confirmar activación",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        String mensaje = controlador.activarCliente(identificacion);
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Cliente activado")) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<ClienteResumenDTO> lista = controlador.obtenerListadoResumen();
            for (ClienteResumenDTO resumen : lista) {
                modeloTabla.addRow(new Object[]{
                        resumen.getCodigo(),
                        resumen.getNombreCompleto(),
                        resumen.getTelefono(),
                        resumen.getEstado()
                });
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            mostrarErrorBaseDatos(excepcion);
        }
    }

    private void cargarFilaSeleccionada(int fila) {
        String codigo = String.valueOf(modeloTabla.getValueAt(fila, 0));
        try {
            Cliente cliente = controlador.buscarPorCodigo(codigo);
            if (cliente != null) {
                cargarClienteEnFormulario(cliente);
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            mostrarErrorBaseDatos(excepcion);
        }
    }

    private void cargarClienteEnFormulario(Cliente cliente) {
        txtCodigo.setText(cliente.getCodigoCliente());
        comboTipoIdentificacion.setSelectedItem(cliente.getTipoIdentificacion());
        txtIdentificacion.setText(cliente.getIdentificacion());
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtDireccion.setText(cliente.getDireccion());
        txtTelefono.setText(cliente.getTelefono());
        comboTipoCliente.setSelectedItem(cliente.getTipoCliente());
    }

    private boolean validarFormularioBasico() {
        if (ValidadorEntradas.esNuloOVacio(txtCodigo.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del cliente.");
            txtCodigo.requestFocus();
            return false;
        }
        if (ValidadorEntradas.esNuloOVacio(txtIdentificacion.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de identificación.");
            txtIdentificacion.requestFocus();
            return false;
        }
        if (ValidadorEntradas.esNuloOVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese los nombres del cliente.");
            txtNombre.requestFocus();
            return false;
        }
        if (ValidadorEntradas.esNuloOVacio(txtApellido.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese los apellidos del cliente.");
            txtApellido.requestFocus();
            return false;
        }
        return true;
    }

    private void mostrarErrorBaseDatos(ExcepcionAccesoDatos excepcion) {
        JOptionPane.showMessageDialog(
                this,
                ControladorCliente.mensajeParaUsuario(excepcion),
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtIdentificacion.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        comboTipoIdentificacion.setSelectedIndex(0);
        comboTipoCliente.setSelectedIndex(0);
        tablaCliente.clearSelection();
    }
}
