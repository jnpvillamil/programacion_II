package co.edu.uptc.gui;

import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.interfaces.ManejadorEventoAdministracion;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProveedor extends JPanel {

    private final ManejadorEventoAdministracion manejadorEvento;

    private JTextField txtCodigo;
    private JTextField txtNit;
    private JTextField txtRazonSocial;
    private JTextField txtCorreo;
    private JTextField txtRepresentante;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private DefaultTableModel modeloTabla;
    private JTable tablaProveedor;
    private JButton botonCambiarEstado;

    public PanelProveedor(ManejadorEventoAdministracion manejadorEvento) {
        this.manejadorEvento = manejadorEvento;
        setLayout(new BorderLayout(20, 20));
        ConstructorComponentes.aplicarFondoPanel(this);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearEtiquetaNegrita("GESTIÓN DE PROVEEDORES");
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
        txtNit = ConstructorComponentes.crearCampoTexto();
        txtRazonSocial = ConstructorComponentes.crearCampoTexto();
        txtCorreo = ConstructorComponentes.crearCampoTexto();
        txtRepresentante = ConstructorComponentes.crearCampoTexto();
        txtDireccion = ConstructorComponentes.crearCampoTexto();
        txtTelefono = ConstructorComponentes.crearCampoTexto();

        restriccion.gridy = 0;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Código Proveedor:"), restriccion);
        restriccion.gridx = 1;
        panelFormulario.add(txtCodigo, restriccion);
        restriccion.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("NIT:"), restriccion);
        restriccion.gridx = 3;
        panelFormulario.add(txtNit, restriccion);

        restriccion.gridy = 1;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Razón Social:"), restriccion);
        restriccion.gridx = 1;
        panelFormulario.add(txtRazonSocial, restriccion);
        restriccion.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Correo Electrónico:"), restriccion);
        restriccion.gridx = 3;
        panelFormulario.add(txtCorreo, restriccion);

        restriccion.gridy = 2;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Rep. Legal (Nombre):"), restriccion);
        restriccion.gridx = 1;
        panelFormulario.add(txtRepresentante, restriccion);
        restriccion.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Teléfono:"), restriccion);
        restriccion.gridx = 3;
        panelFormulario.add(txtTelefono, restriccion);

        restriccion.gridy = 3;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Dirección:"), restriccion);
        restriccion.gridx = 1;
        restriccion.gridwidth = 3;
        panelFormulario.add(txtDireccion, restriccion);
        restriccion.gridwidth = 1;

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBoton.setBackground(ConstructorComponentes.COLOR_FONDO_PANEL);

        JButton botonBuscar = ConstructorComponentes.crearBotonInformativo("Buscar");
        JButton botonEditar = ConstructorComponentes.crearBotonEditar("Editar");
        botonCambiarEstado = ConstructorComponentes.crearBotonCambiarEstado();
        JButton botonLimpiar = ConstructorComponentes.crearBotonInformativo("Limpiar");
        JButton botonGuardar = ConstructorComponentes.crearBotonGuardar("Guardar");

        botonBuscar.addActionListener(evento -> buscarProveedor());
        botonEditar.addActionListener(evento -> editarProveedor());
        botonCambiarEstado.addActionListener(evento -> cambiarEstadoProveedor());
        botonLimpiar.addActionListener(evento -> limpiarFormulario());
        botonGuardar.addActionListener(evento -> guardarProveedor());

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
        String[] columna = {"NIT", "Razón Social", "Correo", "Teléfono", "Estado"};
        modeloTabla = new DefaultTableModel(columna, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaProveedor = new JTable(modeloTabla);
        ConstructorComponentes.darEstiloTabla(tablaProveedor);
        tablaProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProveedor.getSelectionModel().addListSelectionListener(evento -> {
            if (evento.getValueIsAdjusting()) {
                return;
            }
            int fila = tablaProveedor.getSelectedRow();
            if (fila >= 0) {
                cargarFilaSeleccionada(fila);
                String estado = String.valueOf(modeloTabla.getValueAt(fila, 4));
                if (ConstructorComponentes.esEstadoActivo(estado)) {
                    ConstructorComponentes.configurarBotonInactivar(botonCambiarEstado);
                } else {
                    ConstructorComponentes.configurarBotonActivar(botonCambiarEstado);
                }
            } else {
                ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
            }
        });

        JScrollPane scroll = new JScrollPane(tablaProveedor);
        scroll.setPreferredSize(new Dimension(0, 250));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7)));
        add(scroll, BorderLayout.SOUTH);
    }

    private Proveedor extraerProveedorFormulario() {
        Proveedor proveedor = new Proveedor(
                txtRepresentante.getText().trim(),
                "",
                txtNit.getText().trim(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim(),
                txtCodigo.getText().trim(),
                txtRazonSocial.getText().trim(),
                txtNit.getText().trim(),
                txtCorreo.getText().trim()
        );
        proveedor.setActivo(true);
        return proveedor;
    }

    private void guardarProveedor() {
        if (!validarFormularioBasico()) {
            return;
        }
        String mensaje = manejadorEvento.registrarProveedor(extraerProveedorFormulario());
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Proveedor registrado")) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void buscarProveedor() {
        String nit = txtNit.getText().trim();
        if (ValidadorEntradas.esNuloOVacio(nit)) {
            nit = JOptionPane.showInputDialog(this, "Ingrese el NIT a buscar:");
            if (ValidadorEntradas.esNuloOVacio(nit)) {
                return;
            }
        }

        try {
            Proveedor proveedor = manejadorEvento.buscarPorNit(nit.trim());
            if (proveedor != null) {
                cargarProveedorEnFormulario(proveedor);
            } else {
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado.");
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            mostrarErrorBaseDatos(excepcion);
        }
    }

    private void editarProveedor() {
        if (!validarFormularioBasico()) {
            return;
        }
        String mensaje = manejadorEvento.modificarProveedor(extraerProveedorFormulario());
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Proveedor actualizado")) {
            actualizarTabla();
        }
    }

    private void cambiarEstadoProveedor() {
        if (!botonCambiarEstado.isEnabled()) {
            return;
        }
        String nit = txtNit.getText().trim();
        if (ValidadorEntradas.esNuloOVacio(nit)) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla o ingrese el NIT.");
            return;
        }

        boolean inactivar = "Inactivar".equals(botonCambiarEstado.getText());
        String accion = inactivar ? "inactivar" : "activar";
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea " + accion + " al proveedor con NIT " + nit + "?",
                "Confirmar cambio de estado",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String mensaje = inactivar
                ? manejadorEvento.inactivarProveedor(nit)
                : manejadorEvento.activarProveedor(nit);
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.startsWith("Proveedor inactivado") || mensaje.startsWith("Proveedor activado")) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<ProveedorResumenDTO> lista = manejadorEvento.obtenerListadoResumenProveedor();
            for (ProveedorResumenDTO resumen : lista) {
                modeloTabla.addRow(new Object[]{
                        resumen.nit(),
                        resumen.razonSocial(),
                        resumen.correo(),
                        resumen.telefono(),
                        resumen.estado()
                });
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            mostrarErrorBaseDatos(excepcion);
        }
        tablaProveedor.clearSelection();
        ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
    }

    private void cargarFilaSeleccionada(int fila) {
        String nit = String.valueOf(modeloTabla.getValueAt(fila, 0));
        try {
            Proveedor proveedor = manejadorEvento.buscarPorNit(nit);
            if (proveedor != null) {
                cargarProveedorEnFormulario(proveedor);
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            mostrarErrorBaseDatos(excepcion);
        }
    }

    private void cargarProveedorEnFormulario(Proveedor proveedor) {
        txtCodigo.setText(proveedor.getCodigoProveedor());
        txtNit.setText(proveedor.getNit());
        txtRazonSocial.setText(proveedor.getRazonSocial());
        txtCorreo.setText(proveedor.getCorreoElectronico());
        txtRepresentante.setText(proveedor.getNombre());
        txtDireccion.setText(proveedor.getDireccion());
        txtTelefono.setText(proveedor.getTelefono());
    }

    private boolean validarFormularioBasico() {
        if (ValidadorEntradas.esNuloOVacio(txtCodigo.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del proveedor.");
            txtCodigo.requestFocus();
            return false;
        }
        if (ValidadorEntradas.esNuloOVacio(txtNit.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el NIT.");
            txtNit.requestFocus();
            return false;
        }
        if (ValidadorEntradas.esNuloOVacio(txtRazonSocial.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese la razón social.");
            txtRazonSocial.requestFocus();
            return false;
        }
        if (ValidadorEntradas.esNuloOVacio(txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el correo electrónico.");
            txtCorreo.requestFocus();
            return false;
        }
        if (ValidadorEntradas.esNuloOVacio(txtRepresentante.getText())) {
            JOptionPane.showMessageDialog(this, "Ingrese el representante legal.");
            txtRepresentante.requestFocus();
            return false;
        }
        return true;
    }

    private void mostrarErrorBaseDatos(ExcepcionAccesoDatos excepcion) {
        JOptionPane.showMessageDialog(
                this,
                UtilidadMensajeAccesoDatos.mensajeProveedor(excepcion),
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNit.setText("");
        txtRazonSocial.setText("");
        txtCorreo.setText("");
        txtRepresentante.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        tablaProveedor.clearSelection();
        ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
    }
}
