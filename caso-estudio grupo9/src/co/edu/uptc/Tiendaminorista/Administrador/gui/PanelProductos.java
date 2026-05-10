package co.edu.uptc.Tiendaminorista.Administrador.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import co.edu.uptc.Tiendaminorista.modelo.Producto;
import co.edu.uptc.Tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.Tiendaminorista.Gui.PanelPrincipal;

public class PanelProductos extends JPanel implements ActionListener {

    private JTextField textoDelCodigo, textoDelNombre, textoCategoria;
    private JTextField textoPrecioCompra, textoPrecioVenta, textoStockActual, textoStockMinimo;
    private JTextField textoCantidadVenta;
    private JTextArea areaListaProductos;
    private GestionProducto gestionProducto;
    private JComboBox<Producto> comboProductos;

    public PanelProductos(GestionProducto gestionProducto) {
        this.gestionProducto = gestionProducto;
        inicio();
        actualizarVista();
    }

    private void inicio() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de Formulario (Norte)
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Productos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        textoDelNombre = new JTextField(15);
        textoCategoria = new JTextField(15);
        textoPrecioCompra = new JTextField(10);
        textoPrecioVenta = new JTextField(10);
        textoStockActual = new JTextField(10);
        textoStockMinimo = new JTextField(10);
        textoDelCodigo = new JTextField(10);
        textoCantidadVenta = new JTextField(10);
        comboProductos = new JComboBox<>();
        comboProductos.setPreferredSize(new Dimension(200, 25));

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(textoDelNombre, gbc);
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(textoCategoria, gbc);

        // Fila 2
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Precio Compra:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(textoPrecioCompra, gbc);
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Precio Venta:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(textoPrecioVenta, gbc);

        // Fila 3
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Stock Actual:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(textoStockActual, gbc);
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Stock Mínimo:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(textoStockMinimo, gbc);

        // Fila 4
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Código (Buscar):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(textoDelCodigo, gbc);
        gbc.gridx = 2;
        panelFormulario.add(new JLabel("Seleccionar Producto:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(comboProductos, gbc);

        // Fila 5
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Cantidad a Vender:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(textoCantidadVenta, gbc);

        // Botones
        JButton btnRegistrar = new JButton("REGISTRAR");
        JButton btnActualizar = new JButton("ACTUALIZAR");
        JButton btnBuscar = new JButton("BUSCAR");
        JButton btnVender = new JButton("VENDER");

        btnRegistrar.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnBuscar.addActionListener(this);
        btnVender.addActionListener(this);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVender);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 4;
        panelFormulario.add(panelBotones, gbc);

        // Área de lista (Centro)
        areaListaProductos = new JTextArea();
        areaListaProductos.setEditable(false);
        areaListaProductos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(areaListaProductos), BorderLayout.CENTER);
    }

    public void actualizarVista() {
        List<Producto> lista = gestionProducto.listarProductos();
        
        comboProductos.removeAllItems();
        for (Producto p : lista) {
            if (p.isActivo() && p.getStockActual() > 0) {
                comboProductos.addItem(p);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s %-20s %-12s %-8s %-10s %-8s\n", 
            "CÓDIGO", "NOMBRE", "CATEGORÍA", "STOCK", "P.VENTA", "ESTADO"));
        sb.append("--------------------------------------------------------------------------------\n");
        for (Producto p : lista) {
            String nombre = p.getNombre() != null ? p.getNombre() : "";
            String categoria = p.getCategoria() != null ? p.getCategoria() : "";
            if (nombre.length() > 20) nombre = nombre.substring(0, 17) + "...";
            if (categoria.length() > 12) categoria = categoria.substring(0, 9) + "...";
            
            sb.append(String.format("%-8s %-20s %-12s %-8d $%-9.0f %-8s\n", 
                p.getCodigo(), nombre, categoria, p.getStockActual(), 
                p.getPrecioVenta(), (p.isActivo() ? "ACTIVO" : "INACTIVO")));
        }
        areaListaProductos.setText(sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "REGISTRAR":
                registrar();
                break;
            case "BUSCAR":
                buscar();
                break;
            case "ACTUALIZAR":
                actualizar();
                break;
            case "VENDER":
                vender();
                break;
        }
    }

    private boolean validarCamposRegistro() {
        if (textoDelNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return false;
        }
        if (textoCategoria.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La categoría es obligatoria");
            return false;
        }
        try {
            Double.parseDouble(textoPrecioCompra.getText().trim());
            Double.parseDouble(textoPrecioVenta.getText().trim());
            Integer.parseInt(textoStockActual.getText().trim());
            Integer.parseInt(textoStockMinimo.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los campos numéricos deben tener valores válidos");
            return false;
        }
        return true;
    }

    private void registrar() {
        if (!validarCamposRegistro()) return;
        
        try {
            Producto p = new Producto();
            p.setNombre(textoDelNombre.getText().trim());
            p.setCategoria(textoCategoria.getText().trim());
            p.setPrecioCompra(Double.parseDouble(textoPrecioCompra.getText().trim()));
            p.setPrecioVenta(Double.parseDouble(textoPrecioVenta.getText().trim()));
            p.setStockActual(Integer.parseInt(textoStockActual.getText().trim()));
            p.setStockMinimo(Integer.parseInt(textoStockMinimo.getText().trim()));
            p.setActivo(true);
            
            gestionProducto.registrarProducto(p);
            JOptionPane.showMessageDialog(this, "Producto registrado correctamente");
            limpiarCampos();
            actualizarVista();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
        }
    }

    private void buscar() {
        String cod = textoDelCodigo.getText().trim();
        if (cod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código para buscar");
            return;
        }
        
        for (Producto p : gestionProducto.listarProductos()) {
            if (p.getCodigo().equals(cod)) {
                textoDelNombre.setText(p.getNombre());
                textoCategoria.setText(p.getCategoria());
                textoPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
                textoPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
                textoStockActual.setText(String.valueOf(p.getStockActual()));
                textoStockMinimo.setText(String.valueOf(p.getStockMinimo()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Producto no encontrado");
    }

    private void actualizar() {
        String codigo = textoDelCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero busque un producto para actualizar");
            return;
        }
        
        if (!validarCamposRegistro()) return;
        
        try {
            Producto p = new Producto();
            p.setCodigo(codigo);
            p.setNombre(textoDelNombre.getText().trim());
            p.setCategoria(textoCategoria.getText().trim());
            p.setPrecioCompra(Double.parseDouble(textoPrecioCompra.getText().trim()));
            p.setPrecioVenta(Double.parseDouble(textoPrecioVenta.getText().trim()));
            p.setStockActual(Integer.parseInt(textoStockActual.getText().trim()));
            p.setStockMinimo(Integer.parseInt(textoStockMinimo.getText().trim()));
            p.setActivo(true);
            
            gestionProducto.actualizarProducto(p);
            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente");
            limpiarCampos();
            actualizarVista();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void vender() {
        Producto producto = (Producto) comboProductos.getSelectedItem();
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }
        
        int cantidad;
        try {
            cantidad = Integer.parseInt(textoCantidadVenta.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida");
            return;
        }
        
        if (cantidad > producto.getStockActual()) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + producto.getStockActual());
            return;
        }
        
        double totalVenta = producto.getPrecioVenta() * cantidad;
        
        String idCliente = JOptionPane.showInputDialog(this, "ID del cliente (opcional):");
        if (idCliente == null || idCliente.trim().isEmpty()) {
            idCliente = "VENTA_DIRECTA";
        }
        
        // Actualizar stock
        producto.setStockActual(producto.getStockActual() - cantidad);
        gestionProducto.actualizarProducto(producto);
        
        // Registrar en contabilidad
        PanelPrincipal ventana = (PanelPrincipal) SwingUtilities.getWindowAncestor(this);
        if (ventana != null) {
            ventana.registrarVenta(producto.getNombre(), totalVenta, idCliente);
        }
        
        textoCantidadVenta.setText("");
        actualizarVista();
        
        JOptionPane.showMessageDialog(this, 
            String.format("Venta exitosa!\nProducto: %s\nCantidad: %d\nTotal: $%,.0f", 
                producto.getNombre(), cantidad, totalVenta));
    }

    private void limpiarCampos() {
        textoDelNombre.setText("");
        textoCategoria.setText("");
        textoPrecioCompra.setText("");
        textoPrecioVenta.setText("");
        textoStockActual.setText("");
        textoStockMinimo.setText("");
        textoDelCodigo.setText("");
        textoCantidadVenta.setText("");
    }
}