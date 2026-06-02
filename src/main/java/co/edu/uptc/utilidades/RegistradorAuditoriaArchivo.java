package co.edu.uptc.utilidades;

import co.edu.uptc.interfaces.RegistradorAuditoria;

public class RegistradorAuditoriaArchivo implements RegistradorAuditoria {

    @Override
    public void registrar(String linea, String rutaDestino) {
        ExportadorDatos.registrarLog(linea, rutaDestino);
    }
}
