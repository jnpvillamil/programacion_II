package uptc.edu.co.controller;

import uptc.edu.co.controller.UsuarioController.Resultado;
import uptc.edu.co.dao.ProductoDAO;
import uptc.edu.co.model.Producto;
import uptc.edu.co.util.Validador;
import java.io.IOException;
import java.util.List;


//CRUD

public class ProductoController {

    private final ProductoDAO dao = new ProductoDAO();

    //CREATE
    
    public Resultado crear(String codigo, String nombre, String precio, String stock) {

        
        if (Validador.esCampoVacio(codigo)) {
            return Resultado.error("El código es obligatorio");
        }
        if (!Validador.esCodigoValido(codigo)) {
            return Resultado.error(Validador.mensajeErrorCodigo());
        }
        if (Validador.esCampoVacio(nombre)) {
            return Resultado.error("El nombre es obligatorio");
        }
        if (!Validador.esPrecioValido(precio)) {
            return Resultado.error(Validador.mensajeErrorPrecio());
        }
        if (!Validador.esStockValido(stock)) {
            return Resultado.error(Validador.mensajeErrorStock());
        }

        try {
            
            double precioDouble = Double.parseDouble(precio.trim());
            int    stockInt     = Integer.parseInt(stock.trim());

            
            Producto nuevo = new Producto(
                codigo.trim().toUpperCase(),
                nombre.trim(),
                precioDouble,
                stockInt
            );

            boolean guardado = dao.guardar(nuevo);

            if (guardado) {
                return Resultado.exito("Producto '" + nombre + "' creado correctamente");
            } else {
                return Resultado.error("Ya existe un producto con el código " + codigo.toUpperCase());
            }

        } catch (NumberFormatException e) {
            return Resultado.error("Error de formato en precio o stock");
        } catch (IOException e) {
            return Resultado.error("Error al guardar: " + e.getMessage());
        }
    }

    // READ
    
    public List<Producto> listar() throws IOException {
        return dao.listarTodos();
    }

    
     
    
    public Producto buscar(String codigo) throws IOException {
        if (Validador.esCampoVacio(codigo)) return null;
        return dao.buscarPorCodigo(codigo.trim().toUpperCase());
    }

    // UPDATE
     
    public Resultado actualizar(String codigo, String nombre, String precio, String stock) {
        
        if (Validador.esCampoVacio(nombre)) {
            return Resultado.error("El nombre es obligatorio");
        }
        if (!Validador.esPrecioValido(precio)) {
            return Resultado.error(Validador.mensajeErrorPrecio());
        }
        if (!Validador.esStockValido(stock)) {
            return Resultado.error(Validador.mensajeErrorStock());
        }

        try {
            double precioDouble = Double.parseDouble(precio.trim());
            int    stockInt     = Integer.parseInt(stock.trim());

            Producto actualizado = new Producto(
                codigo.trim().toUpperCase(),
                nombre.trim(),
                precioDouble,
                stockInt
            );

            boolean ok = dao.actualizar(actualizado);
            if (ok) {
                return Resultado.exito("Producto actualizado correctamente");
            } else {
                return Resultado.error("Producto no encontrado");
            }

        } catch (NumberFormatException e) {
            return Resultado.error("Error de formato en precio o stock");
        } catch (IOException e) {
            return Resultado.error("Error al actualizar: " + e.getMessage());
        }
    }

    //DELETE
    
    public Resultado eliminar(String codigo) {
        if (Validador.esCampoVacio(codigo)) {
            return Resultado.error("Selecciona un producto para eliminar");
        }
        try {
            boolean eliminado = dao.eliminar(codigo.trim().toUpperCase());
            if (eliminado) {
                return Resultado.exito("Producto eliminado correctamente");
            } else {
                return Resultado.error("Producto no encontrado");
            }
        } catch (IOException e) {
            return Resultado.error("Error al eliminar: " + e.getMessage());
        }
    }
}
