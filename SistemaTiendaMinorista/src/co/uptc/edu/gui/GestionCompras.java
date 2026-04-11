package co.uptc.edu.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import co.uptc.edu.modelo.Compra;
import co.uptc.edu.modelo.DetalleCompra;

public class GestionCompras extends JFrame {

    private JTextField txtFactura, txtFecha;
    private JComboBox<String> cbProveedor;

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtIVA, txtSubtotal, txtTotal;

    private co.uptc.edu.negocio.GestionCompras gestion;

    public GestionCompras() {

        gestion = new co.uptc.edu.negocio.GestionCompras();

        setTitle("Gestión de Compras");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20,20));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelDetalle(), BorderLayout.CENTER);
        add(crearPanelResumen(), BorderLayout.SOUTH);

        cargarProveedores();
    }

    // ================= PROVEEDORES =================
    private void cargarProveedores() {

        cbProveedor.addItem("Seleccione proveedor");

        //  AQUÍ debes conectar con tu backend real
        cbProveedor.addItem("001 - Proveedor A");
        cbProveedor.addItem("002 - Proveedor B");
    }

    // ================= PANEL SUPERIOR =================
    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Información General"));

        txtFactura = new JTextField(15);
        txtFecha = new JTextField("DD/MM/AAAA", 15);
        cbProveedor = new JComboBox<>();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Factura:"), gbc);

        gbc.gridx = 1;
        panel.add(txtFactura, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 3;
        panel.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Proveedor:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(cbProveedor, gbc);

        return panel;
    }

    // ================= DETALLE =================
    private JPanel crearPanelDetalle() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Detalle de Productos"));

        String[] columnas = {"Código","Producto","Cantidad","Costo","Subtotal"};

        modelo = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return column != 4;
            }
        };

        tabla = new JTable(modelo);

        //  CALCULO AUTOMÁTICO
        modelo.addTableModelListener(e -> {

            for(int i = 0; i < modelo.getRowCount(); i++){
                try{
                    int cant = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                    double costo = Double.parseDouble(modelo.getValueAt(i, 3).toString());

                    modelo.setValueAt(cant * costo, i, 4);

                }catch(Exception ex){}
            }

            calcularTotales();
        });

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        botones.add(btnAgregar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        //  AGREGAR FILA
        btnAgregar.addActionListener(e -> {
            modelo.addRow(new Object[]{"", "", 0, 0.0, 0.0});
        });

        //  ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if(fila >= 0){
                modelo.removeRow(fila);
                calcularTotales();
            }
        });

        //  LIMPIAR
        btnLimpiar.addActionListener(e -> {
            modelo.setRowCount(0);
            calcularTotales();
        });

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // ================= RESUMEN =================
    private JPanel crearPanelResumen() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Resumen"));

        JPanel totales = new JPanel(new GridLayout(3,2,10,10));

        txtSubtotal = new JTextField();
        txtIVA = new JTextField();
        txtTotal = new JTextField();

        txtSubtotal.setEditable(false);
        txtIVA.setEditable(false);
        txtTotal.setEditable(false);

        totales.add(new JLabel("Subtotal:"));
        totales.add(txtSubtotal);

        totales.add(new JLabel("IVA 19%:"));
        totales.add(txtIVA);

        totales.add(new JLabel("TOTAL:"));
        totales.add(txtTotal);

        panel.add(totales, BorderLayout.CENTER);

        JButton btnRegistrar = new JButton("Registrar Compra");

        btnRegistrar.addActionListener(e -> {

            if(txtFactura.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Ingrese factura");
                return;
            }

            if(modelo.getRowCount() == 0){
                JOptionPane.showMessageDialog(this, "Agregue productos");
                return;
            }

            Compra compra = new Compra(
                    txtFactura.getText(),
                    txtFecha.getText(),
                    cbProveedor.getSelectedItem().toString()
            );

            for(int i = 0; i < modelo.getRowCount(); i++){

                DetalleCompra d = new DetalleCompra(
                        modelo.getValueAt(i, 0).toString(),
                        modelo.getValueAt(i, 1).toString(),
                        Integer.parseInt(modelo.getValueAt(i, 2).toString()),
                        Double.parseDouble(modelo.getValueAt(i, 3).toString())
                );

                compra.agregarDetalle(d);
            }

            if(gestion.registrarCompra(compra)){
                JOptionPane.showMessageDialog(this, "Compra registrada ✔");
                limpiarTodo();
            } else {
                JOptionPane.showMessageDialog(this, "Factura duplicada");
            }
        });

        panel.add(btnRegistrar, BorderLayout.SOUTH);

        return panel;
    }

    // ================= CALCULOS =================
    private void calcularTotales(){

        double subtotal = 0;

        for(int i = 0; i < modelo.getRowCount(); i++){
            subtotal += Double.parseDouble(modelo.getValueAt(i, 4).toString());
        }

        double iva = subtotal * 0.19;
        double total = subtotal + iva;

        txtSubtotal.setText(String.valueOf(subtotal));
        txtIVA.setText(String.valueOf(iva));
        txtTotal.setText(String.valueOf(total));
    }

    private void limpiarTodo(){
        modelo.setRowCount(0);
        txtFactura.setText("");
        txtFecha.setText("");
        txtSubtotal.setText("");
        txtIVA.setText("");
        txtTotal.setText("");
    }

    public static void main(String[] args) {
        new GestionCompras().setVisible(true);
    }
}