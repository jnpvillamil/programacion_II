package co.edu.uptc.tiendaminorista.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import co.edu.uptc.tiendaminorista.gui.administrador.PanelProductos;

public class Evento implements ActionListener {

    public final static String SALIR = "Salir";
    public final static String ENTRAR = "Entrar";
    public final static String REGISTRARCLIENTE = "Registrar cliente";
    public final static String MODIFICARCLIENTE = "Modificar cliente";
    public final static String HISTORIALCLIENTE = "Historial de compra cliente";
    public final static String BUSQUEDACLI = "busquedacliente";
    public final static String CANCELAR = "Cancelar";
    public final static String REGISTRAR = "Registrar";
    public final static String ACTUALIZARCLI = "Actualizar cliente";
    public final static String DESACTIVARCLI = "Desactivar cliente";
    public final static String ACTIVARCLI = "Activar cliente";
    public final static String REGISTRARPROVEDORES = "Registrar Proveedores";
    public final static String ACTUALIZARPRO = "Actualizar Proveedores";
    public final static String COMPRASPRO = "Compras realizadas a los proveedores";
    public final static String CANCELARPRO = "Cancelar";
    public final static String REGISTRARPROV = "Registrar Proveedor";
    public final static String ACTUALIZARPRO1 = "Actualizar Proveedor";
    public final static String DESACTIVARPRO = "Desactivar proveedor";
    public final static String ACTIVARPRO = "Activar proveedor";
    public final static String REGISTRAREM = "Registar Empleado";
    public final static String ACTUALIZAREM = "Actualizar Empleado";
    public final static String ELIMINAREM = "Eliminar";
    public final static String BUSCAR_HISTORIAL_CLI = "Buscar Historial Cliente";
    
    public final static String COMPRASCLI = "Realizar compra"; 
    public final static String REALIZARCOM = "Ejecutar Compra Desde Panel"; 
    public final static String VOLVER = "Volver de Compra"; 

    private PanelPrincipal ventana;
    private PanelProductos panelProductos;

    public Evento(PanelPrincipal V) {
        ventana = V;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String evento = e.getActionCommand();

        if (evento.equals(SALIR)) {
            int confirm = JOptionPane.showConfirmDialog(null, 
                "¿Seguro que quiere salir del sistema?", 
                "Salida del sistema", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (evento.equals(ENTRAR)) {
            ventana.loguear();
        } else if (evento.equals(REGISTRARCLIENTE)) {
            ventana.mostrarRegistroCliente();
        } else if (evento.equals(CANCELAR)) {
            ventana.regresarAlInicial();
        } else if (evento.equals(REGISTRAR)) {
            ventana.registrarCliente();
        } else if (evento.equals(MODIFICARCLIENTE)) {
            ventana.mostrarActualizarCliente();
        } else if (evento.equals(ACTUALIZARCLI)) {
            ventana.actualizarCliente();
        } else if (evento.equals(DESACTIVARCLI)) {
            ventana.desactivarCliente();
        } else if (evento.equals(ACTIVARCLI)) {
            ventana.activarCliente();
        } else if (evento.equals(REGISTRARPROVEDORES)) {
            ventana.mostrarRegistrarProveedor();
        } else if (evento.equals(REGISTRARPROV)) {
            ventana.registrarProveedor();
        } else if (evento.equals(ACTUALIZARPRO)) {
            ventana.mostrarActualizarProveedor();
        } else if (evento.equals(ACTUALIZARPRO1)) {
            ventana.actualizarProveedor();
        } else if (evento.equals(DESACTIVARPRO)) {
            ventana.desactivarProveedor();
        } else if (evento.equals(ACTIVARPRO)) {
            ventana.activarProveedor();
        } else if (evento.equals(COMPRASPRO)) {
            ventana.mostrarComprasPro();    
        } else if (evento.equals(CANCELARPRO)) {
            ventana.regresarAlInicial();
        } else if (evento.equals(REGISTRAREM)) {
            ventana.registrarEmpleado();
        } else if (evento.equals(ACTUALIZAREM)) {
            ventana.actualizarEmpleado();
        } else if (evento.equals(ELIMINAREM)) { 
            ventana.eliminarEmpleado();
        } else if (evento.equals(COMPRASCLI)) {
            ventana.mostrarCompraCliente(); 
        } else if (evento.equals(REALIZARCOM)) {
            ventana.ejecutarCompraCliente(); 
        } else if (evento.equals(VOLVER)) {
            ventana.mostrarPanelCliente(); 
        } else if (evento.equals(HISTORIALCLIENTE)) {
            ventana.mostrarPantallaHistorial(); 
        } else if (evento.equals(BUSCAR_HISTORIAL_CLI)) {
            ventana.buscarHistorialCliente();
        } 
    }

    public PanelPrincipal getVentana() {
        return this.ventana;
    }
}