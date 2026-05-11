package co.uptc.edu.tienda.modelo;

import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.enums.TipoClienteEnum;
import co.uptc.edu.tienda.enums.TipoDocEnum;

public class Cliente extends Persona {

    public static int contadorCliente = 300;

    private int idCliente;
    private String direccionC;
    private TipoClienteEnum tipoCliente;
    private long telefonoC;
    private EstadoEnum estado;

    public Cliente() {

        super();

        this.idCliente = contadorCliente++;
        this.estado = EstadoEnum.ACTIVO;
    }

    public Cliente(
            String nombre,
            TipoDocEnum doc,
            long numDoc,
            String direccion,
            TipoClienteEnum tipo,
            long tel
    ) {

        super(nombre, doc, numDoc);

        this.idCliente = contadorCliente++;
        this.direccionC = direccion;
        this.tipoCliente = tipo;
        this.telefonoC = tel;
        this.estado = EstadoEnum.ACTIVO;
    }

    public Cliente(int idCliente) {

        this.idCliente = idCliente;
    }

    public static void setContador(int ultimoId) {

        contadorCliente = ultimoId + 1;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDireccionC() {
        return direccionC;
    }

    public void setDireccionC(String direccionC) {
        this.direccionC = direccionC;
    }

    public TipoClienteEnum getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteEnum tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public long getTelefonoC() {
        return telefonoC;
    }

    public void setTelefonoC(long telefonoC) {
        this.telefonoC = telefonoC;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public void inactivar() {

        this.estado = EstadoEnum.INACTIVO;
    }
    public void activar() {

        this.estado = EstadoEnum.ACTIVO;
    }
    public void eliminar() {
        this.estado = EstadoEnum.INACTIVO;
    }
    
}