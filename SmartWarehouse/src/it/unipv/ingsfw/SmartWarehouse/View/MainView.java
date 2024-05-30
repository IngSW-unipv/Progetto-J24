package it.unipv.ingsfw.SmartWarehouse.View;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unipv.ingsfw.SmartWarehouse.Controller.MainController;

public class MainView extends JFrame {
	    private JButton clientButton;
	    private JButton operatorButton;

	    public MainView() {
	        super("Welcome to Warehouse");

	        clientButton = new JButton("Client");
	        operatorButton = new JButton("WarehouseOperator");

	        setLayout(new GridLayout(2, 1));

	        add(clientButton);
	        add(operatorButton);

	        setSize(300, 100);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null); 
	        setVisible(true);
	    }
	    
	    public int logOrReg() {
	    	String[] option= { "Sign in", "Login" };
	    	int r= JOptionPane.showOptionDialog(this, "Client", "Login or Sign in", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
	    	return r;
	    }
	    
	    public static void main(String[] args) {
			MainView m=new MainView();
			MainController mc = new MainController(m);
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
