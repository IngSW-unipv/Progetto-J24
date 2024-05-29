package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LoginController {
	private LoginView view;
	private User model;

	public LoginController(LoginView view, User model) {
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
				model.setEmail(view.getEmail());
				model.setPassword(String.valueOf(view.getPassword()));
				
				Login logger = new Login(SingletonUser.getInstance().getUserDAO().selectUserByEmail(model));
				if (logger.login())
					view.dispose();

			}

		};

		view.getConfirmButton().addActionListener(confirm);

	}

}