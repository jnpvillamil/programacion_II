package co.edu.uptc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.dto.CredencialDto;
import co.edu.uptc.gui.LoginGUI;
import co.edu.uptc.gui.MenuPrincipalGUI;
import co.edu.uptc.gui.modelo.Usuario;
import co.edu.uptc.gui.negocio.GestionSeguridad;

public class Evento implements ActionListener {

    private LoginGUI loginGUI;
    private MenuPrincipalGUI menuPrincipalGUI;

    private GestionSeguridad gestionSeguridad;


    private Usuario usuarioSesion;

    public Evento() {
        gestionSeguridad = new GestionSeguridad();
    }

    public void iniciar() {
        loginGUI = new LoginGUI();
        asignarEventosLogin();
        loginGUI.setVisible(true);
    }

    private void asignarEventosLogin() {
        loginGUI.getBtnIngresar().addActionListener(this);
        loginGUI.getBtnSalir().addActionListener(this);
    }

    private void abrirMenuPrincipal() {
        menuPrincipalGUI = new MenuPrincipalGUI(usuarioSesion);
        menuPrincipalGUI.getBtnCerrarSesion().addActionListener(this);
        menuPrincipalGUI.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "INGRESAR":
                iniciarSesion();
                break;
            case "SALIR":
                System.exit(0); //explicar porfa
                break;
            case "CERRAR_SESION":
                menuPrincipalGUI.dispose(); //aca que acontece
                usuarioSesion = null; ///aca tambuen
                iniciar();
                break;


            default:
                JOptionPane.showMessageDialog(null, "Acción no implementada: " + comando);
                break;
        }
    }

	private void iniciarSesion() {
        String usuario = loginGUI.getTxtUsuario().getText().trim();
        String contrasena = new String(loginGUI.getTxtContrasena().getPassword());//porque nuevo string

        CredencialDto credencialDto = new CredencialDto(usuario, contrasena);
        Usuario usuarioValidado = gestionSeguridad.validarIngreso(credencialDto);

        if (usuarioValidado != null) {
            usuarioSesion = usuarioValidado;
            loginGUI.mostrarMensaje("Bienvenido " + usuarioValidado.getNombreUsuario());
            loginGUI.dispose();
            abrirMenuPrincipal();
        } else {
            loginGUI.mostrarMensaje("Usuario o contraseña incorrectos");
        }
    }


}