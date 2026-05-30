package co.edu.uptc.gui.interfaces;
import java.util.List;
public interface RF27_ConsultarComprasPorProveedor extends IReporteBase {
    public List<Object> buscarComprasProveedor(String nit);
}