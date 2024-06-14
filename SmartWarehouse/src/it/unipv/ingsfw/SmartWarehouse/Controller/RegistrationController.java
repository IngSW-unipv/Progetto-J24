package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;



import java.awt.event.ActionListener;
import it.unipv.ingsfw.SmartWarehouse.Model.authentication.Registration;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.View.LoginClView;
import it.unipv.ingsfw.SmartWarehouse.View.RegistrationView;

public class RegistrationController {
	private RegistrationView view;
	private Client c;

	public RegistrationController(RegistrationView view, Client c) {
		this.view = view;
		this.c =c;
		initComponents();
	}

	private void initComponents() {

		ActionListener confirm = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				c.setName(String.valueOf(view.getNameField()));
				c.setSurname(String.valueOf(view.getSurnameField()));
				c.setEmail(String.valueOf(view.getEmailField()));
				new Registration(c);
				view.dispose();

			}

		};
		view.getConfirmButton().addActionListener(confirm);
		ActionListener switchToLogin = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				LoginClView loginView = new LoginClView();
				loginView.setVisible(true);
				view.dispose();

			}
		};

		view.getConfirmButton().addActionListener(switchToLogin);

	}

}
