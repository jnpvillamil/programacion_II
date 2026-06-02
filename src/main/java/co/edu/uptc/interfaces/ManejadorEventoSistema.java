package co.edu.uptc.interfaces;

import co.edu.uptc.dto.LoginDTO;
import co.edu.uptc.dto.MovimientoResumenDTO;
import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ReporteUtilidadDTO;
import co.edu.uptc.dto.UsuarioDTO;

import java.awt.CardLayout;
import java.util.List;
import javax.swing.JPanel;

public interface ManejadorEventoSistema {

    UsuarioDTO validarIngreso(LoginDTO loginDto);

    UsuarioDTO obtenerUsuarioSesion();

    void cerrarSesion();

    void configurarNavegacion(JPanel panelContenedor, CardLayout cardLayout);

    void mostrarPanel(String nombrePanel);

    List<MovimientoResumenDTO> listarResumenMovimiento();

    List<ReporteUtilidadDTO> listarReporteUtilidad();

    ReporteFinancieroDTO generarConsolidadoJson();

    ReporteFinancieroDTO leerReporteFinanciero();
}
