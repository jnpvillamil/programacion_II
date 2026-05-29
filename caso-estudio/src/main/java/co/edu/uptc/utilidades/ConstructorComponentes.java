package co.edu.uptc.utilidades;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConstructorComponentes {

    public static final Color AZUL_OSCURO = Color.decode("#1A237E");
    public static final Color GRIS_FONDO = Color.decode("#ECECEC");
    public static final Color COLOR_HOVER = Color.decode("#283593");
    public static final Color VERDE_GUARDAR = Color.decode("#2E7D32");
    public static final Color VERDE_GUARDAR_HOVER = Color.decode("#388E3C");
    public static final Color ROJO_PELIGRO = Color.decode("#C62828");
    public static final Color ROJO_PELIGRO_HOVER = Color.decode("#D32F2F");
    public static final String FUENTE_TITULO = "SansSerif";
    public static final int TAMANIO_TITULO = 18;
    public static final String FUENTE_UI = "SansSerif";
    public static final int TAMANIO_LABEL = 14;
    public static final int TAMANIO_CAMPO = 14;
    public static final int ALTURA_CAMPO = 32;
    public static final int TAMANIO_PESTANA = 15;
    public static final int TAMANIO_LOGIN_LABEL = 13;
    public static final int TAMANIO_LOGIN_CAMPO = 13;
    public static final int ALTURA_LOGIN_CAMPO = 30;

    public static JButton crearBotonPrimario(String texto) {
        return crearBotonEstilizado(texto, AZUL_OSCURO, COLOR_HOVER);
    }

    public static JButton crearBotonGuardar(String texto) {
        return crearBotonEstilizado(texto, VERDE_GUARDAR, VERDE_GUARDAR_HOVER);
    }

    public static JButton crearBotonPeligro(String texto) {
        return crearBotonEstilizado(texto, ROJO_PELIGRO, ROJO_PELIGRO_HOVER);
    }

    private static JButton crearBotonEstilizado(String texto, Color fondo, Color hover) {
        JButton boton = new JButton(texto.toUpperCase());
        boton.setBackground(fondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(fondo);
            }
        });

        return boton;
    }

    public static JLabel crearLabelTitulo(String texto) {
        JLabel label = new JLabel(texto.toUpperCase());
        label.setFont(new Font(FUENTE_TITULO, Font.BOLD, TAMANIO_TITULO));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static JLabel crearLabelFormulario(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font(FUENTE_UI, Font.PLAIN, TAMANIO_LABEL));
        return label;
    }

    public static Border crearBordeSeccion(String titulo) {
        Border borde = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        TitledBorder tituloBorde = BorderFactory.createTitledBorder(borde, titulo);
        tituloBorde.setTitleFont(new Font(FUENTE_UI, Font.BOLD, TAMANIO_LABEL));
        return BorderFactory.createCompoundBorder(
                tituloBorde,
                BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    public static Border crearBordeCampoLogin() {
        Border bordeSuave = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true);
        return BorderFactory.createCompoundBorder(
                bordeSuave,
                BorderFactory.createEmptyBorder(4, 8, 4, 8));
    }

    public static JLabel crearLabelLogin(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font(FUENTE_UI, Font.PLAIN, TAMANIO_LOGIN_LABEL));
        return label;
    }

    public static JTextField crearCampoLogin() {
        JTextField campo = new JTextField();
        campo.setBackground(Color.WHITE);
        campo.setFont(new Font(FUENTE_UI, Font.PLAIN, TAMANIO_LOGIN_CAMPO));
        campo.setBorder(crearBordeCampoLogin());
        campo.setPreferredSize(new Dimension(280, ALTURA_LOGIN_CAMPO));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, ALTURA_LOGIN_CAMPO));
        return campo;
    }

    public static JPasswordField crearCampoClaveLogin() {
        JPasswordField campo = new JPasswordField();
        campo.setBackground(Color.WHITE);
        campo.setFont(new Font(FUENTE_UI, Font.PLAIN, TAMANIO_LOGIN_CAMPO));
        campo.setBorder(crearBordeCampoLogin());
        campo.setPreferredSize(new Dimension(280, ALTURA_LOGIN_CAMPO));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, ALTURA_LOGIN_CAMPO));
        return campo;
    }

    public static JTextField crearCampoTexto() {
        JTextField campoTexto = new JTextField();
        campoTexto.setBackground(Color.WHITE);
        campoTexto.setFont(new Font(FUENTE_UI, Font.PLAIN, TAMANIO_CAMPO));
        Border bordeSuave = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true);
        campoTexto.setBorder(BorderFactory.createCompoundBorder(
                bordeSuave,
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        campoTexto.setPreferredSize(new Dimension(campoTexto.getPreferredSize().width, ALTURA_CAMPO));
        return campoTexto;
    }

    public static JComboBox<String> crearComboBox(String[] opciones) {
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setFont(new Font(FUENTE_UI, Font.PLAIN, TAMANIO_CAMPO));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(combo.getPreferredSize().width, ALTURA_CAMPO));
        return combo;
    }

    @SafeVarargs
    public static <E extends Enum<E>> JComboBox<E> crearComboBoxEnum(E... valores) {
        JComboBox<E> combo = new JComboBox<>(valores);
        combo.setFont(new Font(FUENTE_UI, Font.PLAIN, TAMANIO_CAMPO));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(combo.getPreferredSize().width, ALTURA_CAMPO));
        return combo;
    }

    public static JTabbedPane crearPestanas() {
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font(FUENTE_UI, Font.BOLD, TAMANIO_PESTANA));
        pestanas.setBackground(GRIS_FONDO);
        pestanas.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return pestanas;
    }

    public static JButton crearBotonMenu(String texto) {
        JButton boton = crearBotonEstilizado(texto, AZUL_OSCURO, COLOR_HOVER);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        return boton;
    }

    public static void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font(FUENTE_UI, Font.PLAIN, TAMANIO_CAMPO));
        tabla.setRowHeight(30);
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);
        tabla.setGridColor(Color.LIGHT_GRAY);

        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setFont(new Font(FUENTE_UI, Font.BOLD, TAMANIO_LABEL));
        encabezado.setReorderingAllowed(false);
        encabezado.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(AZUL_OSCURO);
                setForeground(Color.WHITE);
                setOpaque(true);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
    }
}
