package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelCompra;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.*;
import co.edu.uptc.negocio.*;
import co.edu.uptc.persistencia.PersistenciaProducto;
import co.edu.uptc.utilidades.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Controlador para la gestión de compras.
 * 
 * APLICACIÓN DE PRINCIPIOS SOLID:
 * - S (Single Responsibility): Solo coordina interacción entre GUI y negocio de compras
 * - D (Dependency Inversion): Recibe dependencias inyectadas (Gestion*), no las instancia
 * - O (Open/Closed): Abierto a nuevas implementaciones sin modificar este código
 */
public class ControladorCompra {
    private PanelCompra vista;
    private GestionCompras negocio;
    private GestionProveedor gProv;
    private GestionInventario gInv;
    private Proveedor provActual;

    /**
     * Constructor con inyección completa de dependencias.
     * 
     * @param vista Panel de GUI
     * @param negocio Gestión de compras
     * @param gProv Gestión de proveedores
     * @param gInv Gestión de inventario
     */
    public ControladorCompra(PanelCompra vista, GestionCompras negocio, GestionProveedor gProv, GestionInventario gInv) {
        this.vista = vista;
        this.negocio = negocio;
        this.gProv = gProv;
        this.gInv = gInv;
        this.vista.getBtnBuscarProveedor().addActionListener(e -> buscarProv());
        this.vista.getBtnFinalizarCompra().addActionListener(e -> finalizar());
    }

    /**
     * Constructor convenencia: crea instancias por defecto con persistencia MySQL.
     */
    public ControladorCompra(PanelCompra vista) {
        this(vista, 
             new GestionCompras(new GestionInventario()),
             new GestionProveedor(),
             new GestionInventario());
    }

    private void buscarProv() {
        provActual = gProv.buscar(vista.getTxtIdentificacionProveedor().getText());
        if (provActual != null) vista.getLblNombreProveedor().setText(provActual.getNombre());
    }

    private void finalizar() {
        if (provActual == null) return;
        Compra c = new Compra(vista.getTxtFacturaProveedor().getText(), ManejadorFechas.obtenerFechaActual(), 
                             provActual, new ArrayList<>(), 0, 0);
        if (negocio.procesarCompra(c)) JOptionPane.showMessageDialog(vista, "Compra exitosa");
    }
}