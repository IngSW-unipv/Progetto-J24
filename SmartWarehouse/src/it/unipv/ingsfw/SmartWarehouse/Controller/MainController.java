package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import it.unipv.ingsfw.SmartWarehouse.Exception.AccountAlreadyExistsException;
import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyFieldException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongOperatorException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.Shop;
import it.unipv.ingsfw.SmartWarehouse.Model.authentication.Login;
import it.unipv.ingsfw.SmartWarehouse.Model.authentication.Registration;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryManager;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.WarehouseOperator;
import it.unipv.ingsfw.SmartWarehouse.View.LoginOpView;
import it.unipv.ingsfw.SmartWarehouse.View.LoginClView;
import it.unipv.ingsfw.SmartWarehouse.View.MainView;
import it.unipv.ingsfw.SmartWarehouse.View.PickingView;
import it.unipv.ingsfw.SmartWarehouse.View.RegistrationView;
import it.unipv.ingsfw.SmartWarehouse.View.ShopFrame;
import it.unipv.ingsfw.SmartWarehouse.View.inventory.InventoryView;

public class MainController {
	MainView mainView;
	LoginClView loginView;
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
					loginView = new LoginClView();
					okLoginClientButton();
				} else if(r==0) {
					regView = new RegistrationView();
					okRegistrationClientButton();
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
	private void okLoginClientButton() {
		ActionListener okListener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}
			private void manageAction() {
				Login login = new Login();
				try {
					
					login.loginClient(loginView.getEmailField().getText(), String.valueOf(loginView.getPasswordField().getPassword()));
					loginView.setVisible(false);
					new ShopController(new Shop(), new ShopFrame()); 
					
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(loginView, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		};
		loginView.getConfirmButton().addActionListener(okListener);
	}
	private void okRegistrationClientButton() {
        ActionListener okListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    manageAction();
                } catch (EmptyFieldException | AccountAlreadyExistsException e1) {
                    JOptionPane.showMessageDialog(regView, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void manageAction() throws EmptyFieldException, AccountAlreadyExistsException {
                Client c = new Client(
                    regView.getNameField().getText(),
                    regView.getSurnameField().getText(),
                    regView.getEmailField().getText(),
                    String.valueOf(regView.getPasswordField().getPassword())
                );
                Registration registration = new Registration(c);
                registration.registerClient();
                regView.setVisible(false);
                loginView = new LoginClView();
                okLoginClientButton();
                loginView.setVisible(true);
            }
        };
        regView.getConfirmButton().addActionListener(okListener);
    }
	
	private void okOpButtonInit() {
	    ActionListener okListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                manageAction();
	            } catch (WrongOperatorException e1) {
	                e1.printStackTrace();
	                JOptionPane.showMessageDialog(loginOpView, e1.getMessage(), "Errore Operatore", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	        private void manageAction() throws WrongOperatorException {
	            Login login = new Login();
	            String idview = String.valueOf(loginOpView.getTextId().getPassword());
	            try {
	                login.loginOp(String.valueOf(loginOpView.getTextId().getPassword()));
	            } catch (Exception e) {
	                
	                JOptionPane.showMessageDialog(loginOpView, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
	                return; 
	            }
	            if (idview.charAt(0) == 'i' || idview.charAt(0) == 's') {
	                loginOpView.setVisible(false);
	                InventoryView iv = new InventoryView();
	                mainView.setVisible(false);
	                new InventoryController(new InventoryManager(), iv);
	                new SupplyController(new SupplyManager(), iv.getSupplyPanel());
	                
	            } else if (idview.charAt(0) == 'p') {
	                loginOpView.setVisible(false);
	                PickingView viewp = new PickingView();
	                new PickingController(viewp);
	            } else {
	                throw new WrongOperatorException();
	            }
	        }
	    };
	    loginOpView.getConfirmButton().addActionListener(okListener);
	}

	
}
