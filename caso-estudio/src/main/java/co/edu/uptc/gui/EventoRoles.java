package co.edu.uptc.gui;

import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.modelo.Administrador;
import co.edu.uptc.modelo.Cajero;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.negocio.GestionSupervisor;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventoRoles implements ActionListener {

    public static final String CMD_REGISTRAR = "CMD_REGISTRAR_ROL";
    public static final String CMD_LISTAR = "CMD_LISTAR_ROLES";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelRoles panel;
    private final GestionSupervisor gestion;

    public EventoRoles(VentanaPrincipal ventanaPrincipal,
                       PanelRoles panel,
                       GestionSupervisor gestion) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panel = panel;
        this.gestion = gestion;

        suscribir(panel.getBtnRegistrar(), CMD_REGISTRAR);
        suscribir(panel.getBtnListar(), CMD_LISTAR);
    }

    public void refrescarVista() {
        actualizarTabla();
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void registrar() {
        String nombre = panel.getTxtNombre().getText().trim();
        String login = panel.getTxtUsuario().getText().trim();
        String clave = new String(panel.getTxtClave().getPassword());
        RolUsuario rol = (RolUsuario) panel.getCbRol().getSelectedItem();

        Usuario usuario;
        if (rol == RolUsuario.CAJERO) {
            usuario = new Cajero(nombre, "", "", "", login, clave);
        } else {
            usuario = new Administrador(nombre, "", "", "", login, clave);
        }

        ResultadoOperacion resultado = gestion.registrarUsuarioConRol(usuario, rol);
        mostrarResultado(resultado);

        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = panel.getModeloTabla();
        modelo.setRowCount(0);

        List<UsuarioDTO> usuarios = gestion.listarUsuarios();
        for (UsuarioDTO u : usuarios) {
            modelo.addRow(new Object[]{
                    u.getUsuario(),
                    u.getNombre(),
                    u.getRol()
            });
        }
    }

    private void limpiarFormulario() {
        panel.getTxtNombre().setText("");
        panel.getTxtUsuario().setText("");
        panel.getTxtClave().setText("");
        panel.getCbRol().setSelectedIndex(0);
    }

    private void mostrarResultado(ResultadoOperacion resultado) {
        JOptionPane.showMessageDialog(
                panel,
                resultado.getMensaje(),
                resultado.isExito() ? "Éxito" : "Error",
                resultado.isExito() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_REGISTRAR.equals(comando)) {
            registrar();
        } else if (CMD_LISTAR.equals(comando)) {
            actualizarTabla();
        }
    }
}