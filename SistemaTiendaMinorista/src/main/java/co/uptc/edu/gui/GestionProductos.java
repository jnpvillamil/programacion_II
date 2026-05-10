package co.uptc.edu.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import co.uptc.edu.modelo.Producto;

public class GestionProductos extends JFrame {

    // CAMPOS
    private JTextField txtCodigo, txtNombre, txtPrecioCompra, txtPrecioVenta, txtStock, txtStockMin;
    private JComboBox<String> cbCategoria, cbEstado;

    private JTable tabla;
    private DefaultTableModel modelo;

    // BACKEND
    private co.uptc.edu.negocio.GestionProductos gestion;

    public GestionProductos() {

        gestion = new co.uptc.edu.negocio.GestionProductos();

        setTitle("Gestión de Productos");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15,15));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
        add(crearPanelMovimientos(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Datos del Producto"));

        JPanel formulario = new JPanel(new GridLayout(3,4,15,10));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtPrecioCompra = new JTextField();
        txtPrecioVenta = new JTextField();
        txtStock = new JTextField();
        txtStockMin = new JTextField();

        cbCategoria = new JComboBox<>(new String[]{"Víveres", "Aseo", "Papelería"});
        cbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});

        formulario.add(new JLabel("Código Interno:"));
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Nombre del Producto:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Categoría:"));
        formulario.add(cbCategoria);

        formulario.add(new JLabel("Precio de Compra:"));
        formulario.add(txtPrecioCompra);

        formulario.add(new JLabel("Precio de Venta:"));
        formulario.add(txtPrecioVenta);

        formulario.add(new JLabel("Stock Actual:"));
        formulario.add(txtStock);

        formulario.add(new JLabel("Stock Mínimo:"));
        formulario.add(txtStockMin);

        formulario.add(new JLabel("Estado:"));
        formulario.add(cbEstado);

        panel.add(formulario, BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnPrecio = new JButton("Actualizar Precios");

        botones.add(btnNuevo);
        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnPrecio);

        // REGISTRAR
        btnRegistrar.addActionListener(e -> {

            try{
                Producto p = new Producto(
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        cbCategoria.getSelectedItem().toString(),
                        Double.parseDouble(txtPrecioCompra.getText()),
                        Double.parseDouble(txtPrecioVenta.getText()),
                        Integer.parseInt(txtStock.getText()),
                        Integer.parseInt(txtStockMin.getText())
                );

                if(gestion.registrarProducto(p)){
                    modelo.addRow(new Object[]{
                            p.getCodigo(), p.getNombre(), p.getCategoria(),
                            p.getPrecioCompra(), p.getPrecioVenta(),
                            p.getStockActual(), p.getStockMinimo(), p.getEstado()
                    });

                    JOptionPane.showMessageDialog(this, "Producto registrado");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this, "El producto ya existe");
                }

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {

            try{

                Producto p = new Producto(
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        cbCategoria.getSelectedItem().toString(),
                        Double.parseDouble(txtPrecioCompra.getText()),
                        Double.parseDouble(txtPrecioVenta.getText()),
                        Integer.parseInt(txtStock.getText()),
                        Integer.parseInt(txtStockMin.getText())
                );

                //  IMPORTANTE → estado desde combo
                p.setEstado(cbEstado.getSelectedItem().toString());

                if(gestion.modificarProducto(p)){

                    int fila = tabla.getSelectedRow();

                    modelo.setValueAt(txtNombre.getText(), fila, 1);
                    modelo.setValueAt(cbCategoria.getSelectedItem(), fila, 2);
                    modelo.setValueAt(txtPrecioCompra.getText(), fila, 3);
                    modelo.setValueAt(txtPrecioVenta.getText(), fila, 4);
                    modelo.setValueAt(txtStock.getText(), fila, 5);
                    modelo.setValueAt(txtStockMin.getText(), fila, 6);
                    modelo.setValueAt(cbEstado.getSelectedItem(), fila, 7); // 🔥 AQUÍ

                    JOptionPane.showMessageDialog(this, "Producto actualizado");

                }

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Datos inválidos");
            }
        });
        //  ACTUALIZAR PRECIO
        btnPrecio.addActionListener(e -> {

            double nuevo = Double.parseDouble(txtPrecioVenta.getText());

            if(gestion.actualizarPrecio(txtCodigo.getText(), nuevo)){
                int fila = tabla.getSelectedRow();
                modelo.setValueAt(nuevo, fila, 4);

                JOptionPane.showMessageDialog(this, "Precio actualizado");
            }
        });

     
        btnNuevo.addActionListener(e -> limpiar());

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private JScrollPane crearTabla() {

        String[] columnas = {
                "Código", "Nombre", "Categoría",
                "Precio Compra", "Precio Venta",
                "Stock Actual", "Stock Mínimo", "Estado"
        };

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        //  SELECCIÓN
        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila >= 0){
                txtCodigo.setText(modelo.getValueAt(fila, 0).toString());
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                cbCategoria.setSelectedItem(modelo.getValueAt(fila, 2));
                txtPrecioCompra.setText(modelo.getValueAt(fila, 3).toString());
                txtPrecioVenta.setText(modelo.getValueAt(fila, 4).toString());
                txtStock.setText(modelo.getValueAt(fila, 5).toString());
                txtStockMin.setText(modelo.getValueAt(fila, 6).toString());
                cbEstado.setSelectedItem(modelo.getValueAt(fila, 7));
            }
        });

        return new JScrollPane(tabla);
    }

    private JPanel crearPanelMovimientos() {
        return new JPanel(); 
    }

    private void limpiar(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtStock.setText("");
        txtStockMin.setText("");
    }

    public static void main(String[] args) {
        new GestionProductos().setVisible(true);
    }
}