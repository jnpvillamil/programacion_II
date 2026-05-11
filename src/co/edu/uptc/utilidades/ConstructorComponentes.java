package co.edu.uptc.utilidades;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ConstructorComponentes {
    
    public static final Color COLOR_FONDO_GRIS = new Color(236, 240, 241);
    public static final Color COLOR_MENU_OSCURO = new Color(44, 62, 80);
    public static final Color COLOR_AZUL_ACCION = new Color(41, 128, 185);
    public static final Color COLOR_VERDE_GUARDAR = new Color(39, 174, 96);
    public static final Color COLOR_TEXTO_BLANCO = Color.WHITE;

    public static JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(COLOR_MENU_OSCURO);
        boton.setForeground(COLOR_TEXTO_BLANCO);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setFocusPainted(false);
        // Ajustes para macOS
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public static JButton crearBotonAccion(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(COLOR_TEXTO_BLANCO);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
      
        boton.setOpaque(true); 
        boton.setBorderPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return boton;
    }

    public static JLabel crearEtiquetaNegrita(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        etiqueta.setForeground(Color.BLACK);
        return etiqueta;
    }

    public static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setBackground(Color.WHITE);
  
        campo.setColumns(15);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return campo;
    }

    public static void darEstiloTabla(JTable tabla) {
        tabla.getTableHeader().setBackground(COLOR_MENU_OSCURO);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_BLANCO);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new Color(52, 152, 219));
    }
}