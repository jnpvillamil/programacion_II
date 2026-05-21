package co.edu.uptc.gui;

import co.edu.uptc.utilidades.*;
import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoClave;

    public VentanaLogin() {
        setTitle("Acceso al Sistema - Tienda Minorista");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Hace que los campos se expandan horizontalmente
        gbc.insets = new Insets(10, 5, 10, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.3; // Proporción de tamaño para la etiqueta
        panelCentral.add(ConstructorComponentes.crearEtiquetaNegrita("Usuario:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 0.7; // Proporción de tamaño para el campo de texto
        campoUsuario = ConstructorComponentes.crearCampoTexto();
        panelCentral.add(campoUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.3;
        panelCentral.add(ConstructorComponentes.crearEtiquetaNegrita("Contraseña:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 0.7;
        campoClave = new JPasswordField(15);
        campoClave.setBorder(campoUsuario.getBorder());
        panelCentral.add(campoClave, gbc);

        gbc.gridx = 0; gbc.gridy = 2; 
        gbc.gridwidth = 2; // Hace que el botón ocupe dos columnas
        gbc.fill = GridBagConstraints.NONE; // Para que el botón no se estire demasiado
        JButton botonIngresar = ConstructorComponentes.crearBotonAccion("Iniciar Sesión", ConstructorComponentes.COLOR_AZUL_ACCION);
        botonIngresar.setPreferredSize(new Dimension(150, 40));
        botonIngresar.addActionListener(e -> {
            new VentanaPrincipal().setVisible(true);
            dispose();
        });
        panelCentral.add(botonIngresar, gbc);

        add(panelCentral, BorderLayout.CENTER);
        CentradorVentanas.centrar(this);
    }
}