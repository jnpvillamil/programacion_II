package co.edu.uptc.gui.interfaces;

public interface RF24_DescontarStockAutomatico extends IVentaBase {
    public void restarInventario(String codigo, int cantidad);
    
}