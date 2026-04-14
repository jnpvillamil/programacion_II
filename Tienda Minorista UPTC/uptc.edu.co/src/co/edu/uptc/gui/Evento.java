package co.edu.uptc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener {
    private PanelProducto pProd;
    private PanelVenta pVen;
    private PanelCliente pCli;
    private PanelCompra pCom;
    private PanelProveedor pProv;

    public void setPaneles(PanelProducto p1, PanelVenta p2, PanelCliente p3, PanelCompra p4, PanelProveedor p5) {
        this.pProd = p1; 
        this.pVen = p2; 
        this.pCli = p3; 
        this.pCom = p4; 
        this.pProv = p5;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        
        if (pProd != null) {
            if (src == pProd.getBtnGuardar()) pProd.guardarProducto();
            if (src == pProd.getBtnEliminar()) pProd.eliminarProducto();
        }

        if (pVen != null) {
            if (src == pVen.getBtnAgregar()) pVen.agregar();
            if (src == pVen.getBtnFinalizar()) pVen.finalizar();
        }

        if (pCli != null) {
            if (src == pCli.getBtnGuardar()) pCli.guardarCliente();
            if (src == pCli.getBtnEliminar()) pCli.eliminarCliente();
        }

        if (pCom != null) {
            if (src == pCom.getBtnAgregar()) pCom.agregar();
            if (src == pCom.getBtnFinalizar()) pCom.finalizar();
        }

        if (pProv != null) {
            if (src == pProv.getBtnGuardar()) pProv.guardarProveedor();
        }
    }
}