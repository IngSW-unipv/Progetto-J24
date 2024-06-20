package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginOpView extends JFrame {
	private JPasswordField textId;
	private JButton confirm;

	public LoginOpView() {
		setTitle("Login");
		setVisible(true);
        setSize(400, 150);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(2, 1));
        add(panel);
        
        
        panel.add(new JLabel("ID:"));
        textId = new JPasswordField(10);
        panel.add(textId);
        
        
        confirm=new JButton("OK");
        panel.add(confirm);
        
	}
	
	public JPasswordField getTextId() {
		return textId;
	}

	public void setTextId(JPasswordField textId) {
		this.textId = textId;
	}

	public JButton getConfirmButton() {
		return confirm;
	}

	public void setConfirmButton(JButton confirm) {
		this.confirm = confirm;
	}
}
