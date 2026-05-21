package co.edu.uptc.utilidades;

import java.io.File;

public class GestorDirectorios {
    
    public static void asegurarDirectorios(String ruta) {
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }
}