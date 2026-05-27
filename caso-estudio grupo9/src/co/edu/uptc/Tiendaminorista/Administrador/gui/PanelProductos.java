package co.edu.uptc.Tiendaminorista.Administrador.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import co.edu.uptc.Tiendaminorista.modelo.Producto;
import co.edu.uptc.Tiendaminorista.negocio.GestionProducto;

public class PanelProductos extends JPanel implements ActionListener {

    private JTextField textoDelCodigo, textoDelNombre, textoCategoria;
    private JTextField textoPrecioCompra, textoPrecioVenta, textoStockActual, textoStockMinimo;
    private JTextArea areaListaProductos;
    private GestionProducto gestionProducto;

    public PanelProductos(GestionProducto gestionProducto) {
        this.gestionProducto = gestionProducto;
        inicio();
        actualizarVista(); // Carga los productos apenas se abre el panel
    }

    private void inicio() {
        setLayout(new BorderLayout());

        // Panel de Formulario (Norte)
        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Productos"));

        textoDelNombre = new JTextField();
        textoCategoria = new JTextField();
        textoPrecioCompra = new JTextField();
        textoPrecioVenta = new JTextField();
        textoStockActual = new JTextField();
        textoStockMinimo = new JTextField();
        textoDelCodigo = new JTextField();

        panelFormulario.add(new JLabel("Nombre:")); panelFormulario.add(textoDelNombre);
        panelFormulario.add(new JLabel("Categoría:")); panelFormulario.add(textoCategoria);
        panelFormulario.add(new JLabel("Precio Compra:")); panelFormulario.add(textoPrecioCompra);
        panelFormulario.add(new JLabel("Precio Venta:")); panelFormulario.add(textoPrecioVenta);
        panelFormulario.add(new JLabel("Stock Actual:")); panelFormulario.add(textoStockActual);
        panelFormulario.add(new JLabel("Stock Mínimo:")); panelFormulario.add(textoStockMinimo);
        panelFormulario.add(new JLabel("Código (Para Buscar/Modificar):")); panelFormulario.add(textoDelCodigo);

        // Botones
        JButton btnRegistrar = new JButton("REGISTRAR");
        JButton btnActualizar = new JButton("ACTUALIZAR");
        JButton btnBuscar = new JButton("BUSCAR");
        
        btnRegistrar.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnBuscar.addActionListener(this);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnActualizar);
        panelFormulario.add(panelBotones);

        // Área de lista (Centro)
        areaListaProductos = new JTextArea();
        areaListaProductos.setEditable(false);
        areaListaProductos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(areaListaProductos), BorderLayout.CENTER);
    }

    public void actualizarVista() {
        List<Producto> lista = gestionProducto.listarProductos();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-20s %-15s %-10s %-10s\n", "CÓDIGO", "NOMBRE", "CATEGORÍA", "STOCK", "ESTADO"));
        sb.append("----------------------------------------------------------------------\n");
        for (Producto p : lista) {
            sb.append(String.format("%-10s %-20s %-15s %-10d %-10s\n", 
                p.getCodigo(), p.getNombre(), p.getCategoria(), p.getStockActual(), (p.isActivo()?"ACTIVO":"INACTIVO")));
        }
        areaListaProductos.setText(sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("REGISTRAR")) {
            registrar();
        } else if (comando.equals("BUSCAR")) {
            buscar();
        } else if (comando.equals("ACTUALIZAR")) {
            actualizar();
        }
    }

    private void registrar() {
        Producto p = extraerDatos();
        gestionProducto.registrarProducto(p);
        JOptionPane.showMessageDialog(this, "Producto registrado");
        limpiarCampos();
        actualizarVista();
    }

    private void buscar() {
        String cod = textoDelCodigo.getText();
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
        JOptionPane.showMessageDialog(this, "No encontrado");
    }

    private void actualizar() {
        Producto p = extraerDatos();
        p.setCodigo(textoDelCodigo.getText()); // Mantenemos el código original
        gestionProducto.actualizarProducto(p);
        JOptionPane.showMessageDialog(this, "Producto actualizado");
        actualizarVista();
    }

    private Producto extraerDatos() {
        Producto p = new Producto();
        p.setNombre(textoDelNombre.getText());
        p.setCategoria(textoCategoria.getText());
        p.setPrecioCompra(Double.parseDouble(textoPrecioCompra.getText()));
        p.setPrecioVenta(Double.parseDouble(textoPrecioVenta.getText()));
        p.setStockActual(Integer.parseInt(textoStockActual.getText()));
        p.setStockMinimo(Integer.parseInt(textoStockMinimo.getText()));
        p.setActivo(true);
        return p;
    }

    private void limpiarCampos() {
        textoDelNombre.setText("");
        textoCategoria.setText("");
        textoPrecioCompra.setText("");
        textoPrecioVenta.setText("");
        textoStockActual.setText("");
        textoStockMinimo.setText("");
        textoDelCodigo.setText("");
    }
}