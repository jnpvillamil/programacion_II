package co.edu.uptc.gui.modelo;

import co.edu.uptc.gui.interfaces.Gestionable;
import co.edu.uptc.dao.VendedorDao;
import co.edu.uptc.enums.TipoDocumentoEnum;

public class Vendedor extends Persona implements Gestionable {

    private double sueldoBase;
    private double porcentajeComision;

    public Vendedor() {
    }

    public Vendedor(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
                    String numeroDocumento, String telefono, String direccion, 
                    double sueldoBase, double porcentajeComision) {
        super(codigo, nombre, apellido, tipoDocumento, numeroDocumento, telefono, direccion);
        this.sueldoBase = sueldoBase;
        this.porcentajeComision = porcentajeComision;
    }

    public Vendedor(int idPrueba, String usuario, co.edu.uptc.enums.RolUsuarioEnum rol) {
        super(String.valueOf(idPrueba), usuario, "", null, "", "", "");
        this.setSueldoBase(0.0);
        this.setPorcentajeComision(0.0);
    }
    @Override
    public void registrar() {
        VendedorDao dao = new VendedorDao();
        dao.registrarVendedor(this);
    }

    @Override
    public void modificar() {
        VendedorDao dao = new VendedorDao();
        dao.actualizarVendedor(this);
    }

    @Override
    public void inactivar() {

    }

    public double getSueldoBase() { return sueldoBase; }
    public void setSueldoBase(double sueldoBase) { this.sueldoBase = sueldoBase; }
    public double getPorcentajeComision() { return porcentajeComision; }
    public void setPorcentajeComision(double porcentajeComision) { this.porcentajeComision = porcentajeComision; }
}