package co.edu.uptc.gui;

<<<<<<< HEAD
=======
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
>>>>>>> refs/remotes/origin/feature/grupo7

<<<<<<< HEAD

import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelRoles extends PanelBase {

    
private JComboBox<RolUsuario> cbRol;
private JButton btnRegistrar;
    private JButton btnListar;
    
    
    public void initComponents() {
    } 
    
}
=======
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelRoles extends PanelBase {

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
   
    public void initComponents() {
    }
}
>>>>>>> refs/remotes/origin/feature/grupo7
