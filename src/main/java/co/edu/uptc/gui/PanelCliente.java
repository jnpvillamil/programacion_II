package co.edu.uptc.gui;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.interfaces.ManejadorEventoAdministracion;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelCliente extends JPanel {

    private final ManejadorEventoAdministracion manejadorEvento;

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
    private JButton botonCambiarEstado;

    public PanelCliente(ManejadorEventoAdministracion manejadorEvento) {
        this.manejadorEvento = manejadorEvento;
        setLayout(new BorderLayout(20, 20));
        ConstructorComponentes.aplicarFondoPanel(this);
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
        panelContenedorFormulario.setBackground(ConstructorComponentes.COLOR_FONDO_PANEL);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(ConstructorComponentes.COLOR_FONDO_PANEL);
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
        panelBoton.setBackground(ConstructorComponentes.COLOR_FONDO_PANEL);

        JButton botonBuscar = ConstructorComponentes.crearBotonInformativo("Buscar");
        JButton botonEditar = ConstructorComponentes.crearBotonEditar("Editar");
        botonCambiarEstado = ConstructorComponentes.crearBotonCambiarEstado();
        JButton botonLimpiar = ConstructorComponentes.crearBotonInformativo("Limpiar");
        JButton botonGuardar = ConstructorComponentes.crearBotonGuardar("Guardar");

        botonBuscar.addActionListener(evento -> buscarCliente());
        botonEditar.addActionListener(evento -> editarCliente());
        botonCambiarEstado.addActionListener(evento -> cambiarEstadoCliente());
        botonLimpiar.addActionListener(evento -> limpiarFormulario());
        botonGuardar.addActionListener(evento -> guardarCliente());

        panelBoton.add(botonBuscar);
        panelBoton.add(botonEditar);
        panelBoton.add(botonCambiarEstado);
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
            if (evento.getValueIsAdjusting()) {
                return;
            }
            int fila = tablaCliente.getSelectedRow();
            if (fila >= 0) {
                cargarFilaSeleccionada(fila);
                String estado = String.valueOf(modeloTabla.getValueAt(fila, 3));
                if (ConstructorComponentes.esEstadoActivo(estado)) {
                    ConstructorComponentes.configurarBotonInactivar(botonCambiarEstado);
                } else {
                    ConstructorComponentes.configurarBotonActivar(botonCambiarEstado);
                }
            } else {
                ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
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
        String mensaje = manejadorEvento.registrarCliente(extraerClienteFormulario());
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
            Cliente cliente = manejadorEvento.buscarPorIdentificacion(identificacion.trim());
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
        String mensaje = manejadorEvento.modificarCliente(extraerClienteFormulario());
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Cliente actualizado")) {
            actualizarTabla();
        }
    }

    private void cambiarEstadoCliente() {
        if (!botonCambiarEstado.isEnabled()) {
            return;
        }
        String identificacion = txtIdentificacion.getText().trim();
        if (ValidadorEntradas.esNuloOVacio(identificacion)) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla o ingrese la identificación.");
            return;
        }

        boolean inactivar = "Inactivar".equals(botonCambiarEstado.getText());
        String accion = inactivar ? "inactivar" : "activar";
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea " + accion + " al cliente con identificación " + identificacion + "?",
                "Confirmar cambio de estado",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String mensaje = inactivar
                ? manejadorEvento.inactivarCliente(identificacion)
                : manejadorEvento.activarCliente(identificacion);
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Cliente inactivado") || mensaje.startsWith("Cliente activado")) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<ClienteResumenDTO> lista = manejadorEvento.obtenerListadoResumenCliente();
            for (ClienteResumenDTO resumen : lista) {
                modeloTabla.addRow(new Object[]{
                        resumen.codigo(),
                        resumen.nombreCompleto(),
                        resumen.telefono(),
                        resumen.estado()
                });
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            mostrarErrorBaseDatos(excepcion);
        }
        tablaCliente.clearSelection();
        ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
    }

    private void cargarFilaSeleccionada(int fila) {
        String codigo = String.valueOf(modeloTabla.getValueAt(fila, 0));
        try {
            Cliente cliente = manejadorEvento.buscarPorCodigoCliente(codigo);
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
                UtilidadMensajeAccesoDatos.mensajeCliente(excepcion),
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
        ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
    }
}
