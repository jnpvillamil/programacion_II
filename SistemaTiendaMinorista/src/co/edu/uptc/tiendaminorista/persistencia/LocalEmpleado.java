package co.edu.uptc.tiendaminorista.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.tiendaminorista.interfaces.IGestionEmpleado;
import co.edu.uptc.tiendaminorista.modelo.Empleado;

public class LocalEmpleado implements IGestionEmpleado {

    private static final String RUTA_ARCHIVO = "target/empleados.json";
    private Gson gson;

    public LocalEmpleado() {
        this.gson = new Gson();
        verificarArchivo();
    }

    private void verificarArchivo() {
        try {
            File archivo = new File(RUTA_ARCHIVO);
            if (!archivo.exists()) {
                archivo.getParentFile().mkdirs(); 
                archivo.createNewFile();
                escribirLista(new ArrayList<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void escribirLista(List<Empleado> lista) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Empleado empleado) {
        List<Empleado> listaActual = listar();
        listaActual.add(empleado);
        escribirLista(listaActual);
    }

    @Override
    public void actualizar(Empleado empleadoModificado) {
        List<Empleado> listaActual = listar();
        for (int i = 0; i < listaActual.size(); i++) {
            Empleado emp = listaActual.get(i);
            if (emp.getCorreo().equals(empleadoModificado.getCorreo())) {
                listaActual.set(i, empleadoModificado);
                break;
            }
        }
        escribirLista(listaActual);
    }

    @Override
    public List<Empleado> listar() {
        File archivo = new File(RUTA_ARCHIVO);
        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Empleado>>() {}.getType();
            List<Empleado> lista = gson.fromJson(reader, tipoLista);
            
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public void eliminar(Empleado empleado) {
        List<Empleado> lista = listar(); 
        
        if (lista != null) {
          
            lista.removeIf(emp -> emp.getCorreo().equalsIgnoreCase(empleado.getCorreo()));
            
     
            escribirLista(lista); 
        }
    }
}