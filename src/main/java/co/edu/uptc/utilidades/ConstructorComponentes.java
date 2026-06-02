package co.edu.uptc.utilidades;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ConstructorComponentes {

    public static final Color COLOR_FONDO_PANEL = new Color(0xF8F9FA);
    public static final Color COLOR_FONDO_BLANCO = Color.WHITE;
    public static final Color COLOR_MENU_LATERAL = new Color(0x0D2619);
    public static final Color COLOR_TEXTO_PRINCIPAL = new Color(0x2C3E50);
    public static final Color COLOR_BOTON_GUARDAR = new Color(0x27AE60);
    public static final Color COLOR_BOTON_EDITAR = new Color(0xF39C12);
    public static final Color COLOR_BOTON_INFORMATIVO = new Color(0x2980B9);
    public static final Color COLOR_BOTON_PELIGRO = new Color(0xC0392B);
    public static final Color COLOR_BOTON_DESHABILITADO = new Color(0xE9ECEF);
    public static final Color COLOR_TEXTO_BLANCO = Color.WHITE;
    public static final Color COLOR_SELECCION_TABLA = new Color(0x2980B9);

    public static final Color COLOR_FONDO_GRIS = COLOR_FONDO_PANEL;
    public static final Color COLOR_MENU_OSCURO = COLOR_MENU_LATERAL;
    public static final Color COLOR_BOTON_POSITIVO = COLOR_BOTON_GUARDAR;
    public static final Color COLOR_BOTON_SECUNDARIO = COLOR_BOTON_INFORMATIVO;
    public static final Color COLOR_BOTON_NEUTRO = COLOR_BOTON_INFORMATIVO;
    public static final Color COLOR_AZUL_ACCION = COLOR_BOTON_INFORMATIVO;
    public static final Color COLOR_VERDE_GUARDAR = COLOR_BOTON_GUARDAR;

    private ConstructorComponentes() {
    }

    public static void aplicarFondoPanel(JComponent componente) {
        componente.setBackground(COLOR_FONDO_PANEL);
    }

    public static void aplicarEstiloBoton(JButton boton, Color colorFondo, Color colorTexto) {
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        aplicarEstiloBoton(boton, COLOR_MENU_LATERAL, COLOR_TEXTO_BLANCO);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return boton;
    }

    public static JButton crearBotonAccion(String texto, Color colorFondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        aplicarEstiloBoton(boton, colorFondo, colorTexto);
        return boton;
    }

    public static JButton crearBotonGuardar(String texto) {
        return crearBotonAccion(texto, COLOR_BOTON_GUARDAR, COLOR_TEXTO_BLANCO);
    }

    public static JButton crearBotonEditar(String texto) {
        return crearBotonAccion(texto, COLOR_BOTON_EDITAR, COLOR_TEXTO_PRINCIPAL);
    }

    public static JButton crearBotonInformativo(String texto) {
        return crearBotonAccion(texto, COLOR_BOTON_INFORMATIVO, COLOR_TEXTO_BLANCO);
    }

    public static JButton crearBotonPeligro(String texto) {
        return crearBotonAccion(texto, COLOR_BOTON_PELIGRO, COLOR_TEXTO_BLANCO);
    }

    public static JButton crearBotonPositivo(String texto) {
        return crearBotonGuardar(texto);
    }

    public static JButton crearBotonSecundario(String texto) {
        return crearBotonInformativo(texto);
    }

    public static JButton crearBotonNeutro(String texto) {
        return crearBotonInformativo(texto);
    }

    public static JButton crearBotonCambiarEstado() {
        JButton boton = new JButton("Cambiar Estado");
        configurarBotonEstadoNeutral(boton);
        return boton;
    }

    public static void configurarBotonEstadoNeutral(JButton boton) {
        boton.setText("Cambiar Estado");
        boton.setEnabled(false);
        aplicarEstiloBoton(boton, COLOR_BOTON_DESHABILITADO, COLOR_TEXTO_PRINCIPAL);
        boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public static void configurarBotonInactivar(JButton boton) {
        boton.setText("Inactivar");
        boton.setEnabled(true);
        aplicarEstiloBoton(boton, COLOR_BOTON_PELIGRO, COLOR_TEXTO_BLANCO);
    }

    public static void configurarBotonActivar(JButton boton) {
        boton.setText("Activar");
        boton.setEnabled(true);
        aplicarEstiloBoton(boton, COLOR_BOTON_INFORMATIVO, COLOR_TEXTO_BLANCO);
    }

    public static boolean esEstadoActivo(String estado) {
        return estado != null && estado.equalsIgnoreCase("Activo");
    }

    public static JLabel crearEtiquetaNegrita(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        etiqueta.setForeground(COLOR_TEXTO_PRINCIPAL);
        return etiqueta;
    }

    public static JLabel crearTituloModulo(String texto) {
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(COLOR_TEXTO_PRINCIPAL);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        return titulo;
    }

    public static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setBackground(COLOR_FONDO_BLANCO);
        campo.setForeground(COLOR_TEXTO_PRINCIPAL);
        campo.setColumns(15);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0xCED4DA), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return campo;
    }

    public static void darEstiloTabla(JTable tabla) {
        JTableHeader cabecera = tabla.getTableHeader();
        cabecera.setOpaque(true);
        cabecera.setBackground(COLOR_MENU_LATERAL);
        cabecera.setForeground(COLOR_TEXTO_BLANCO);
        cabecera.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cabecera.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tablaActual,
                    Object valor,
                    boolean seleccionado,
                    boolean foco,
                    int fila,
                    int columna) {
                JLabel celda = (JLabel) super.getTableCellRendererComponent(
                        tablaActual, valor, seleccionado, foco, fila, columna);
                celda.setOpaque(true);
                celda.setBackground(COLOR_MENU_LATERAL);
                celda.setForeground(COLOR_TEXTO_BLANCO);
                celda.setFont(new Font("Segoe UI", Font.BOLD, 12));
                celda.setHorizontalAlignment(SwingConstants.CENTER);
                celda.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(0xDEE2E6)));
                return celda;
            }
        });

        tabla.setRowHeight(28);
        tabla.setBackground(COLOR_FONDO_BLANCO);
        tabla.setForeground(COLOR_TEXTO_PRINCIPAL);
        tabla.setSelectionBackground(COLOR_SELECCION_TABLA);
        tabla.setSelectionForeground(COLOR_TEXTO_BLANCO);
        tabla.setGridColor(new Color(0xDEE2E6));
        tabla.setShowGrid(true);
    }
}
