package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import co.edu.uptc.gui.modelo.Venta;
import co.edu.uptc.conexion.Conexion;

@SuppressWarnings("serial")
public class VentanaVenta extends JFrame implements ActionListener {

    private JTextField campoFactura, campoPrecio, campoCantidad, campoTotal;
    private JComboBox<ComboItem> comboCliente;
    private JComboBox<ComboItem> comboProducto;
    private JButton botonFichar, botonLimpiar;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;



    static class ComboItem {
        private String id;
        private String descripcion;

        public ComboItem(String id, String descripcion) {
            this.id = id;
            this.descripcion = descripcion;
        }

        public String getId() { return id; }

        @Override
        public String toString() {
            return id + " - " + descripcion;
        }
    }

    public VentanaVenta() {
        setTitle("Módulo de Facturación y Ventas");
        setSize(1020, 680); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(17, 17));

        iniciarComponentesFormulario();
        iniciarComponenteTabla();
        actualizarListasDesplegables();
    }

    public void actualizarListasDesplegables() {
        cargarListaClientes();
        cargarListaProductos();
        generarConsecutivoFacturaTexto();
    }

    private void iniciarComponentesFormulario() {
        javax.swing.border.Border bordeLineaGrueso = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);

        JPanel panelForm = new JPanel(new GridLayout(3, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder(bordeLineaGrueso, " Registrar Nueva Venta (Datos de Facturación) "));

        panelForm.add(new JLabel("N° Factura:", SwingConstants.RIGHT));
        campoFactura = new JTextField();
        campoFactura.setEditable(false);
        campoFactura.setBackground(new Color(240, 240, 240));
        panelForm.add(campoFactura);

        panelForm.add(new JLabel("Código Cliente:", SwingConstants.RIGHT));
        comboCliente = new JComboBox<>(); 
        panelForm.add(comboCliente);

        panelForm.add(new JLabel("Código Producto:", SwingConstants.RIGHT));
        comboProducto = new JComboBox<>(); 
        comboProducto.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    obtenerPrecioProductoBD();
                }
            }
        });
        panelForm.add(comboProducto);

        panelForm.add(new JLabel("Precio Unitario ($):", SwingConstants.RIGHT));
        campoPrecio = new JTextField();
        campoPrecio.setEditable(false);
        campoPrecio.setBackground(new Color(240, 240, 240));
        panelForm.add(campoPrecio);

        panelForm.add(new JLabel("Cantidad:", SwingConstants.RIGHT));
        campoCantidad = new JTextField();
        campoCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalVenta();
            }
        });
        panelForm.add(campoCantidad);

        panelForm.add(new JLabel("Total Venta ($):", SwingConstants.RIGHT));
        campoTotal = new JTextField();
        campoTotal.setEditable(false);
        campoTotal.setBackground(new Color(245, 245, 225)); 
        campoTotal.setFont(campoTotal.getFont().deriveFont(Font.BOLD));
        panelForm.add(campoTotal);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botonFichar = new JButton(" FACTURAR ");
        botonLimpiar = new JButton("Limpiar Formulario");

        botonFichar.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelAcciones.add(botonFichar);
        panelAcciones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(panelForm, BorderLayout.CENTER);
        contenedorSuperior.add(panelAcciones, BorderLayout.SOUTH);
        contenedorSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarComponenteTabla() {
        String[] columnas = {"N° Factura", "Cod. Cliente", "Cod. Producto", "Cantidad", "Total Facturado", "Fecha Registro"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaVentas = new JTable(modeloTabla);
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaVentas.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(tablaVentas);
        scroll.setBorder(BorderFactory.createTitledBorder(" Historial de Ventas Registradas en el Turno "));
        
        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        contenedorTabla.add(scroll, BorderLayout.CENTER);

        add(contenedorTabla, BorderLayout.CENTER);
    }

    private Connection obtenerConexion() throws SQLException {
        Conexion conexionProyecto = new Conexion();
        return conexionProyecto.getConnection();
    }

    private void cargarListaClientes() {
        comboCliente.removeAllItems();
        String sql = "SELECT codigo, nombre FROM cliente ORDER BY codigo";
        try (Connection con = obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                comboCliente.addItem(new ComboItem(rs.getString("codigo"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar lista de clientes: " + e.getMessage());
        }
    }

    private void cargarListaProductos() {
        comboProducto.removeAllItems();
        comboProducto.addItem(new ComboItem("", "-- Seleccione --"));
        String sql = "SELECT codigo, nombre FROM producto ORDER BY codigo";
        try (Connection con = obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                comboProducto.addItem(new ComboItem(rs.getString("codigo"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar lista de productos: " + e.getMessage());
        }
    }

    private void generarConsecutivoFacturaTexto() {
        String sql = "SELECT numero_factura FROM factura_venta ORDER BY numero_factura DESC LIMIT 1";
        try (Connection con = obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                String ultimoCod = rs.getString("numero_factura"); 
                String[] partes = ultimoCod.split("-");
                int numero = Integer.parseInt(partes[1]) + 1;
                campoFactura.setText(String.format("FAC-%03d", numero)); 
            } else {
                campoFactura.setText("FAC-001");
            }
        } catch (Exception e) {
            campoFactura.setText("FAC-001");
        }
    }

    private void obtenerPrecioProductoBD() {
        ComboItem item = (ComboItem) comboProducto.getSelectedItem();
        if (item == null || item.getId().isEmpty()) {
            campoPrecio.setText("");
            campoTotal.setText("");
            return;
        }
        String sql = "SELECT precio_venta FROM producto WHERE codigo = ?";
        try (Connection con = obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, item.getId());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    campoPrecio.setText(String.valueOf(rs.getDouble("precio_venta")));
                    calcularTotalVenta();
                }
            }
        } catch (Exception e) {
            System.out.println("Error consultando precio: " + e.getMessage());
        }
    }

    private void calcularTotalVenta() {
        try {
            if (!campoPrecio.getText().isEmpty() && !campoCantidad.getText().isEmpty()) {
                double precio = Double.parseDouble(campoPrecio.getText());
                int cantidad = Integer.parseInt(campoCantidad.getText());
                campoTotal.setText(String.valueOf(precio * cantidad));
            } else {
                campoTotal.setText("");
            }
        } catch (NumberFormatException ex) {
            campoTotal.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) {
            limpiarCampos();
            return;
        }

        if (e.getSource() == botonFichar) {
            ComboItem clienteSel = (ComboItem) comboCliente.getSelectedItem();
            ComboItem productoSel = (ComboItem) comboProducto.getSelectedItem();
            
            if (productoSel == null || productoSel.getId().isEmpty() || clienteSel == null || campoCantidad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos de la venta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String fechaHoraActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                Venta venta = new Venta(
                        campoFactura.getText(),
                        clienteSel.getId(),
                        productoSel.getId(),
                        Integer.parseInt(campoCantidad.getText()),
                        Double.parseDouble(campoTotal.getText())
                );

                venta.registrarVenta();

                Object[] fila = {
                    venta.getCodigoFactura(),
                    venta.getCodigoCliente(),
                    venta.getCodigoProducto(),
                    venta.getCantidad(),
                    venta.getTotal(),
                    fechaHoraActual 
                };
                modeloTabla.addRow(fila);
                
                JOptionPane.showMessageDialog(this, "¡Venta registrada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Verifique los valores de precio y cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        campoPrecio.setText("");
        campoCantidad.setText("");
        campoTotal.setText("");
        actualizarListasDesplegables();
    }
}