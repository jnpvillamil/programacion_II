package co.edu.uptc.Tiendaminorista.Administrador.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import co.edu.uptc.Tiendaminorista.Gui.Evento;
import co.edu.uptc.Tiendaminorista.Gui.PanelPrincipal;
import co.edu.uptc.Tiendaminorista.modelo.Producto;
import co.edu.uptc.Tiendaminorista.modelo.Proveedor;
import co.edu.uptc.Tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.Tiendaminorista.negocio.GestionProveedor;

public class PanelComprasPro extends JPanel implements ActionListener {

    private JComboBox<Proveedor> comboProveedores;
    private JComboBox<Producto> comboProductos;
    private JTextField txtCantidad, txtPrecioCompra;
    private JTextArea areaLista;
    private GestionProveedor gestionProveedor;
    private GestionProducto gestionProducto;
    private JButton btnComprar, btnVolver;

    public PanelComprasPro(Evento e, GestionProveedor gestionProveedor, GestionProducto gestionProducto) {
        this.gestionProveedor = gestionProveedor;
        this.gestionProducto = gestionProducto;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("COMPRAS A PROVEEDORES", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Compra"));

        comboProveedores = new JComboBox<>();
        comboProductos = new JComboBox<>();
        txtCantidad = new JTextField();
        txtPrecioCompra = new JTextField();
        txtPrecioCompra.setEditable(false);

        panelFormulario.add(new JLabel("Proveedor:"));
        panelFormulario.add(comboProveedores);
        panelFormulario.add(new JLabel("Producto:"));
        panelFormulario.add(comboProductos);
        panelFormulario.add(new JLabel("Cantidad:"));
        panelFormulario.add(txtCantidad);
        panelFormulario.add(new JLabel("Precio Unitario:"));
        panelFormulario.add(txtPrecioCompra);

        btnComprar = new JButton("COMPRAR");
        btnComprar.addActionListener(this);
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnComprar);

        // Área de texto para mostrar información
        areaLista = new JTextArea();
        areaLista.setEditable(false);
        areaLista.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Panel inferior con botón volver
        JPanel panelInferior = new JPanel();
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e);
        btnVolver.setActionCommand(Evento.CANCELAR);
        panelInferior.add(btnVolver);

        add(panelFormulario, BorderLayout.CENTER);
        add(new JScrollPane(areaLista), BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);

        cargarDatos();
        
        comboProductos.addActionListener(evt -> actualizarPrecioCompra());
    }

    private void cargarDatos() {
        List<Proveedor> proveedores = gestionProveedor.listarProveedores();
        comboProveedores.removeAllItems();
        for (Proveedor p : proveedores) {
            if (p.isActivo()) {
                comboProveedores.addItem(p);
            }
        }

        actualizarListaProductos();
        actualizarAreaTexto();
    }

    private void actualizarListaProductos() {
        List<Producto> productos = gestionProducto.listarProductos();
        comboProductos.removeAllItems();
        for (Producto p : productos) {
            if (p.isActivo()) {
                comboProductos.addItem(p);
            }
        }
    }

    private void actualizarPrecioCompra() {
        Producto p = (Producto) comboProductos.getSelectedItem();
        if (p != null) {
            txtPrecioCompra.setText(String.format("%,.0f", p.getPrecioCompra()));
        }
    }

    private void actualizarAreaTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("-COMPRAS RECIENTES:\n");
        sb.append("(Aqui irán las compras registradas)\n");
        sb.append("INSTRUCCIONES:\n");
        sb.append("1.Seleccione proveedor\n");
        sb.append("2.Seleccione producto\n");
        sb.append("3.Ingrese cantidad\n");
        sb.append("4.Presione COMPRAR\n");
        areaLista.setText(sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("COMPRAR")) {
            realizarCompra();
        }
    }

    private void realizarCompra() {
        Proveedor proveedor = (Proveedor) comboProveedores.getSelectedItem();
        Producto producto = (Producto) comboProductos.getSelectedItem();

        if (proveedor == null || producto == null) {
            JOptionPane.showMessageDialog(this, "Seleccione proveedor y producto");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida");
            return;
        }

        double totalCompra = producto.getPrecioCompra() * cantidad;

        int confirm = JOptionPane.showConfirmDialog(this,
            String.format("Confirmar compra:\nProveedor: %s\nProducto: %s\nCantidad: %d\nTotal: $%,.0f",
                proveedor.getNombre(), producto.getNombre(), cantidad, totalCompra),
            "Confirmar Compra", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        producto.setStockActual(producto.getStockActual() + cantidad);
        gestionProducto.actualizarProducto(producto);

        PanelPrincipal ventana = (PanelPrincipal) SwingUtilities.getWindowAncestor(this);
        if (ventana != null) {
            ventana.registrarCompra(producto.getNombre(), totalCompra, proveedor.getNit());
        }

        txtCantidad.setText("");
        actualizarListaProductos();
        
        JOptionPane.showMessageDialog(this,
            String.format("Compra registrada!\nProveedor: %s\nProducto: %s\nCantidad: %d\nTotal: $%,.0f",
                proveedor.getNombre(), producto.getNombre(), cantidad, totalCompra));
    }
}