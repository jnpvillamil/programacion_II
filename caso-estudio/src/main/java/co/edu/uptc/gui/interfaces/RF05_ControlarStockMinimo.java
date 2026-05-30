package co.edu.uptc.gui.interfaces;
import co.edu.uptc.gui.modelo.Producto;
import java.util.List;
public interface RF05_ControlarStockMinimo extends IProductoBase {
    public List<Producto> consultarAlertasStockBajo();
}