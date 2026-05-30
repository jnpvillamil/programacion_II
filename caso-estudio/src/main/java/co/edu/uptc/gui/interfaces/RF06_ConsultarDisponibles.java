package co.edu.uptc.gui.interfaces;
import co.edu.uptc.gui.modelo.Producto;
import java.util.List;
public interface RF06_ConsultarDisponibles extends IProductoBase {
    public List<Producto> consultarDisponibles();
}