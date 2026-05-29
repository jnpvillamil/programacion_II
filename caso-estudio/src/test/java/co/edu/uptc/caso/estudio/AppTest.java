package co.edu.uptc.caso.estudio;

import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.negocio.ServicioAutorizacion;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

public class AppTest extends TestCase {

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testVentaCalculaIvaAlDiecinuevePorCiento() {
        Venta venta = crearVentaConUnProducto(100.0, 2);
        assertEquals(200.0, venta.calcularSubtotal(), 0.01);
        assertEquals(38.0, venta.calcularIVA(), 0.01);
        assertEquals(238.0, venta.calcularTotal(), 0.01);
    }

    public void testCompraCalculaTotales() {
        Compra compra = crearCompraConUnProducto(50.0, 4);
        assertEquals(200.0, compra.calcularSubtotal(), 0.01);
        assertEquals(38.0, compra.calcularIVA(), 0.01);
        assertEquals(238.0, compra.calcularTotal(), 0.01);
    }

    public void testCajeroSoloAccedeAVentasClientesYCerrarSesion() {
        ServicioAutorizacion autorizacion = new ServicioAutorizacion();
        assertTrue(autorizacion.verificarPermiso(RolUsuario.CAJERO, "VENTAS"));
        assertTrue(autorizacion.verificarPermiso(RolUsuario.CAJERO, "CLIENTES"));
        assertFalse(autorizacion.verificarPermiso(RolUsuario.CAJERO, "INVENTARIO"));
        assertFalse(autorizacion.verificarPermiso(RolUsuario.CAJERO, "REPORTES"));
    }

    public void testAdministradorAccedeATodosLosModulos() {
        ServicioAutorizacion autorizacion = new ServicioAutorizacion();
        assertTrue(autorizacion.verificarPermiso(RolUsuario.ADMINISTRADOR, "INVENTARIO"));
        assertTrue(autorizacion.verificarPermiso(RolUsuario.ADMINISTRADOR, "COMPRAS"));
        assertTrue(autorizacion.verificarPermiso(RolUsuario.ADMINISTRADOR, "CONSULTAS"));
    }

    private Venta crearVentaConUnProducto(double precioUnitario, int cantidad) {
        Producto producto = new Producto();
        producto.setCodigoProducto("P001");
        producto.setPrecioVenta(precioUnitario);

        DetalleVenta detalle = new DetalleVenta(producto, cantidad, precioUnitario, precioUnitario * cantidad);

        Venta venta = new Venta();
        venta.setProductosVendidos(List.of(detalle));
        return venta;
    }

    private Compra crearCompraConUnProducto(double costoUnitario, int cantidad) {
        Producto producto = new Producto();
        producto.setCodigoProducto("P001");

        DetalleVenta detalle = new DetalleVenta(producto, cantidad, costoUnitario, costoUnitario * cantidad);

        Compra compra = new Compra();
        compra.setProductosComprados(List.of(detalle));
        return compra;
    }
}
