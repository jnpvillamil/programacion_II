package co.edu.uptc.ventanas;

import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class VentanaMenu extends JFrame implements ActionListener {
    private JButton btnInsCliente, btnActCliente, btnConCliente;
    private JButton btnInsProveedor, btnActProveedor, btnConProveedor;
    private JButton btnInsProducto, btnActProducto, btnConProducto;

    public VentanaMenu() {
        setTitle("Sistema de Gestión - Caso de Estudio");
        setSize(580, 360);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLabel lblClientes = new JLabel("GESTIÓN DE CLIENTES"); lblClientes.setBounds(40, 30, 160, 25); add(lblClientes);
        btnInsCliente = new JButton("Insertar Cliente"); btnInsCliente.setBounds(40, 70, 150, 30); btnInsCliente.addActionListener(this); add(btnInsCliente);
        btnActCliente = new JButton("Actualizar Cliente"); btnActCliente.setBounds(40, 120, 150, 30); btnActCliente.addActionListener(this); add(btnActCliente);
        btnConCliente = new JButton("Consultar Clientes"); btnConCliente.setBounds(40, 170, 150, 30); btnConCliente.addActionListener(this); add(btnConCliente);

        JLabel lblProveedores = new JLabel("GESTIÓN PROVEEDORES"); lblProveedores.setBounds(215, 30, 160, 25); add(lblProveedores);
        btnInsProveedor = new JButton("Insertar Proveedor"); btnInsProveedor.setBounds(215, 70, 150, 30); btnInsProveedor.addActionListener(this); add(btnInsProveedor);
        btnActProveedor = new JButton("Actualizar Proveedor"); btnActProveedor.setBounds(215, 120, 150, 30); btnActProveedor.addActionListener(this); add(btnActProveedor);
        btnConProveedor = new JButton("Consultar Proveedores"); btnConProveedor.setBounds(215, 170, 150, 30); btnConProveedor.addActionListener(this); add(btnConProveedor);

        JLabel lblProductos = new JLabel("GESTIÓN PRODUCTOS"); lblProductos.setBounds(390, 30, 160, 25); add(lblProductos);
        btnInsProducto = new JButton("Insertar Producto"); btnInsProducto.setBounds(390, 70, 150, 30); btnInsProducto.addActionListener(this); add(btnInsProducto);
        btnActProducto = new JButton("Actualizar Producto"); btnActProducto.setBounds(390, 120, 150, 30); btnActProducto.addActionListener(this); add(btnActProducto);
        btnConProducto = new JButton("Consultar Productos"); btnConProducto.setBounds(390, 170, 150, 30); btnConProducto.addActionListener(this); add(btnConProducto);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnInsCliente) { new VentanaInsertarCliente().setVisible(true); }
        else if (e.getSource() == btnActCliente) { new VentanaActualizarCliente().setVisible(true); }
        else if (e.getSource() == btnConCliente) { new VentanaConsultarCliente().setVisible(true); }
        else if (e.getSource() == btnInsProveedor) { new VentanaInsertarProveedor().setVisible(true); }
        else if (e.getSource() == btnActProveedor) { new VentanaActualizarProveedor().setVisible(true); }
        else if (e.getSource() == btnConProveedor) { new VentanaConsultarProveedor().setVisible(true); }
        else if (e.getSource() == btnInsProducto) { new VentanaInsertarProducto().setVisible(true); }
        else if (e.getSource() == btnActProducto) { new VentanaActualizarProducto().setVisible(true); }
        else if (e.getSource() == btnConProducto) { new VentanaConsultarProducto().setVisible(true); }
    }
}