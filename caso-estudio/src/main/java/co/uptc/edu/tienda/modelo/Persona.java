package co.uptc.edu.tienda.modelo;

import co.uptc.edu.tienda.enums.TipoDocEnum;

public class Persona {

    private String nombreCompleto;

    private TipoDocEnum tipoDocumento;

    private long numeroDocumento;

    public Persona() {

        super();
    }

    public Persona(
            String nombreCompleto,
            TipoDocEnum tipoDocumento,
            long numeroDocumento
    ) {

        this.nombreCompleto = nombreCompleto;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombreCompleto() {

        return nombreCompleto;
    }

    public void setNombreCompleto(
            String nombreCompleto
    ) {

        this.nombreCompleto = nombreCompleto;
    }

    public TipoDocEnum getTipoDocumento() {

        return tipoDocumento;
    }

    public void setTipoDocumento(
            TipoDocEnum tipoDocumento
    ) {

        this.tipoDocumento = tipoDocumento;
    }

    public long getNumeroDocumento() {

        return numeroDocumento;
    }

    public void setNumeroDocumento(
            long numeroDocumento
    ) {

        this.numeroDocumento = numeroDocumento;
    }
}