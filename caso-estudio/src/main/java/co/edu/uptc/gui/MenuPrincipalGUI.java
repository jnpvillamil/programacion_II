package co.edu.uptc.gui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import co.edu.uptc.enums.RolUsuarioEnum;
import co.edu.uptc.gui.modelo.Usuario;

@SuppressWarnings("serial")
public class MenuPrincipalGUI extends JFrame {

    private JButton btnCerrarSesion;
    private JButton btnRegistrarProducto;  
    private Usuario usuarioActual;
    private JButton btnClientes;

    public MenuPrincipalGUI(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        setTitle("Menú Principal - " + usuarioActual.getRol());
        setSize(550, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarComponentes();
        configurarPermisos();
    }

    private void iniciarComponentes() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));  

        btnClientes = new JButton("Clientes");
        btnCerrarSesion = new JButton("Cerrar sesión");
        btnRegistrarProducto = new JButton("Registrar Producto");  
    
        btnClientes.setActionCommand("ABRIR_CLIENTES");
        btnCerrarSesion.setActionCommand("CERRAR_SESION");
        btnRegistrarProducto.setActionCommand("REGISTRAR_PRODUCTO"); 

  
        panel.add(btnClientes);
        panel.add(btnRegistrarProducto); 
        panel.add(btnCerrarSesion);

        add(panel);
    }

    private void configurarPermisos() {
        if (usuarioActual.getRol() == RolUsuarioEnum.VENDEDOR) {
            btnCerrarSesion.setEnabled(false);
        }
    }

    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }

    public JButton getBtnRegistrarProducto() {
        return btnRegistrarProducto;  
 }
}