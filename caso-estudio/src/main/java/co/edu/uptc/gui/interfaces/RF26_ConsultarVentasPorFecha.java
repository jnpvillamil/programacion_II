package co.edu.uptc.gui.interfaces;
import co.edu.uptc.gui.modelo.Venta;
import java.util.Date;
import java.util.List;
public interface RF26_ConsultarVentasPorFecha extends IReporteBase {
    public List<Venta> buscarVentasPorRango(Date inicio, Date fin);
}