package it.unipv.ingsfw.SmartWarehouse.View;

import javax.swing.*;
import java.awt.*;

public class RegistrationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel registrationPanel;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton confirmButton;



	public RegistrationView() {

		setTitle("Registrazione");
		setSize(400, 400);
		

		registrationPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		add(registrationPanel);
		
		registrationPanel.add(new JLabel("Name:"));
		nameField = new JTextField();
		registrationPanel.add(nameField);
	
		registrationPanel.add(new JLabel("Surname:"));
		surnameField = new JTextField();
		registrationPanel.add(surnameField);
	

		registrationPanel.add(new JLabel("Email:"));
		emailField = new JTextField();
		registrationPanel.add(emailField);
		
		registrationPanel.add(new JLabel("Password:"));
		passwordField = new JPasswordField();
		registrationPanel.add(passwordField);


		confirmButton = new JButton("OK");

		registrationPanel.add(confirmButton);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setVisible(true);
		
	}
	
	public JTextField getNameField() {
		return nameField;
	}


	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}


	public JTextField getSurnameField() {
		return surnameField;
	}


	public void setSurnameField(JTextField surnameField) {
		this.surnameField = surnameField;
	}


	public JTextField getEmailField() {
		return emailField;
	}


	public void setEmailField(JTextField emailField) {
		this.emailField = emailField;
	}


	public JPasswordField getPasswordField() {
		return passwordField;
	}


	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}


	public JButton getConfirmButton() {
		return confirmButton;
	}


	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}



}