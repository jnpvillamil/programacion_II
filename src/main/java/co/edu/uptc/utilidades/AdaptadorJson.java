package co.edu.uptc.utilidades;

import java.util.List;

public class AdaptadorJson {
    
    public static <T> String convertirAJson(List<T> lista) {
        return "[]"; 
    }

    public static <T> List<T> convertirDesdeJson(String json, Class<T[]> claseArreglo) {
        return null; 
    }
}