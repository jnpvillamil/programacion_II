package co.uptc.edu.co.interfaces;

import java.util.List;

public interface IGestionReporte {

    void guardar(String nombreReporte, Object datos) throws Exception;

    List<String> listar() throws Exception;
}