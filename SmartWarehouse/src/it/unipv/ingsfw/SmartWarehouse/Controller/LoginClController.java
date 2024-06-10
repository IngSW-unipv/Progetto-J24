package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;

import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.authentication.Login;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.View.LoginClView;
public class LoginClController {
	private LoginClView view;
	private Client c;

	public LoginClController(LoginClView view, Client c) {
		this.view = view;
		this.c = c;
		initComponents();
	}


	private void initComponents() {

		ActionListener confirmCl = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageClAction();
			}

				private void manageClAction() {
					c.setEmail((String.valueOf(view.getEmailField())));
					c.setPassword((String.valueOf(view.getPasswordField())));
					Login logger = new Login();
					view.dispose();

			}

		};

	        view.getConfirmButton().addActionListener(confirmCl);
	    }
		
}
