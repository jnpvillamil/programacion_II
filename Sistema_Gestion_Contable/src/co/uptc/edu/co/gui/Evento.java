package co.uptc.edu.co.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener {

    private VentanaPrincipal ventana;

    public Evento(VentanaPrincipal ventana){
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String comando = e.getActionCommand();

        switch(comando){

            case "Clientes":
                ventana.mostrarPanel(new PanelCliente());
                break;

            case "Productos":
                ventana.mostrarPanel(new PanelProducto());
                break;

            case "Proveedores":
                ventana.mostrarPanel(new PanelProveedor());
                break;

            case "Ventas":
                ventana.mostrarPanel(new PanelVenta());
                break;

            case "Compras":
                ventana.mostrarPanel(new PanelCompra());
                break;

            case "Contabilidad":
                ventana.mostrarPanel(new PanelContabilidad());
                break;

            case "Reportes":
                ventana.mostrarPanel(new PanelReportes());
                break;
                
            case "Consultas":
            	ventana.mostrarPanel(new PanelConsultas());
        }
    }
}