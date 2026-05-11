package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelCompra;
import co.edu.uptc.modelo.*;
import co.edu.uptc.negocio.*;
import co.edu.uptc.utilidades.*;
import javax.swing.*;
import java.util.ArrayList;

public class ControladorCompra {
    private PanelCompra vista;
    private GestionCompras negocio;
    private GestionProveedor gProv;
    private GestionInventario gInv;
    private Proveedor provActual;

    public ControladorCompra(PanelCompra vista, GestionCompras negocio, GestionProveedor gProv, GestionInventario gInv) {
        this.vista = vista;
        this.negocio = negocio;
        this.gProv = gProv;
        this.gInv = gInv;
        this.vista.getBtnBuscarProveedor().addActionListener(e -> buscarProv());
        this.vista.getBtnFinalizarCompra().addActionListener(e -> finalizar());
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