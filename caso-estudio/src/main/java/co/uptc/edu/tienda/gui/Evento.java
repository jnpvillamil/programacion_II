package co.uptc.edu.tienda.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Evento implements ActionListener {

    public final static String CANCELAR = "Cancelar";
    public final static String LOGIN = "Iniciar Sesión";
    public final static String VER = "Ver";
    public final static String ACTUALIZAR = "Actualizar";
    public final static String ELIMINAR = "Inactivar";
    public final static String CREAR = "Registrar";
    public final static String BUSCAR = "Buscar";
    public final static String LIMPIAR = "Limpiar";
    public final static String ACTIVAR = "Activar";

    // PROVEEDOR
    public final static String ELIMINAR_PR = "Eliminar_PR";
    public final static String VER_PR = "Ver_PR";
    public final static String ACTUALIZAR_PR = "Actualizar_PR";
    public final static String CREAR_PR = "Nuevo_PR";
    public final static String BUSCAR_PR = "Buscar_PR";
    public final static String LIMPIAR_PR = "Limpiar_PR";
    public final static String CANCELAR_PR = "Cancelar_PR";
    public final static String GUARDAR_PR = "Guardar_PR";
    public final static String EDITAR_PR = "Editar_PR";

    // PRODUCTO
    public final static String CREAR_PRD = "Nuevo_PRD";
    public final static String ACTUALIZAR_PRD = "Actualizar_PRD";
    public final static String GUARDAR_PRD = "Guardar_PRD";
    public final static String EDITAR_PRD = "Editar_PRD";
    public final static String CANCELAR_PRD = "Cancelar_PRD";

    public final static String BUSCAR_PRD = "Buscar_PRD";
    public final static String LIMPIAR_PRD = "Limpiar_PRD";
    public final static String VER_PRD = "Ver_PRD";

    // NUEVOS
    public final static String INACTIVAR_PRD = "Inactivar_PRD";
    public final static String ACTIVAR_PRD = "Activar_PRD";

    public final static String GUARDAR = "Guardar";
    public final static String EDITAR = "Editar";

    // CLIENTE
    public final static String CREAR_CLI = "Nuevo_CLI";
    public final static String ACTUALIZAR_CLI = "Actualizar_CLI";
    public final static String ELIMINAR_CLI = "Eliminar_CLI";
    public final static String GUARDAR_CLI = "Guardar_CLI";
    public final static String EDITAR_CLI = "Editar_CLI";
    public final static String CANCELAR_CLI = "Cancelar_CLI";

    private VentanaPrincipal ventana;

    Evento(VentanaPrincipal v) {
        ventana = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String evento = e.getActionCommand();

        if(evento.equals(CANCELAR)) {

            JOptionPane.showMessageDialog(null,"Hasta Luego");
            System.exit(0);

        } else if(evento.equals(LOGIN)) {

            ventana.loguear();

        // PROVEEDOR
        } else if(evento.equals(CREAR_PR)) {

            ventana.lanzarDialogoProveedor();

        } else if(evento.equals(CANCELAR_PR)) {

            ventana.cerrarDialogoProveedor();

        } else if(evento.equals(GUARDAR_PR)) {

            ventana.crearProveedor();

        } else if(evento.equals(ACTUALIZAR_PR)) {

            ventana.lanzarDialogoModificarProveedor();

        } else if(evento.equals(EDITAR_PR)) {

            ventana.modificarProveedor();

        } else if(evento.equals(ELIMINAR_PR)) {

            ventana.eliminarProveedor();
        } else if(evento.equals(VER_PR)) {

            ventana.verProveedor();
        } else if(evento.equals(BUSCAR_PR)) {

            ventana.buscarProveedor();

        // PRODUCTO
        } else if(evento.equals(CREAR_PRD)) {

            ventana.lanzarDialogoProducto();

        } else if(evento.equals(CANCELAR_PRD)) {

            ventana.cerrarDialogoProducto();

        } else if(evento.equals(GUARDAR_PRD)) {

            ventana.crearProducto();

        } else if(evento.equals(ACTUALIZAR_PRD)) {

            ventana.lanzarDialogoModificarProducto();

        } else if(evento.equals(EDITAR_PRD)) {

            ventana.modificarProducto();

        } else if(evento.equals(INACTIVAR_PRD)) {

            ventana.inactivarProducto();

        } else if(evento.equals(ACTIVAR_PRD)) {

            ventana.activarProducto();

        } else if(evento.equals(BUSCAR_PRD)) {

            ventana.buscarProducto();

        } else if(evento.equals(LIMPIAR_PRD)) {

            ventana.limpiarProducto();

        } else if(evento.equals(VER_PRD)) {

            ventana.verProducto();
        }
    }
}