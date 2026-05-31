package co.edu.uptc.tiendaminorista.gui.administrador;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;
import co.edu.uptc.tiendaminorista.modelo.Producto;

public class PanelCompraCliente extends JPanel {

    private JComboBox<String> comboClientes;
    private JComboBox<String> comboProductos;
    private JTextField txtCantidad;
    private JTable tablaCompras;
    private DefaultTableModel modeloTabla;
    private Evento evento;

    private JLabel lblPrecioUnitario;
    private JLabel lblPrecioTotal;

    private List<Cliente> listaClientesAux;
    private List<Producto> listaProductosAux;

    public PanelCompraCliente(Evento e) {
        this.evento = e;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Registrar Compra de Cliente");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titulo);
        add(Box.createVerticalStrut(20));

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setMaximumSize(new Dimension(400, 210)); 

        panelFormulario.add(new JLabel("Seleccione el Cliente:"));
        comboClientes = new JComboBox<>();
        panelFormulario.add(comboClientes);
        panelFormulario.add(Box.createVerticalStrut(10));

        panelFormulario.add(new JLabel("Seleccione el Producto:"));
        comboProductos = new JComboBox<>();
        panelFormulario.add(comboProductos);
        panelFormulario.add(Box.createVerticalStrut(10));

        panelFormulario.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelFormulario.add(txtCantidad);
        panelFormulario.add(Box.createVerticalStrut(15));
        
        lblPrecioUnitario = new JLabel("Precio Unitario: $0.0");
        lblPrecioUnitario.setFont(lblPrecioUnitario.getFont().deriveFont(Font.BOLD));
        panelFormulario.add(lblPrecioUnitario);
        panelFormulario.add(Box.createVerticalStrut(5));

        lblPrecioTotal = new JLabel("Total Estimado: $0.0");
        lblPrecioTotal.setFont(lblPrecioTotal.getFont().deriveFont(Font.BOLD));
        panelFormulario.add(lblPrecioTotal);

        panelFormulario.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(panelFormulario);
        add(Box.createVerticalStrut(20));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        
        JButton btnAgregar = new JButton("Realizar Compra");
        btnAgregar.addActionListener(evento);
        btnAgregar.setActionCommand(Evento.REALIZARCOM);
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(evento);
        btnVolver.setActionCommand(Evento.VOLVER); 
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnVolver);
        panelBotones.setMaximumSize(new Dimension(400, 40));
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(panelBotones);
        add(Box.createVerticalStrut(20));

        String[] columnas = {"Cliente", "Producto", "Cantidad", "Total Compra", "Fecha"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCompras = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        scrollPane.setMaximumSize(new Dimension(850, 250));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scrollPane);

        comboProductos.addActionListener(evt -> actualizarValoresCalculados());

        txtCantidad.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent evt) { actualizarValoresCalculados(); }
            @Override
            public void removeUpdate(DocumentEvent evt) { actualizarValoresCalculados(); }
            @Override
            public void changedUpdate(DocumentEvent evt) { actualizarValoresCalculados(); }
        });
    }

    public void actualizarValoresCalculados() {
        Producto prodSel = getProductoSeleccionado();
        if (prodSel == null) {
            lblPrecioUnitario.setText("Precio Unitario: $0.0");
            lblPrecioTotal.setText("Total Estimado: $0.0");
            return;
        }

        double precioUnitario = prodSel.getPrecioVenta();
        lblPrecioUnitario.setText("Precio Unitario: $" + precioUnitario);

        try {
            String cantTexto = txtCantidad.getText().trim();
            if (cantTexto.isEmpty()) {
                lblPrecioTotal.setText("Total Estimado: $0.0");
            } else {
                int cantidad = Integer.parseInt(cantTexto);
                double total = precioUnitario * cantidad;
                lblPrecioTotal.setText("Total Estimado: $" + total);
            }
        } catch (NumberFormatException e) {
            lblPrecioTotal.setText("Total Estimado: (Cantidad inválida)");
        }
    }

    public void cargarClientesEnCombo(List<Cliente> clientes) {
        this.listaClientesAux = clientes;
        comboClientes.removeAllItems();
        if (clientes != null) {
            for (Cliente c : clientes) {
                if (c.isActivo()) {
                    comboClientes.addItem(c.getNombre() + " (" + c.getNumeroIdentificacion() + ")");
                }
            }
        }
    }

    public void cargarProductosEnCombo(List<Producto> productos) {
        this.listaProductosAux = productos;
        comboProductos.removeAllItems();
        if (productos != null) {
            for (Producto p : productos) {
                if (p.isActivo()) { 
                    comboProductos.addItem(p.getNombre() + " - $" + p.getPrecioVenta());
                }
            }
        }
        actualizarValoresCalculados();
    }

    public Cliente getClienteSeleccionado() {
        int index = comboClientes.getSelectedIndex();
        if (index >= 0 && index < listaClientesAux.size()) {
            return listaClientesAux.get(index);
        }
        return null;
    }

    public Producto getProductoSeleccionado() {
        int index = comboProductos.getSelectedIndex();
        if (index >= 0 && index < listaProductosAux.size()) {
            return listaProductosAux.get(index);
        }
        return null;
    }

    public int getCantidad() throws NumberFormatException {
        return Integer.parseInt(txtCantidad.getText().trim());
    }

    public void limpiarCampos() {
        txtCantidad.setText("");
        lblPrecioUnitario.setText("Precio Unitario: $0.0");
        lblPrecioTotal.setText("Total Estimado: $0.0");
    }

    public void actualizarTablaCompras(List<CompasCliente> compras) {
        modeloTabla.setRowCount(0);
        if (compras != null) {
            for (CompasCliente c : compras) {
                String nombreCli = (c.getCliente() != null) ? c.getCliente().getNombre() : "N/A";
                String nombreProd = (c.getProducto() != null) ? c.getProducto().getNombre() : "N/A";
                
                modeloTabla.addRow(new Object[]{
                    nombreCli,
                    nombreProd,
                    c.getCantidad(),
                    c.getTotalCompra(),
                    c.getFecha().toString()
                });
            }
        }
        tablaCompras.revalidate();
        tablaCompras.repaint();
    }
}