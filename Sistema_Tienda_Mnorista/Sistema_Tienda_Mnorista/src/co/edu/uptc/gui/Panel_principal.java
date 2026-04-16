package co.edu.uptc.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Panel_principal extends JFrame {

    private Panel_login panellogin;
    private Evento evento;
    private PanelInicial panelinicial;
    private panel_registrocli panelregistrocliente;
    private PanelCliente panelcli;
    private Panel_Actualizarcli actaulizacli;
    private Panel_Registrarpro Registrarpro;
    private Panel_Actualizarpro Actualizarpro;

    public Panel_principal() {

        setSize(800, 600);
        setTitle("Tienda minorista");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        evento = new Evento(this);

        panellogin = new Panel_login(evento);
        add(panellogin, BorderLayout.CENTER);

        panelinicial = new PanelInicial(evento);
        panelregistrocliente = new panel_registrocli(evento);
        panelcli = new PanelCliente(evento);
        actaulizacli = new Panel_Actualizarcli(evento);
        Registrarpro = new Panel_Registrarpro(evento);
        Actualizarpro = new Panel_Actualizarpro(evento);
    }

    public static void main(String[] args) {
        Panel_principal ventana = new Panel_principal();
        ventana.setVisible(true);
    }

    public void loguear() {
        getContentPane().removeAll();
        add(panelinicial, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void mostrarRegistroCliente() {
        getContentPane().removeAll();
        add(panelregistrocliente, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    public void mostrarPanelCliente() {
        getContentPane().removeAll();
        add(panelcli, BorderLayout.CENTER);

 
        panelcli.cargarClientes(
                co.edu.uptc.negocio.Cliente_negocio.getInstance().getListaClientes()
        );

        revalidate();
        repaint();
    }

    public void regresarcli() {
        getContentPane().removeAll();
        add(panelinicial, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void Actualizarcli() {
        getContentPane().removeAll();
        add(actaulizacli, BorderLayout.CENTER);

        
        actaulizacli.cargarClientes(
                co.edu.uptc.negocio.Cliente_negocio.getInstance().getListaClientes()
        );

        revalidate();
        repaint();
    }

    public void registrarpro() {
        getContentPane().removeAll();
        add(Registrarpro, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void Actualizarpro1() {
        getContentPane().removeAll();
        add(Actualizarpro, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public panel_registrocli getPanelRegistro() {
        return panelregistrocliente;
    }

    public PanelCliente getPanelCliente() {
        return panelcli;
    }

    public Panel_Actualizarcli getPanelActualizar() {
        return actaulizacli;
    }
}