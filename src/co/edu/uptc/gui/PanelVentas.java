package co.edu.uptc.gui;

import co.edu.uptc.negocio.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class PanelVentas extends JPanel {
    private SistemaGestion sis;
    private Venta ventaTemporal;
    private DefaultTableModel modeloDetalles;
    private JLabel lblTotal;

    public PanelVentas(SistemaGestion sis) {
        this.sis = sis;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(244, 246, 249));

        JLabel lblTitulo = new JLabel("Punto de Venta (POS)");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        // Formulario Superior
        JPanel panelNorte = new JPanel(new GridLayout(3, 4, 10, 10));
        panelNorte.setOpaque(false);
        
        JTextField txtFactura = new JTextField();
        JComboBox<String> cbClientes = new JComboBox<>(new String[]{"Cliente de Mostrador"}); // Reemplazar con sis.getClientes()
        JComboBox<String> cbProductos = new JComboBox<>(new String[]{"Seleccione Producto..."}); // Reemplazar con sis.getProductos()
        JTextField txtCant = new JTextField();
        JComboBox<String> cbFormaPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia"});
        
        JButton btnAgregar = new JButton("Añadir a Factura");
        btnAgregar.setBackground(new Color(52, 152, 219)); // Azul
        btnAgregar.setForeground(Color.WHITE);

        panelNorte.add(new JLabel("N° Factura:")); panelNorte.add(txtFactura);
        panelNorte.add(new JLabel("Cliente:")); panelNorte.add(cbClientes);
        panelNorte.add(new JLabel("Producto:")); panelNorte.add(cbProductos);
        panelNorte.add(new JLabel("Cantidad:")); panelNorte.add(txtCant);
        panelNorte.add(new JLabel("Forma Pago:")); panelNorte.add(cbFormaPago);
        panelNorte.add(new JLabel("")); panelNorte.add(btnAgregar);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setOpaque(false);
        contenedorCentral.add(panelNorte, BorderLayout.NORTH);

        // Tabla de detalles
        modeloDetalles = new DefaultTableModel(new String[]{"Producto", "Cant.", "Precio Unit.", "Subtotal"}, 0);
        JTable tablaDetalles = new JTable(modeloDetalles);
        tablaDetalles.setRowHeight(25);
        tablaDetalles.getTableHeader().setBackground(new Color(44, 62, 80));
        tablaDetalles.getTableHeader().setForeground(Color.WHITE);
        contenedorCentral.add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);

        add(contenedorCentral, BorderLayout.CENTER);

        // Totales y Registrar
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelSur.setOpaque(false);
        lblTotal = new JLabel("Total Venta: $0.0");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JButton btnRegistrar = new JButton("Completar Venta");
        btnRegistrar.setBackground(new Color(46, 204, 113));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        panelSur.add(lblTotal);
        panelSur.add(btnRegistrar);
        add(panelSur, BorderLayout.SOUTH);

        // Eventos
        btnAgregar.addActionListener(e -> {
            // Lógica similar a PanelCompras: validar, instanciar DetalleTransaccion, agregarlo a VentaTemporal
            
        });

        btnRegistrar.addActionListener(e -> {
            // Lógica: sis.getGestorVenta().registrarVenta(ventaTemporal);
            JOptionPane.showMessageDialog(this, "Venta registrada con éxito.");
            modeloDetalles.setRowCount(0);
            lblTotal.setText("Total Venta: $0.0");
        });
    }
}