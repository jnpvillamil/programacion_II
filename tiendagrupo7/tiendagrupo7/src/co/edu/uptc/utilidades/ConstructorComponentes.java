package co.edu.uptc.utilidades;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConstructorComponentes {

    public static final Color AZUL_OSCURO = Color.decode("#1A237E");
    public static final Color GRIS_FONDO = Color.decode("#ECECEC");
    public static final Color COLOR_HOVER = Color.decode("#283593");

    public static JButton crearBotonPrimario(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(AZUL_OSCURO);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public static JLabel crearLabelTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(Color.BLACK);
        return label;
    }

    public static JTextField crearCampoTexto() {
        JTextField campoTexto = new JTextField();
        campoTexto.setBackground(Color.WHITE);
        campoTexto.setFont(new Font("SansSerif", Font.PLAIN, 12));
        Border bordeSuave = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true);
        campoTexto.setBorder(BorderFactory.createCompoundBorder(
                bordeSuave,
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return campoTexto;
    }

    public static JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(AZUL_OSCURO);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setHorizontalAlignment(SwingConstants.LEFT);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(COLOR_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(AZUL_OSCURO);
            }
        });

        return boton;
    }
}