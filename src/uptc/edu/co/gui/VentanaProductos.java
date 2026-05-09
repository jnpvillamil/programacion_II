package uptc.edu.co.gui;

import uptc.edu.co.controller.ProductoController;
import uptc.edu.co.controller.UsuarioController.Resultado;
import uptc.edu.co.model.Producto;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;


public class VentanaProductos extends JFrame {

    
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JLabel     lblMensaje;

    
    private JTable           tabla;
    private DefaultTableModel modeloTabla;

    
    private final ProductoController controller = new ProductoController();

    
    private final Color AZUL   = new Color(24, 95, 200);
    private final Color VERDE  = new Color(40, 140, 60);
    private final Color ROJO   = new Color(180, 40, 40);
    private final Color GRIS   = new Color(100, 110, 130);
    private final Color BORDE  = new Color(200, 210, 225);
    private final Color EXITO  = new Color(34, 139, 34);
    private final Color ERROR  = new Color(190, 30, 30);
    private final Color AVISO  = new Color(180, 110, 0);

    public VentanaProductos() {
        setTitle("Gestión de Productos — CRUD");
        setSize(720, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        construirUI();
        cargarTabla(); 
    }

    private void construirUI() {
        setLayout(new BorderLayout(0, 0));

        
        JPanel panelTitulo = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0,0, AZUL, getWidth(), 0, new Color(16,70,160)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelTitulo.setPreferredSize(new Dimension(0, 50));

        JLabel lblTitulo = new JLabel("📦  Gestión de Productos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(16, 14, 320, 22);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        
        JPanel panelCentro = new JPanel(new BorderLayout(0, 8));
        panelCentro.setBackground(new Color(245, 247, 250));
        panelCentro.setBorder(new EmptyBorder(12, 12, 8, 12));
        add(panelCentro, BorderLayout.CENTER);

        
        JPanel formulario = new JPanel(new GridLayout(2, 4, 10, 6));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(new CompoundBorder(
            new LineBorder(BORDE, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));

        txtCodigo = campoFormulario("Código (ej: P001)");
        txtNombre = campoFormulario("Nombre del producto");
        txtPrecio = campoFormulario("Precio (ej: 3500)");
        txtStock  = campoFormulario("Stock (ej: 50)");

        formulario.add(etiqueta("Código *"));
        formulario.add(etiqueta("Nombre *"));
        formulario.add(etiqueta("Precio *"));
        formulario.add(etiqueta("Stock *"));
        formulario.add(txtCodigo);
        formulario.add(txtNombre);
        formulario.add(txtPrecio);
        formulario.add(txtStock);

        panelCentro.add(formulario, BorderLayout.NORTH);

        
        String[] columnas = {"Código", "Nombre", "Precio ($)", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(26);
        tabla.setSelectionBackground(new Color(210, 225, 250));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(230, 235, 245));

        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                cargarFilaEnFormulario(tabla.getSelectedRow());
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(new LineBorder(BORDE, 1, true));
        panelCentro.add(scroll, BorderLayout.CENTER);

        
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentro.add(lblMensaje, BorderLayout.SOUTH);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panelBotones.setBackground(new Color(236, 240, 245));
        panelBotones.setBorder(new MatteBorder(1, 0, 0, 0, BORDE));

        JButton btnGuardar   = boton("💾 Guardar",   VERDE);
        JButton btnActualizar = boton("✏ Actualizar", AZUL);
        JButton btnEliminar  = boton("🗑 Eliminar",   ROJO);
        JButton btnLimpiar   = boton("🔄 Limpiar",    GRIS);

        
        btnGuardar.addActionListener(e -> {
            Resultado r = controller.crear(
                txtCodigo.getText(), txtNombre.getText(),
                txtPrecio.getText(), txtStock.getText()
            );
            mostrarMensaje(r);
            if (r.exito) { limpiarFormulario(); cargarTabla(); }
        });

        
        btnActualizar.addActionListener(e -> {
            Resultado r = controller.actualizar(
                txtCodigo.getText(), txtNombre.getText(),
                txtPrecio.getText(), txtStock.getText()
            );
            mostrarMensaje(r);
            if (r.exito) { limpiarFormulario(); cargarTabla(); }
        });

        
        btnEliminar.addActionListener(e -> {
            String codigo = txtCodigo.getText().trim();
            if (codigo.isEmpty()) {
                mostrarMensaje(Resultado.error("Selecciona un producto de la tabla"));
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el producto " + codigo + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Resultado r = controller.eliminar(codigo);
                mostrarMensaje(r);
                if (r.exito) { limpiarFormulario(); cargarTabla(); }
            }
        });

        
        btnLimpiar.addActionListener(e -> { limpiarFormulario(); lblMensaje.setText(" "); });

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    
    private void cargarTabla() {
        modeloTabla.setRowCount(0); 
        try {
            List<Producto> productos = controller.listar();
            for (Producto p : productos) {
                modeloTabla.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    String.format("$ %,.2f", p.getPrecio()),
                    p.getStock()
                });
            }
        } catch (IOException e) {
            mostrarMensaje(Resultado.error("Error al cargar productos: " + e.getMessage()));
        }
    }

    
    private void cargarFilaEnFormulario(int fila) {
        txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        
        
        String precio = modeloTabla.getValueAt(fila, 2).toString()
                            .replace("$", "").replace(",", "").trim();
        txtPrecio.setText(precio);
        txtStock.setText(modeloTabla.getValueAt(fila, 3).toString());
        
        
        txtCodigo.setEditable(false);
    }

    
    
    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtCodigo.setEditable(true);
        tabla.clearSelection();
    }

    
    private void mostrarMensaje(Resultado r) {
        lblMensaje.setForeground(r.exito ? EXITO : ERROR);
        lblMensaje.setText(r.exito ? "✔  " + r.mensaje : "✖  " + r.mensaje);
    }

    
    private JTextField campoFormulario(String placeholder) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setBorder(new CompoundBorder(
            new LineBorder(BORDE, 1, true),
            new EmptyBorder(2, 6, 2, 6)
        ));
        tf.setToolTipText(placeholder);
        return tf;
    }

    private JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(new Color(60, 80, 120));
        return l;
    }

    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isEnabled() ? (getModel().isRollover() ? color.darker() : color)
                                       : new Color(180, 190, 210));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(130, 32));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
