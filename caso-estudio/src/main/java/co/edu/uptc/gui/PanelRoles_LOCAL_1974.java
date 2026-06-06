package co.edu.uptc.gui;

import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelRoles extends PanelBase {

	   private JTextField txtNombre;
	    private JTextField txtUsuario;
	    private JPasswordField txtClave;
	    private JComboBox<RolUsuario> cbRol;
	    private JButton btnRegistrar;
	    private JButton btnListar;
	    private JTable tablaUsuarios;
	    private DefaultTableModel modeloTabla;
   
    public void initComponents() {
    }
}
