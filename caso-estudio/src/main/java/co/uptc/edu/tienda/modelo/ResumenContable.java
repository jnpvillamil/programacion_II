package co.uptc.edu.tienda.modelo;

public class ResumenContable {
    private double ingresos;
    private double egresos;
    private double utilidad;
    private double ivaGenerado;
    private double ivaDescontable;

    public ResumenContable(double ingresos, double egresos,
                           double ivaGenerado, double ivaDescontable) {
        this.ingresos = ingresos;
        this.egresos = egresos;
        this.utilidad = ingresos - egresos;
        this.ivaGenerado = ivaGenerado;
        this.ivaDescontable = ivaDescontable;
    }

    public double getIngresos() { return ingresos; }
    public void setIngresos(double ingresos) { this.ingresos = ingresos; }

    public double getEgresos() { return egresos; }
    public void setEgresos(double egresos) { this.egresos = egresos; }

    public double getUtilidad() { return utilidad; }
    public void setUtilidad(double utilidad) { this.utilidad = utilidad; }

    public double getIvaGenerado() { return ivaGenerado; }
    public void setIvaGenerado(double ivaGenerado) { this.ivaGenerado = ivaGenerado; }

    public double getIvaDescontable() { return ivaDescontable; }
    public void setIvaDescontable(double ivaDescontable) { this.ivaDescontable = ivaDescontable; }
}