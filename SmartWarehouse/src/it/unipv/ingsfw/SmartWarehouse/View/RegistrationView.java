package it.unipv.ingsfw.SmartWarehouse.View;

import javax.swing.*;
import java.awt.*;

public class RegistrationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel registrationPanel;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField addressField;
	private JTextField emailField;
	private JComboBox <String> typeField;
	private JPasswordField passwordField;
	private JPasswordField confermaPasswordField;
	private JButton registerButton;
	private JButton switchToLoginButton;

	public RegistrationView() {

		setTitle("Registrazione");
		setSize(400, 400);
		

		registrationPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		registrationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		registrationPanel.setBackground(new Color(214, 255, 255));
		add(registrationPanel);
		
		registrationPanel.add(new JLabel("Nome:"));
		nameField = new JTextField();
		registrationPanel.add(nameField);
	
		registrationPanel.add(new JLabel("Cognome:"));
		surnameField = new JTextField();
		registrationPanel.add(surnameField);
	

		registrationPanel.add(new JLabel("Email:"));
		emailField = new JTextField();
		registrationPanel.add(emailField);

		registrationPanel.add(new JLabel("Tipo:"));
        String[] tipo = {"client", "operator"};
		typeField = new JComboBox<>(tipo);
		registrationPanel.add(typeField);
		
		registrationPanel.add(new JLabel("Password:"));
		passwordField = new JPasswordField();
		registrationPanel.add(passwordField);

		registrationPanel.add(new JLabel("Conferma Password:"));
		confermaPasswordField = new JPasswordField();
		registrationPanel.add(confermaPasswordField);

		registerButton = new JButton("Registrati");
		switchToLoginButton = new JButton("Vai al Login");

		registrationPanel.add(registerButton);
		registrationPanel.add(switchToLoginButton);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		
	}

	public JPanel getRegistrationPanel() {
		return registrationPanel;
	}
	
	public String getNome() {
		return nameField.getText();
	}
	
	public String getCognome() {
		return surnameField.getText();
	}

	public String getAddress() {
		return addressField.getText();
	}

	public String getEmail() {
		return emailField.getText();
	}
	

	public char[] getPassword() {
		return passwordField.getPassword();
	}

	public char[] getConfermaPassword() {
		return confermaPasswordField.getPassword();
	}
	
	public JButton getRegisterButton() {
		return registerButton;
	}
	
	public JButton getLoginButton() {
		return switchToLoginButton;
	}

}