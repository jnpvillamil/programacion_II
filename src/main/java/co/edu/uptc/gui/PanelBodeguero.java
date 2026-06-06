package co.edu.uptc.gui;

import co.edu.uptc.dto.BodegueroDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.interfaces.ManejadorEventoBodeguero;
import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import java.awt.*;

public class PanelBodeguero extends JPanel {

    private final ManejadorEventoBodeguero manejadorEvento;

    private JTextField txtLogin;
    private JPasswordField txtClave;
    private JTextField txtZonaBodega;

    public PanelBodeguero(ManejadorEventoBodeguero manejadorEvento) {
        this.manejadorEvento = manejadorEvento;

        setLayout(new BorderLayout(20, 20));
        ConstructorComponentes.aplicarFondoPanel(this);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearTituloModulo("Registro de Bodeguero");
        add(titulo, BorderLayout.NORTH);
        add(construirFormulario(), BorderLayout.CENTER);
    }

    private JPanel construirFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        ConstructorComponentes.aplicarFondoPanel(panelFormulario);
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Bodeguero"));

        GridBagConstraints restriccion = new GridBagConstraints();
        restriccion.fill = GridBagConstraints.HORIZONTAL;
        restriccion.insets = new Insets(8, 10, 8, 10);
        restriccion.weightx = 1.0;

        txtLogin = ConstructorComponentes.crearCampoTexto();
        txtClave = new JPasswordField(15);
        txtClave.setBorder(txtLogin.getBorder());
        txtZonaBodega = ConstructorComponentes.crearCampoTexto();

        restriccion.gridy = 0;
        restriccion.gridx = 0;
        restriccion.weightx = 0.3;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Login:"), restriccion);

        restriccion.gridx = 1;
        restriccion.weightx = 0.7;
        panelFormulario.add(txtLogin, restriccion);

        restriccion.gridy = 1;
        restriccion.gridx = 0;
        restriccion.weightx = 0.3;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Contraseña:"), restriccion);

        restriccion.gridx = 1;
        restriccion.weightx = 0.7;
        panelFormulario.add(txtClave, restriccion);

        restriccion.gridy = 2;
        restriccion.gridx = 0;
        restriccion.weightx = 0.3;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Zona de Bodega:"), restriccion);

        restriccion.gridx = 1;
        restriccion.weightx = 0.7;
        panelFormulario.add(txtZonaBodega, restriccion);

        restriccion.gridy = 3;
        restriccion.gridx = 0;
        restriccion.gridwidth = 2;
        restriccion.fill = GridBagConstraints.NONE;
        restriccion.anchor = GridBagConstraints.EAST;

        JButton btnRegistrar = ConstructorComponentes.crearBotonGuardar("Registrar Bodeguero");
        btnRegistrar.addActionListener(evento -> registrarBodeguero());
        panelFormulario.add(btnRegistrar, restriccion);

        return panelFormulario;
    }

    private void registrarBodeguero() {
        String login = txtLogin.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();
        String zonaBodega = txtZonaBodega.getText().trim();

        BodegueroDTO dto = new BodegueroDTO(
                login,
                RolUsuario.BODEGUERO.name(),
                zonaBodega);

        boolean exito = manejadorEvento.registrarBodeguero(dto, clave);

        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Bodeguero registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No fue posible registrar el bodeguero. Verifique login, contraseña y zona de bodega.",
                    "Error de registro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtLogin.setText("");
        txtClave.setText("");
        txtZonaBodega.setText("");
    }
}
