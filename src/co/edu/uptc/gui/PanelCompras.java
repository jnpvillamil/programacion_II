package co.edu.uptc.gui;

import co.edu.uptc.negocio.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class PanelCompras extends JPanel {
    private SistemaGestion sis;
    private Compra compraTemporal;
    private DefaultTableModel modeloDetalles;
    private JLabel lblTotal;
    
    private JTextField txtFactura, txtCant, txtCosto;
    private JComboBox<Proveedor> cbProveedores;
    private JComboBox<Producto> cbProductos;

    public PanelCompras(SistemaGestion sis) {
        this.sis = sis;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(244, 246, 249));

        JLabel lblTitulo = new JLabel("Módulo de Compras (Ingreso)");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(44, 62, 80));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelNorte = new JPanel(new GridLayout(3, 4, 10, 10));
        panelNorte.setOpaque(false);
        
        txtFactura = new JTextField(); cbProveedores = new JComboBox<>();
        cbProductos = new JComboBox<>(); txtCant = new JTextField(); txtCosto = new JTextField();
        
        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.setBackground(new Color(52, 152, 219)); 
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setOpaque(true); //FIX MAC
        btnAgregar.setBorderPainted(false); //FIX MAC

        panelNorte.add(crearLabel("N° Factura Prov:")); panelNorte.add(txtFactura);
        panelNorte.add(crearLabel("Proveedor:")); panelNorte.add(cbProveedores);
        panelNorte.add(crearLabel("Producto:")); panelNorte.add(cbProductos);
        panelNorte.add(crearLabel("Cantidad:")); panelNorte.add(txtCant);
        panelNorte.add(crearLabel("Costo Unit:")); panelNorte.add(txtCosto);
        panelNorte.add(new JLabel("")); panelNorte.add(btnAgregar);

        add(panelNorte, BorderLayout.NORTH);

        modeloDetalles = new DefaultTableModel(new String[]{"Producto", "Cantidad", "Costo U.", "Subtotal"}, 0);
        JTable tablaDetalles = new JTable(modeloDetalles);
        tablaDetalles.setRowHeight(25);
        tablaDetalles.getTableHeader().setBackground(new Color(44, 62, 80));
        tablaDetalles.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelSur.setOpaque(false);
        
        lblTotal = new JLabel("Total Compra: $0.0");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTotal.setForeground(Color.BLACK); // Contraste
        
        JButton btnRegistrarCompra = new JButton("Registrar Compra");
        btnRegistrarCompra.setBackground(new Color(46, 204, 113));
        btnRegistrarCompra.setForeground(Color.WHITE);
        btnRegistrarCompra.setOpaque(true); // FIX MAC
        btnRegistrarCompra.setBorderPainted(false); //FIX MAC
        
        panelSur.add(lblTotal);
        panelSur.add(btnRegistrarCompra);
        add(panelSur, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> {
            try {
                ValidadorDatos.validarCadenaNoVacia(txtFactura.getText(), "N° Factura");
                Proveedor prov = (Proveedor) cbProveedores.getSelectedItem();
                Producto prod = (Producto) cbProductos.getSelectedItem();
                
                if (prov == null || prod == null) throw new Exception("Seleccione Proveedor y Producto.");

                if (compraTemporal == null) {
                    compraTemporal = new Compra(txtFactura.getText(), new Date(), prov);
                    txtFactura.setEnabled(false); cbProveedores.setEnabled(false);
                }

                int cant = ValidadorDatos.validarEnteroPositivo(txtCant.getText(), "Cantidad");
                double costo = ValidadorDatos.validarDecimalPositivo(txtCosto.getText(), "Costo Unitario");

                DetalleTransaccion detalle = new DetalleTransaccion(prod, cant, costo);
                compraTemporal.agregarDetalle(detalle);

                modeloDetalles.addRow(new Object[]{prod.getNombreProducto(), cant, costo, detalle.getSubtotal()});
                lblTotal.setText("Total Compra: $" + compraTemporal.calcularTotal());
                
                txtCant.setText(""); txtCosto.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Alerta", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnRegistrarCompra.addActionListener(e -> {
            try {
                if (compraTemporal == null) throw new Exception("No hay compra para registrar.");
                sis.getGestorCompra().registrarCompra(compraTemporal);
                JOptionPane.showMessageDialog(this, "Compra Registrada Exitosamente.");
                
                compraTemporal = null; modeloDetalles.setRowCount(0); lblTotal.setText("Total Compra: $0.0");
                txtFactura.setText(""); txtFactura.setEnabled(true); cbProveedores.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel crearLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.BLACK); // Alto contraste
        return lbl;
    }
}