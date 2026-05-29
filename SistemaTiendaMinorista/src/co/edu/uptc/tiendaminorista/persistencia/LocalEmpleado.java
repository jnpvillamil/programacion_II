package co.edu.uptc.tiendaminorista.persistencia;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.tiendaminorista.interfaces.IGestionEmpleado;
import co.edu.uptc.tiendaminorista.modelo.Empleado;

public class LocalEmpleado implements IGestionEmpleado {
    private static final String RUTA = "empleados.json";
    private final Gson gson = new Gson();

    private List<Empleado> leer() {
        try (FileReader reader = new FileReader(RUTA)) {
            Type tipo = new TypeToken<List<Empleado>>() {}.getType();
            List<Empleado> lista = gson.fromJson(reader, tipo);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void escribir(List<Empleado> lista) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Empleado empleado) {
        List<Empleado> lista = leer();
        
    
        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setCorreo(empleado.getCorreo());
        nuevoEmpleado.setPassword(empleado.getPassword());
        nuevoEmpleado.setTipoEmpleado(empleado.getTipoEmpleado()); 
        
        lista.add(nuevoEmpleado);
        escribir(lista);
    }

    @Override
    public List<Empleado> listar() { 
        return leer(); 
    }

    @Override
    public void actualizar(Empleado empleado) {
        List<Empleado> lista = leer();
        for (int i = 0; i < lista.size(); i++) {
          
            if (lista.get(i).getCorreo().equalsIgnoreCase(empleado.getCorreo())) {
                
                Empleado empActualizado = new Empleado();
                empActualizado.setCorreo(empleado.getCorreo());
                empActualizado.setPassword(empleado.getPassword());
                empActualizado.setTipoEmpleado(empleado.getTipoEmpleado());
                
                lista.set(i, empActualizado);
                break;
            }
        }
        escribir(lista);
    }

    @Override
    public void eliminar(Empleado empleado) {
        List<Empleado> lista = leer();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCorreo().equalsIgnoreCase(empleado.getCorreo())) {
                lista.remove(i);
                break;
            }
        }
        escribir(lista);
    }
}