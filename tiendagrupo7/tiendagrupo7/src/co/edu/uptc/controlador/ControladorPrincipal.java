package co.edu.uptc.controlador;

import co.edu.uptc.gui.VentanaPrincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorPrincipal {

    private VentanaPrincipal vistaPrincipal;

    public ControladorPrincipal(VentanaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        inicializarNavegacion();
    }

    private void inicializarNavegacion() {
        vistaPrincipal.getBtnInventario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.mostrarPanel("INVENTARIO");
            }
        });

        vistaPrincipal.getBtnClientes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.mostrarPanel("CLIENTES");
            }
        });

        vistaPrincipal.getBtnVentas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.mostrarPanel("VENTAS");
            }
        });

        vistaPrincipal.getBtnCompras().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.mostrarPanel("COMPRAS");
            }
        });

        vistaPrincipal.getBtnProveedores().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaPrincipal.mostrarPanel("PROVEEDORES");
            }
        });
    }
}