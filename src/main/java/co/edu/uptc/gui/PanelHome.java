package co.edu.uptc.gui;

import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.dto.UsuarioResumenDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.interfaces.ManejadorEventoAdministracion;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelHome extends JPanel {

    private final UsuarioDTO usuarioSesion;
    private final ManejadorEventoAdministracion manejadorEventoAdministracion;
    private final Runnable accionCerrarSesion;

    private JPanel panelAdministracion;
    private JTextField txtLogin;
    private JPasswordField txtClave;
    private JComboBox<RolUsuario> comboRol;
    private DefaultTableModel modeloTabla;
    private JTable tablaUsuario;
    private JButton botonCambiarEstado;

    public PanelHome(UsuarioDTO usuarioSesion,
                     ManejadorEventoAdministracion manejadorEventoAdministracion,
                     Runnable accionCerrarSesion) {
        this.usuarioSesion = usuarioSesion;
        this.manejadorEventoAdministracion = manejadorEventoAdministracion;
        this.accionCerrarSesion = accionCerrarSesion;

        setLayout(new BorderLayout(20, 20));
        ConstructorComponentes.aplicarFondoPanel(this);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        add(construirPanelSuperior(), BorderLayout.NORTH);
        add(construirPanelAdministracion(), BorderLayout.CENTER);
        add(construirPanelInferior(), BorderLayout.SOUTH);

        aplicarRestriccionPorRol();
    }

    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 8));
        ConstructorComponentes.aplicarFondoPanel(panel);

        JLabel titulo = ConstructorComponentes.crearTituloModulo("Centro de Control del Usuario");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitulo = new JLabel("Tienda Minorista", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(ConstructorComponentes.COLOR_TEXTO_PRINCIPAL);

        JLabel lblUsuario = ConstructorComponentes.crearEtiquetaNegrita(
                "Usuario conectado: " + usuarioSesion.usuario());
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblRol = ConstructorComponentes.crearEtiquetaNegrita(
                "Rol asignado: " + usuarioSesion.rol());
        lblRol.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titulo);
        panel.add(subtitulo);
        panel.add(lblUsuario);
        panel.add(lblRol);
        return panel;
    }

    private JPanel construirPanelAdministracion() {
        panelAdministracion = new JPanel(new BorderLayout(15, 15));
        ConstructorComponentes.aplicarFondoPanel(panelAdministracion);
        panelAdministracion.setBorder(BorderFactory.createTitledBorder("Administración de Usuarios"));

        panelAdministracion.add(construirFormularioUsuario(), BorderLayout.NORTH);
        panelAdministracion.add(construirTablaUsuario(), BorderLayout.CENTER);
        return panelAdministracion;
    }

    private JPanel construirFormularioUsuario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        ConstructorComponentes.aplicarFondoPanel(panelFormulario);

        GridBagConstraints restriccion = new GridBagConstraints();
        restriccion.fill = GridBagConstraints.HORIZONTAL;
        restriccion.insets = new Insets(8, 10, 8, 10);
        restriccion.weightx = 0.5;

        txtLogin = ConstructorComponentes.crearCampoTexto();
        txtClave = new JPasswordField(15);
        txtClave.setBorder(txtLogin.getBorder());
        comboRol = new JComboBox<>(RolUsuario.values());

        restriccion.gridy = 0;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Login:"), restriccion);
        restriccion.gridx = 1;
        panelFormulario.add(txtLogin, restriccion);
        restriccion.gridx = 2;
        restriccion.fill = GridBagConstraints.NONE;
        JButton btnBuscar = ConstructorComponentes.crearBotonInformativo("Buscar");
        btnBuscar.addActionListener(evento -> buscarUsuario());
        panelFormulario.add(btnBuscar, restriccion);

        restriccion.gridy = 1;
        restriccion.gridx = 0;
        restriccion.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Contraseña:"), restriccion);
        restriccion.gridx = 1;
        restriccion.gridwidth = 2;
        panelFormulario.add(txtClave, restriccion);
        restriccion.gridwidth = 1;

        restriccion.gridy = 2;
        restriccion.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Rol:"), restriccion);
        restriccion.gridx = 1;
        restriccion.gridwidth = 2;
        panelFormulario.add(comboRol, restriccion);
        restriccion.gridwidth = 1;

        restriccion.gridy = 3;
        restriccion.gridx = 0;
        restriccion.gridwidth = 3;
        restriccion.fill = GridBagConstraints.NONE;
        restriccion.anchor = GridBagConstraints.EAST;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        ConstructorComponentes.aplicarFondoPanel(panelBotones);

        JButton btnRegistrar = ConstructorComponentes.crearBotonGuardar("Registrar");
        btnRegistrar.addActionListener(evento -> registrarUsuario());
        botonCambiarEstado = ConstructorComponentes.crearBotonCambiarEstado();
        botonCambiarEstado.addActionListener(evento -> cambiarEstadoUsuario());
        JButton btnEliminar = ConstructorComponentes.crearBotonPeligro("Eliminar");
        btnEliminar.addActionListener(evento -> eliminarUsuario());
        JButton btnLimpiar = ConstructorComponentes.crearBotonInformativo("Limpiar");
        btnLimpiar.addActionListener(evento -> limpiarFormulario());

        panelBotones.add(btnRegistrar);
        panelBotones.add(botonCambiarEstado);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelFormulario.add(panelBotones, restriccion);

        return panelFormulario;
    }

    private JScrollPane construirTablaUsuario() {
        String[] columnas = {"Login", "Nombre", "Rol", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaUsuario = new JTable(modeloTabla);
        ConstructorComponentes.darEstiloTabla(tablaUsuario);
        tablaUsuario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuario.setPreferredScrollableViewportSize(new Dimension(0, 180));
        tablaUsuario.getSelectionModel().addListSelectionListener(evento -> {
            if (evento.getValueIsAdjusting()) {
                return;
            }
            int fila = tablaUsuario.getSelectedRow();
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
        actualizarTabla();
        return new JScrollPane(tablaUsuario);
    }

    private JPanel construirPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ConstructorComponentes.aplicarFondoPanel(panel);

        JButton btnCerrarSesion = ConstructorComponentes.crearBotonInformativo("Cerrar Sesión");
        btnCerrarSesion.addActionListener(evento -> cerrarSesion());
        panel.add(btnCerrarSesion);
        return panel;
    }

    private void aplicarRestriccionPorRol() {
        boolean esAdministrador = RolUsuario.ADMINISTRADOR.name().equals(usuarioSesion.rol());
        panelAdministracion.setVisible(esAdministrador);
    }

    private void cargarFilaSeleccionada(int fila) {
        String login = String.valueOf(modeloTabla.getValueAt(fila, 0));
        String rol = String.valueOf(modeloTabla.getValueAt(fila, 2));
        txtLogin.setText(login);
        comboRol.setSelectedItem(RolUsuario.valueOf(rol));
    }

    private void registrarUsuario() {
        String login = txtLogin.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();
        RolUsuario rol = (RolUsuario) comboRol.getSelectedItem();

        String mensaje = manejadorEventoAdministracion.registrarUsuario(login, clave, rol);
        mostrarResultado(mensaje, "Registro exitoso", "Error de registro");
        if (!mensaje.startsWith("Error:")) {
            limpiarFormulario();
            actualizarTabla();
        }
    }

    private void buscarUsuario() {
        String login = txtLogin.getText().trim();
        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el login a buscar.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = manejadorEventoAdministracion.buscarUsuario(login);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        txtLogin.setText(usuario.getUsuario());
        txtClave.setText(usuario.getClave());
        comboRol.setSelectedItem(RolUsuario.valueOf(usuario.obtenerRol()));
        JOptionPane.showMessageDialog(this, "Usuario cargado en el formulario.", "Búsqueda exitosa",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarUsuario() {
        String login = txtLogin.getText().trim();
        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique el login del usuario a eliminar.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (login.equalsIgnoreCase(usuarioSesion.usuario())) {
            JOptionPane.showMessageDialog(this, "No puede eliminar su propia cuenta de sesión.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Confirma la eliminación permanente del usuario '" + login + "'?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String mensaje = manejadorEventoAdministracion.eliminarUsuario(login);
        mostrarResultado(mensaje, "Eliminación exitosa", "Error de eliminación");
        if (!mensaje.startsWith("Error:")) {
            limpiarFormulario();
            actualizarTabla();
        }
    }

    private void cambiarEstadoUsuario() {
        if (!botonCambiarEstado.isEnabled()) {
            return;
        }
        String login = txtLogin.getText().trim();
        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla o ingrese el login.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean inactivar = "Inactivar".equals(botonCambiarEstado.getText());
        if (inactivar && login.equalsIgnoreCase(usuarioSesion.usuario())) {
            JOptionPane.showMessageDialog(this, "No puede inactivar su propia cuenta de sesión.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String accion = inactivar ? "inactivar" : "activar";
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea " + accion + " al usuario '" + login + "'?",
                "Confirmar cambio de estado",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String mensaje = inactivar
                ? manejadorEventoAdministracion.inactivarUsuario(login)
                : manejadorEventoAdministracion.activarUsuario(login);
        mostrarResultado(mensaje, "Cambio de estado exitoso", "Error de cambio de estado");
        if (!mensaje.startsWith("Error:")) {
            actualizarTabla();
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<UsuarioResumenDTO> lista = manejadorEventoAdministracion.listarResumenUsuario();
        for (UsuarioResumenDTO resumen : lista) {
            modeloTabla.addRow(new Object[]{
                    resumen.login(),
                    resumen.nombreCompleto(),
                    resumen.rol(),
                    resumen.estado()
            });
        }
        if (tablaUsuario != null) {
            tablaUsuario.clearSelection();
        }
        if (botonCambiarEstado != null) {
            ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
        }
    }

    private void limpiarFormulario() {
        txtLogin.setText("");
        txtClave.setText("");
        comboRol.setSelectedIndex(0);
        if (tablaUsuario != null) {
            tablaUsuario.clearSelection();
        }
        if (botonCambiarEstado != null) {
            ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
        }
    }

    private void mostrarResultado(String mensaje, String tituloExito, String tituloError) {
        boolean exito = !mensaje.startsWith("Error:");
        JOptionPane.showMessageDialog(this, mensaje,
                exito ? tituloExito : tituloError,
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar la sesión actual?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            accionCerrarSesion.run();
        }
    }
}
