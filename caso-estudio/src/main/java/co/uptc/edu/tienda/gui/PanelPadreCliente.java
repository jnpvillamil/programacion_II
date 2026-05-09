package co.uptc.edu.tienda.gui;

import java.util.List;

import co.uptc.edu.tienda.modelo.Cliente;

public class PanelPadreCliente extends PanelCentral<Cliente> {

    public PanelPadreCliente(Evento evento) {
        super(evento);
    }

    @Override
    public void agregarTituloPanel() {

        tituloPanel = "Clientes";
    }

    @Override
    public void agregarIdentificadorComandoBoton() {

        // CLIENTE
        btnInactivar.setActionCommand(Evento.ELIMINAR_CLI);
        btnActualizar.setActionCommand(Evento.ACTUALIZAR_CLI);
        btnRegistrar.setActionCommand(Evento.CREAR_CLI);

        // GENERALES
        btnVer.setActionCommand(Evento.VER);
        btnLimpiar.setActionCommand(Evento.LIMPIAR);
        btnBuscar.setActionCommand(Evento.BUSCAR);
    }

    @Override
    public void agregarCabeceraTabla() {

        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Documento");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Tipo Cliente");
        modelo.addColumn("Estado");

        tblGenerica.setModel(modelo);
    }

    @Override
    public void poblarTabla(List<Cliente> listaClientes) {

        modelo.setRowCount(0);

        for (Cliente c : listaClientes) {

            Object[] fila = {

                c.getIdCliente(),
                c.getNombreCompleto(),
                c.getNumeroDocumento(),
                c.getDireccionC(),
                c.getTelefonoC(),
                c.getTipoCliente(),
                c.getEstado()
            };

            modelo.addRow(fila);
        }
    }

    public int getItemSeleccionado() {

        try {

            int fila = tblGenerica.getSelectedRow();

            if (fila != -1) {

                return Integer.parseInt(
                    tblGenerica.getValueAt(fila, 0).toString()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return -1;
    }
}