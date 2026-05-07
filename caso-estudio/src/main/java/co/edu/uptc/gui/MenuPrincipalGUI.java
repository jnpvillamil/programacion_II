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
    private JButton btnRegistrarProducto;  // Nuevo botón
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
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));  // Cambié a 7 filas para el nuevo botón

        btnClientes = new JButton("Clientes");
        btnCerrarSesion = new JButton("Cerrar sesión");
        btnRegistrarProducto = new JButton("Registrar Producto");  // Nuevo botón

        // Establecer los ActionCommands para cada botón
        btnClientes.setActionCommand("ABRIR_CLIENTES");
        btnCerrarSesion.setActionCommand("CERRAR_SESION");
        btnRegistrarProducto.setActionCommand("REGISTRAR_PRODUCTO");  // Acción para el botón de registrar producto

        // Agregar los botones al panel
        panel.add(btnClientes);
        panel.add(btnRegistrarProducto);  // Agregamos el nuevo botón al panel
        panel.add(btnCerrarSesion);

        add(panel);
    }

    private void configurarPermisos() {
        if (usuarioActual.getRol() == RolUsuarioEnum.VENDEDOR) {
            btnCerrarSesion.setEnabled(false);
        }
    }

    // Métodos para obtener los botones (en caso de que necesites usarlos desde el controlador)
    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }

    public JButton getBtnRegistrarProducto() {
        return btnRegistrarProducto;  // Método para obtener el nuevo botón
    }
}