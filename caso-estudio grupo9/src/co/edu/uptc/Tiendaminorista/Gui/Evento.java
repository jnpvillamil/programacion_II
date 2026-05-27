package co.edu.uptc.Tiendaminorista.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import  co.edu.uptc.Tiendaminorista.Administrador.gui.*;

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

    private PanelPrincipal ventana;
    private PanelProductos panelProductos;

    public Evento(PanelPrincipal V) {
        ventana = V;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String evento = e.getActionCommand();

        if (evento.equals(SALIR)) {

            JOptionPane.showMessageDialog(null, "pelo");

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

        } else if (evento.equals(CANCELARPRO)) {

            ventana.regresarAlInicial();

        }

    }
}