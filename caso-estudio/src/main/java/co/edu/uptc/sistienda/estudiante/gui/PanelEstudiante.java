package co.edu.uptc.sistienda.estudiante.gui;


import java.util.List;
 
import co.edu.uptc.sistienda.comun.gui.PanelCrudAbstracto;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Estudiante;
 

public class PanelEstudiante extends PanelCrudAbstracto {
 
    public PanelEstudiante(Evento evento) {
        super(evento);
    }
 
    @Override
    public String obtenerTituloPanel() {
        return "Gestión de Estudiantes";
    }
 
    @Override
    public void asignarComandosBotones() {
        botonNuevo.setActionCommand(Evento.NUEVO_ESTUDIANTE);
        botonEditar.setActionCommand(Evento.EDITAR_ESTUDIANTE);
        botonInactivar.setActionCommand(Evento.ELIMINAR_ESTUDIANTE);
        botonActivar.setActionCommand(Evento.ACTIVAR_ESTUDIANTE);
        botonBuscar.setActionCommand(Evento.BUSCAR_ESTUDIANTE);
        botonLimpiar.setActionCommand(Evento.LIMPIAR_ESTUDIANTE);
    }
 
    @Override
    public void agregarColumnasTabla() {
        modeloTabla.addColumn("Cédula");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Teléfono");
    }
 
    @Override
    public void poblarTabla(List<?> listaRegistros) {
        modeloTabla.setRowCount(0);
        for (Object obj : listaRegistros) {
            Estudiante e = (Estudiante) obj;
            modeloTabla.addRow(new Object[]{
                e.getCedula(),
                e.getNombre(),
                e.getTelefono()
            });
        }
    }
}
