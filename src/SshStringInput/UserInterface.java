package SshStringInput;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.Color;

public class UserInterface extends JFrame implements ActionListener{
	private JTextField servidorText;
	private JTextField peText;
	private JTextField enviarText;
	JTextArea respuestaText;
	private JButton peBtn;
	private JButton btnYes;
	private JButton btnClavePE, enviarBtn;

	public UserInterface() {
		setResizable(false);
		getContentPane().setLayout(null);
		this.setSize(400, 350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton servidorBtn = new JButton("Conectar PE user@host");
		servidorBtn.setBounds(5, 5, 179, 23);
		getContentPane().add(servidorBtn);
		servidorBtn.addActionListener(this);
		
		servidorText = new JTextField();
		servidorText.setBounds(366, 6, 18, 20);
		servidorText.setToolTipText("Ingrese IP Servidor");
		getContentPane().add(servidorText);
		servidorText.setColumns(10);
		
		peBtn = new JButton("Conectar PE");
		peBtn.setBounds(5, 34, 146, 23);
		getContentPane().add(peBtn);
		peBtn.addActionListener(this);
		
		peText = new JTextField();
		peText.setBounds(161, 37, 223, 20);
		peText.setToolTipText("Ingrese IP de PE");
		getContentPane().add(peText);
		peText.setColumns(10);
		
		enviarText = new JTextField();
		enviarText.setBounds(5, 68, 306, 23);
		enviarText.setToolTipText("ingrese comando");
		getContentPane().add(enviarText);
		enviarText.setColumns(10);
		
		enviarBtn = new JButton("Ver interfaz");
		enviarBtn.setBounds(313, 68, 71, 23);
		getContentPane().add(enviarBtn);
		enviarBtn.addActionListener(this);
		
		respuestaText = new JTextArea();
		respuestaText.setBackground(Color.BLACK);
		respuestaText.setBounds(5, 110, 379, 200);
		getContentPane().add(respuestaText);
		
		btnYes = new JButton("Yes");
		btnYes.setBounds(196, 5, 55, 23);
		getContentPane().add(btnYes);
		btnYes.addActionListener(this);
		
		btnClavePE = new JButton("clave PE");
		btnClavePE.setBounds(261, 5, 89, 23);
		getContentPane().add(btnClavePE);
		btnClavePE.addActionListener(this);
		this.setVisible(true);
	}

	public void setRespuesta(String s) {

		respuestaText.setText(s);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==peBtn) {SshStringInput.conectarRouterPE();}   
		
		if(e.getSource()==btnYes) {SshStringInput.enviarYes();}
		
		if(e.getSource()==btnClavePE) {SshStringInput.enviarClave();}
		
		if(e.getSource()==enviarBtn) {SshStringInput.verInterfaz();}
	}
}
