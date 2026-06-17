package co.edu.uptc.sistienda.estudiante.gui;
 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.sistienda.comun.gui.DialogoCrudAbstracto;
import co.edu.uptc.sistienda.gui.Evento;
import co.edu.uptc.sistienda.modelo.Estudiante;
 

public class DialogoEstudiante extends DialogoCrudAbstracto {
 
    private JTextField campoCedula;
    private JTextField campoNombre;
    private JTextField campoTelefono;
 
    public DialogoEstudiante(Evento evento, boolean esCreacion) {
        super(evento, esCreacion ? "Nuevo Estudiante" : "Editar Estudiante", esCreacion);
        setSize(360, 220);
    }
 
    // Construir los campos del formulario
    @Override
    public void construirCamposFormulario(JPanel panelCampos) {
        campoCedula    = new JTextField();
        campoNombre    = new JTextField();
        campoTelefono  = new JTextField();
 
        // La cédula no se puede editar cuando se está modificando (es la PK)
        campoCedula.setEditable(esCreacion);
 
        panelCampos.add(new JLabel("Cédula:"));
        panelCampos.add(campoCedula);
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(campoNombre);
        panelCampos.add(new JLabel("Teléfono:"));
        panelCampos.add(campoTelefono);
    }
 
    // Asignar los ActionCommand a los botones
    @Override
    public void asignarComandosBotones() {
        botonGuardar.setActionCommand(esCreacion
                ? Evento.GUARDAR_ESTUDIANTE
                : Evento.ACTUALIZAR_ESTUDIANTE);
        botonCancelar.setActionCommand(Evento.CANCELAR_ESTUDIANTE);
    }
 
    // Cargar datos en el formulario (modo edición)
    @Override
    public void cargarDatosEnFormulario(Object objeto) {
        Estudiante estudiante = (Estudiante) objeto;
        campoCedula.setText(estudiante.getCedula());
        campoNombre.setText(estudiante.getNombre());
        campoTelefono.setText(estudiante.getTelefono());
    }

    // Capturar y validar los datos del formulario
    public Estudiante capturarDatosFormulario() throws Exception {
        if (campoCedula.getText().trim().isEmpty())
            throw new Exception("La cédula no puede estar vacía.");
        if (campoNombre.getText().trim().isEmpty())
            throw new Exception("El nombre no puede estar vacío.");
        if (campoTelefono.getText().trim().isEmpty())
            throw new Exception("El teléfono no puede estar vacío.");
 
        Estudiante estudiante = new Estudiante();
        estudiante.setCedula(campoCedula.getText().trim());
        estudiante.setNombre(campoNombre.getText().trim());
        estudiante.setTelefono(campoTelefono.getText().trim());
        return estudiante;
    }
}