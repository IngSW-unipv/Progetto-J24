package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyFieldException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Shop;
import it.unipv.ingsfw.SmartWarehouse.Model.authentication.Login;
import it.unipv.ingsfw.SmartWarehouse.Model.authentication.Registration;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.View.LoginOpView;
import it.unipv.ingsfw.SmartWarehouse.View.LoginView;
import it.unipv.ingsfw.SmartWarehouse.View.MainView;
import it.unipv.ingsfw.SmartWarehouse.View.RegistrationView;
import it.unipv.ingsfw.SmartWarehouse.View.ShopFrame;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.InventoryView;

public class MainController {
	MainView mainView;
	LoginView loginView;
	RegistrationView regView;
	LoginOpView loginOpView;
	
	public MainController(MainView mainView) {
		this.mainView=mainView;
		clientInit();
		operatorInit();
		
	}
	
	private void clientInit() {
		ActionListener clientListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				int r = mainView.logOrReg();
				if (r==1) {
					mainView.setVisible(false);
					loginView = new LoginView();
					okLiginClientButton();
				} else {
					regView = new RegistrationView();
					okRegistrationClientButton() ;
					mainView.setVisible(false);
				}
			}
		};
		mainView.getClientButton().addActionListener(clientListener);
	} 
	
	private void operatorInit() {
		ActionListener operatorListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				loginOpView = new LoginOpView();
				okOpButtonInit();
			}
		};
		mainView.getOperatorButton().addActionListener(operatorListener);
	} 
	
	private void okLiginClientButton() {
		ActionListener okListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				Login login = new Login();
				try {
					login.loginClient(loginView.getEmailField().getText(), String.valueOf(loginView.getPasswordField().getPassword()));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(loginView, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				loginView.setVisible(false);
				new ShopController(new Shop(), new ShopFrame()); 
				new ShopFrame();			
			}
		};
		loginView.getConfirmButton().addActionListener(okListener);
	}
	
	private void okRegistrationClientButton() {
		ActionListener okListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				Client c = new Client (regView.getNameField().getText(), regView.getSurnameField().getText(),
						regView.getEmailField().getText(), String.valueOf(regView.getPasswordField().getPassword()));
				Registration registration = new Registration(c);
				try {
					registration.registerClient();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(loginOpView, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				regView.setVisible(false);
				loginView = new LoginView();
				okLiginClientButton();	
			}
		};
		regView.getConfirmButton().addActionListener(okListener);
	}
	
	private void okOpButtonInit() {
		ActionListener okListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				Login login = new Login();
				try {
					login.loginOp(String.valueOf(loginOpView.getTextId().getPassword()));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(loginOpView, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				loginOpView.setVisible(false);
				InventoryView iv=new InventoryView();
				new InventoryController(new InventoryManager(), iv);
				new SupplyController(new SupplyManager(), iv.getSupplyPanel());
				//controller picking
			}
		};
		loginOpView.getOk().addActionListener(okListener);
	}
	
}
