package it.unipv.ingsfw.SmartWarehouse.View;

import javax.swing.*;
import java.awt.*;


public class LoginView extends JFrame {

	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton confirmButton;
	
	public LoginView() {

		setTitle("Login");
        setSize(400, 150);
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginPanel.setBackground(new Color(214, 255, 255));
        add(loginPanel);

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        fieldsPanel.setBackground(new Color(214, 255, 255));
        loginPanel.add(fieldsPanel, BorderLayout.CENTER);

        fieldsPanel.add(new JLabel("email:"));
        emailField = new JTextField();
        fieldsPanel.add(emailField);

        fieldsPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        fieldsPanel.add(passwordField);

        confirmButton = new JButton("OK");

        loginPanel.add(confirmButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
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