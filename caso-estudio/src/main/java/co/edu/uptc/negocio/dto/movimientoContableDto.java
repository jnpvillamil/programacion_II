package co.edu.uptc.negocio.dto;

public class movimientoContableDto {

    public static int contadorMovimiento = 600;

    private int codigoTransaccion;
    private String fecha;
    private String tipoMovimiento;
    private String cuentaContable;
    private double valor;
    private String descripcion;

    public movimientoContableDto() {
        this.codigoTransaccion = contadorMovimiento++;
    }

    public movimientoContableDto(int codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public int getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getCuentaContable() {
        return cuentaContable;
    }
    public void setCuentaContable(String cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "movimientoContableDto [codigoTransaccion=" + codigoTransaccion+ ", fecha=" + fecha+ ", tipoMovimiento=" + tipoMovimiento+ ", cuentaContable=" + cuentaContable+ ", valor=" + valor+ ", descripcion=" + descripcion + "]";
    }
}