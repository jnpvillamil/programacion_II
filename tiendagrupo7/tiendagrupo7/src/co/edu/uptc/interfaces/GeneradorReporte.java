package co.edu.uptc.interfaces;

import java.util.List;

public interface GeneradorReporte {
    byte[] exportarPdf(List<Object> datos);
    void imprimirReporte();
}