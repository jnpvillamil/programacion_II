package co.uptc.edu.gui;
import javax.swing.*;


import java.awt.*;
public class PanelLogin extends JPanel{
	
	private JTextField textUsuario;
	private JPasswordField txtPassword;
	private JLabel texto1,texto2,titulo;
	private JButton btnCancelar,btnLogin;
	public PanelLogin(Evento e){
		
				this.setLayout(new BorderLayout(10, 10));


				titulo = new JLabel("TIENDA MINORISTA UPTC", SwingConstants.CENTER);
				titulo.setFont(new Font("Arial", Font.BOLD, 18));
				this.add(titulo, BorderLayout.NORTH);

				
				JPanel subPanel = new JPanel();
				subPanel.setLayout(new GridLayout(2, 2, 10, 10));
				
				textUsuario = new JTextField();
				txtPassword = new JPasswordField();
				texto1 = new JLabel("Usuario:");
				texto2 = new JLabel("Contraseña:");
				
				subPanel.add(texto1);       
				subPanel.add(textUsuario);  
				subPanel.add(texto2);       
				subPanel.add(txtPassword);  

				JPanel temporal = new JPanel();
				btnCancelar = new JButton(Evento.CANCELAR);
				btnCancelar.addActionListener(e);
				btnCancelar.setActionCommand(Evento.CANCELAR);
				btnLogin = new JButton(Evento.LOGIN);
				btnLogin.addActionListener(e);
				btnLogin.setActionCommand(Evento.LOGIN);
				temporal.add(btnCancelar);
				temporal.add(btnLogin);
				
				
				this.add(subPanel, BorderLayout.CENTER);
				this.add(temporal, BorderLayout.SOUTH);

				
				this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50,50));

	}
}
