package co.edu.uptc.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import co.edu.uptc.enums.RolUsuarioEnum;
import co.edu.uptc.gui.modelo.Usuario;

@SuppressWarnings("serial")
public class MenuPrincipalGUI extends JFrame {

    private JButton btnClientes;
    private JButton btnProductos;
    private JButton btnProveedores;
    private JButton btnCompras;
    private JButton btnVentas;
    private JButton btnConsultas;
    private JButton btnContabilidad;
    private JButton btnReportes;
    private JButton btnEmpleados;
    private JButton btnNomina;
    private JButton btnCerrarSesion;

    private Usuario usuarioActual;

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
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        btnClientes = new JButton("Clientes");
        btnProductos = new JButton("Productos");
        btnProveedores = new JButton("Proveeedores");
        btnCompras = new JButton("Compras");
        btnVentas = new JButton("Ventas");
        btnConsultas = new JButton("Consultas");
        btnContabilidad = new JButton("Contabilidad");
        btnReportes = new JButton("Reportes");
        btnEmpleados = new JButton("Empleados");
        btnNomina = new JButton("Nómina");
        btnCerrarSesion = new JButton("Cerrar sesión");

        btnClientes.setActionCommand("ABRIR_CLIENTES");
        btnProductos.setActionCommand("ABRIR_PRODUCTOS");
        btnProveedores.setActionCommand("ABRIR_PROVEEDORES");
        btnCompras.setActionCommand("ABRIR_COMPRAS");
        btnVentas.setActionCommand("ABRIR_VENTAS");
        btnConsultas.setActionCommand("ABRIR_CONSULTAS");
        btnContabilidad.setActionCommand("ABRIR_CONTABILIDAD");
        btnReportes.setActionCommand("ABRIR_REPORTES");
        btnEmpleados.setActionCommand("ABRIR_EMPLEADOS");
        btnNomina.setActionCommand("ABRIR_NOMINA");
        btnCerrarSesion.setActionCommand("CERRAR_SESION");

        panel.add(btnClientes);
        panel.add(btnProductos);
        panel.add(btnProveedores);
        panel.add(btnCompras);
        panel.add(btnVentas);
        panel.add(btnConsultas);
        panel.add(btnContabilidad);
        panel.add(btnReportes);
        panel.add(btnEmpleados);
        panel.add(btnNomina);
        panel.add(btnCerrarSesion);

        add(panel);
    }

    private void configurarPermisos() {
        if (usuarioActual.getRol() == RolUsuarioEnum.VENDEDOR) {
            btnProveedores.setEnabled(false);
            btnCompras.setEnabled(false);
            btnContabilidad.setEnabled(false);
            btnReportes.setEnabled(false);
            btnEmpleados.setEnabled(false);
            btnNomina.setEnabled(false);
        }
    }

    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnProductos() {
        return btnProductos;
    }

    public JButton getBtnProveedores() {
        return btnProveedores;
    }

    public JButton getBtnCompras() {
        return btnCompras;
    }

    public JButton getBtnVentas() {
        return btnVentas;
    }

    public JButton getBtnConsultas() {
        return btnConsultas;
    }

    public JButton getBtnContabilidad() {
        return btnContabilidad;
    }

    public JButton getBtnReportes() {
        return btnReportes;
    }

    public JButton getBtnEmpleados() {
        return btnEmpleados;
    }

    public JButton getBtnNomina() {
        return btnNomina;
    }

    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }
}