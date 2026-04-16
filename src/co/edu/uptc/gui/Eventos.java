package co.edu.uptc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Eventos implements ActionListener {
    
    public static final String CMD_VER_PRODUCTOS = "VER_PRODUCTOS";
    public static final String CMD_VER_CLIENTES = "VER_CLIENTES";
    public static final String CMD_VER_PROVEEDORES = "VER_PROVEEDORES";
    public static final String CMD_VER_VENTAS = "VER_VENTAS";
    public static final String CMD_VER_COMPRAS = "VER_COMPRAS";
    public static final String CMD_VER_CONTABILIDAD = "VER_CONTABILIDAD";
    public static final String CMD_VER_CONSULTAS = "VER_CONSULTAS";
    public static final String CMD_VER_REPORTES = "VER_REPORTES";

    private VentanaPrincipal ventana;

    public Eventos(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        // Navegación del CardLayout
        switch (comando) {
            case CMD_VER_PRODUCTOS: ventana.mostrarPanel("PANEL_PRODUCTOS"); break;
            case CMD_VER_CLIENTES: ventana.mostrarPanel("PANEL_CLIENTES"); break;
            case CMD_VER_PROVEEDORES: ventana.mostrarPanel("PANEL_PROVEEDORES"); break;
            case CMD_VER_VENTAS: ventana.mostrarPanel("PANEL_VENTAS"); break;
            case CMD_VER_COMPRAS: ventana.mostrarPanel("PANEL_COMPRAS"); break;
            case CMD_VER_CONTABILIDAD: ventana.mostrarPanel("PANEL_CONTABILIDAD"); break;
            case CMD_VER_CONSULTAS: ventana.mostrarPanel("PANEL_CONSULTAS"); break;
            case CMD_VER_REPORTES: ventana.mostrarPanel("PANEL_REPORTES"); break;
        }
    }
}