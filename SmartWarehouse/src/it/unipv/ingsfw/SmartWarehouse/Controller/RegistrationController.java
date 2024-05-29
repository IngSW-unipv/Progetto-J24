package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

public class RegistrationController {
	private RegistrationView view;
	private User model;

	public RegistrationController(RegistrationView view, User model) {
		this.view = view;
		this.model = model;
		initComponents();
	}

	private void initComponents() {

		ActionListener confirm = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				model.setName(view.getNome());
				model.setSurname(view.getCognome());
				model.setEmail(view.getEmail());
				model.setAddress(view.getAddress());
				model.setEmail(view.getEmail());
				model.setPassword(String.valueOf(view.getPassword()));
				Registration reg = new Registration(model);
				if(reg.register(String.valueOf(view.getConfermaPassword())))
					view.dispose();
					

			}

		};
		view.getRegisterButton().addActionListener(confirm);
		ActionListener switchToLogin = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAction();
			}

			private void manageAction() {
				LoginView loginView = new LoginView();
				loginView.setVisible(true);
				view.dispose();

			}
		};

		view.getLoginButton().addActionListener(switchToLogin);

	}

}