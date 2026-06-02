package co.edu.uptc.gui;

import co.edu.uptc.dto.LoginDTO;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.gui.evento.EventoSistema;
import co.edu.uptc.interfaces.ManejadorEventoSistema;
import co.edu.uptc.negocio.ExcepcionAutenticacion;
import co.edu.uptc.negocio.GestionContable;
import co.edu.uptc.negocio.GestionUsuario;
import co.edu.uptc.negocio.ServicioAutenticacion;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.persistencia.PersistenciaAdministracion;
import co.edu.uptc.persistencia.PersistenciaContable;
import co.edu.uptc.utilidades.CentradorVentanas;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private final JTextField campoUsuario;
    private final JPasswordField campoClave;
    private final ManejadorEventoSistema manejadorEventoSistema;
    private final GestionUsuario gestionUsuario;
    private final GestionContable gestionContable;

    public VentanaLogin() {
        PersistenciaAdministracion persistenciaAdministracion = new PersistenciaAdministracion();
        gestionUsuario = new GestionUsuario(persistenciaAdministracion);
        ServicioAutenticacion servicioAutenticacion = new ServicioAutenticacion(gestionUsuario);
        gestionContable = new GestionContable(new PersistenciaContable());
        manejadorEventoSistema = new EventoSistema(servicioAutenticacion, gestionContable);

        setTitle("Acceso al Sistema - Tienda Minorista");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints restriccion = new GridBagConstraints();
        restriccion.fill = GridBagConstraints.HORIZONTAL;
        restriccion.insets = new Insets(10, 5, 10, 5);

        restriccion.gridx = 0;
        restriccion.gridy = 0;
        restriccion.weightx = 0.3;
        panelCentral.add(ConstructorComponentes.crearEtiquetaNegrita("Usuario:"), restriccion);

        restriccion.gridx = 1;
        restriccion.weightx = 0.7;
        campoUsuario = ConstructorComponentes.crearCampoTexto();
        panelCentral.add(campoUsuario, restriccion);

        restriccion.gridx = 0;
        restriccion.gridy = 1;
        restriccion.weightx = 0.3;
        panelCentral.add(ConstructorComponentes.crearEtiquetaNegrita("Contraseña:"), restriccion);

        restriccion.gridx = 1;
        restriccion.weightx = 0.7;
        campoClave = new JPasswordField(15);
        campoClave.setBorder(campoUsuario.getBorder());
        panelCentral.add(campoClave, restriccion);

        restriccion.gridx = 0;
        restriccion.gridy = 2;
        restriccion.gridwidth = 2;
        restriccion.fill = GridBagConstraints.NONE;
        JButton botonIngresar = ConstructorComponentes.crearBotonGuardar("Iniciar Sesión");
        botonIngresar.setPreferredSize(new Dimension(160, 40));
        botonIngresar.addActionListener(evento -> iniciarSesion());
        panelCentral.add(botonIngresar, restriccion);

        add(panelCentral, BorderLayout.CENTER);
        CentradorVentanas.centrar(this);
    }

    private void iniciarSesion() {
        String usuario = campoUsuario.getText().trim();
        String clave = new String(campoClave.getPassword()).trim();

        try {
            UsuarioDTO usuarioAutenticado = manejadorEventoSistema.validarIngreso(new LoginDTO(usuario, clave));
            dispose();
            SwingUtilities.invokeLater(() -> {
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(
                        usuarioAutenticado,
                        manejadorEventoSistema,
                        gestionUsuario,
                        gestionContable);
                ventanaPrincipal.setVisible(true);
            });
        } catch (ExcepcionAutenticacion excepcion) {
            JOptionPane.showMessageDialog(this, excepcion.getMessage(), "Acceso denegado",
                    JOptionPane.ERROR_MESSAGE);
        } catch (ExcepcionAccesoDatos excepcion) {
            JOptionPane.showMessageDialog(this,
                    UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
