package co.edu.uptc.principal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import co.edu.uptc.ventanas.*; // Importa todas tus ventanas

@SuppressWarnings("serial")
public class Principal extends JFrame {

    private JDesktopPane escritorio;
    private JMenuItem itemInsCli, itemConCli, itemInsProv, itemConProv, itemInsProd, itemConProd;

    public Principal() {

        setTitle("Sistema Comercial Optimizado - UPTC");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        escritorio = new JDesktopPane();
        getContentPane().add(escritorio, BorderLayout.CENTER);

        JMenuBar barra = new JMenuBar();
        Font fuenteMenu = new Font("Arial", Font.BOLD, 13);

        JMenu mClientes = new JMenu("Clientes"); mClientes.setFont(fuenteMenu);
        JMenu mProveedores = new JMenu("Proveedores"); mProveedores.setFont(fuenteMenu);
        JMenu mProductos = new JMenu("Productos"); mProductos.setFont(fuenteMenu);

        mClientes.add(itemInsCli = new JMenuItem("Registrar Cliente"));
        mClientes.add(itemConCli = new JMenuItem("Consultar Listado"));
        
        mProveedores.add(itemInsProv = new JMenuItem("Registrar Proveedor"));
        mProveedores.add(itemConProv = new JMenuItem("Consultar Proveedores"));
        
        mProductos.add(itemInsProd = new JMenuItem("Registrar Producto"));
        mProductos.add(itemConProd = new JMenuItem("Consultar Inventario"));

        barra.add(mClientes); barra.add(mProveedores); barra.add(mProductos);
        setJMenuBar(barra);


        itemInsCli.addActionListener(e -> {
            VentanaInsertarCliente v = new VentanaInsertarCliente();
            mostrarVentanaAjustada(v, 500, 400);
        });

        itemConCli.addActionListener(e -> {
            VentanaConsultarCliente v = new VentanaConsultarCliente();
            mostrarVentanaAjustada(v, 790, 450);
        });

        itemInsProv.addActionListener(e -> {
            VentanaInsertarProveedor v = new VentanaInsertarProveedor();
            mostrarVentanaAjustada(v, 500, 400); 
        });

        itemConProv.addActionListener(e -> {
            VentanaConsultarProveedor v = new VentanaConsultarProveedor();
            mostrarVentanaAjustada(v, 790, 450); 
        });

        itemInsProd.addActionListener(e -> {
            VentanaActualizarProducto v = new VentanaActualizarProducto();
            mostrarVentanaAjustada(v, 500, 400); 
        });

        itemConProd.addActionListener(e -> {
            VentanaConsultarProducto v = new VentanaConsultarProducto();
            mostrarVentanaAjustada(v, 790, 450); 
        });
    }

    private void mostrarVentanaAjustada(JInternalFrame ventana, int ancho, int alto) {
        try {
 
            ventana.setSize(ancho, alto);
            Dimension desktopSize = escritorio.getSize();
            int x = (desktopSize.width - ancho) / 2;
            int y = (desktopSize.height - alto) / 2;
            ventana.setLocation(x, y);
            escritorio.add(ventana);
            ventana.setVisible(true);
            ventana.toFront();
            ventana.setSelected(true);
            escritorio.revalidate();
            escritorio.repaint();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al abrir ventana: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Principal().setVisible(true));
    }
}