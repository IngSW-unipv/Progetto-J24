package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unipv.ingsfw.SmartWarehouse.Controller.MainController;

public class MainView extends JFrame {
	    private JButton clientButton;
	    private JButton operatorButton;

	    public MainView() {
	    	
	        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        JPanel panel = new JPanel();
   
	        JLabel l = new JLabel("Welcome to Warehouse");
	        clientButton = new JButton("Log in as Customer");
	        operatorButton = new JButton("Log in as WarehouseOperator");
	        panel.add(l);
	        panel.add(clientButton);
	        panel.add(operatorButton);
	        this.getContentPane().add(panel);
	        
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null); 
	        setVisible(true);
	    }
	    
	    public int logOrReg() {
	    	String[] option= { "Sign in", "Login" };
	    	int r= JOptionPane.showOptionDialog(this, "Client", "Login or Sign in", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
	    	return r;
	    }

		public JButton getClientButton() {
			return clientButton;
		}

		public void setClienteButton(JButton clientButton) {
			this.clientButton = clientButton;
		}

		public JButton getOperatorButton() {
			return operatorButton;
		}

		public void setOperatorButton(JButton operatorButton) {
			this.operatorButton = operatorButton;
		}
}
