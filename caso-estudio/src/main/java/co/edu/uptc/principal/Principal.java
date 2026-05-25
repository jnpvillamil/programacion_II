package co.edu.uptc.principal;

import java.awt.Color;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import co.edu.uptc.ventanas.VentanaInsertarCliente;
import co.edu.uptc.ventanas.VentanaConsultarCliente;
import co.edu.uptc.ventanas.VentanaInsertarProveedor;
import co.edu.uptc.ventanas.VentanaConsultarProveedor;
import co.edu.uptc.ventanas.VentanaInsertarProducto;
import co.edu.uptc.ventanas.VentanaConsultarProducto;

@SuppressWarnings("serial")
public class Principal extends JFrame {

    private JMenuBar barraMenu;
    private JMenu menuClientes, menuProveedores, menuProductos;
    private JMenuItem itemRegCliente, itemConCliente, itemRegProv, itemConProv, itemRegProd, itemConProd;
    private JDesktopPane panelEscritorio; 

    public Principal() {
        setTitle("Sistema Comercial Optimizado - UPTC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650); 
        setLocationRelativeTo(null); 

        panelEscritorio = new JDesktopPane();
        panelEscritorio.setBackground(Color.WHITE); 
        setContentPane(panelEscritorio);

        barraMenu = new JMenuBar();
        barraMenu.setBackground(new Color(24, 44, 97)); 
        barraMenu.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        barraMenu.add(Box.createHorizontalStrut(280)); 

        menuClientes = new JMenu("CLIENTES");
        menuClientes.setForeground(Color.WHITE);
        menuClientes.setFont(new Font("Arial", Font.BOLD, 14));
        itemRegCliente = new JMenuItem("Registrar Cliente");
        itemConCliente = new JMenuItem("Consultar Clientes");
        menuClientes.add(itemRegCliente);
        menuClientes.add(itemConCliente);

        menuProveedores = new JMenu("PROVEEDORES");
        menuProveedores.setForeground(Color.WHITE);
        menuProveedores.setFont(new Font("Arial", Font.BOLD, 14));
        itemRegProv = new JMenuItem("Registrar Proveedor");
        itemConProv = new JMenuItem("Consultar Proveedores");
        menuProveedores.add(itemRegProv);
        menuProveedores.add(itemConProv);

        menuProductos = new JMenu("PRODUCTOS");
        menuProductos.setForeground(Color.WHITE);
        menuProductos.setFont(new Font("Arial", Font.BOLD, 14));
        itemRegProd = new JMenuItem("Registrar Producto");
        itemConProd = new JMenuItem("Consultar Inventario");
        menuProductos.add(itemRegProd);
        menuProductos.add(itemConProd);

        barraMenu.add(menuClientes);
        barraMenu.add(Box.createHorizontalStrut(30)); 
        barraMenu.add(menuProveedores);
        barraMenu.add(Box.createHorizontalStrut(30)); 
        barraMenu.add(menuProductos);
        
        setJMenuBar(barraMenu);

        itemRegCliente.addActionListener(e -> {
            VentanaInsertarCliente v = new VentanaInsertarCliente();
            panelEscritorio.add(v);
            v.setVisible(true);
        });
        itemConCliente.addActionListener(e -> {
            VentanaConsultarCliente v = new VentanaConsultarCliente();
            panelEscritorio.add(v);
            v.setVisible(true);
        });

        itemRegProv.addActionListener(e -> {
            VentanaInsertarProveedor v = new VentanaInsertarProveedor();
            panelEscritorio.add(v);
            v.setVisible(true);
        });
        itemConProv.addActionListener(e -> {
 
            VentanaConsultarProveedor v = new VentanaConsultarProveedor();
            panelEscritorio.add(v);
            v.setVisible(true);
        });

 
        itemRegProd.addActionListener(e -> {
            VentanaInsertarProducto v = new VentanaInsertarProducto();
            panelEscritorio.add(v);
            v.setVisible(true);
        });
        itemConProd.addActionListener(e -> {
            VentanaConsultarProducto v = new VentanaConsultarProducto();
            panelEscritorio.add(v);
            v.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Principal().setVisible(true);
        });
    }
}