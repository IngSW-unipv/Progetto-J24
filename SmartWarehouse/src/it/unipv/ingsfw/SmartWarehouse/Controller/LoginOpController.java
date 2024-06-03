package it.unipv.ingsfw.SmartWarehouse.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.ingsfw.SmartWarehouse.Model.authentication.Login;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.WarehouseOperator;
import it.unipv.ingsfw.SmartWarehouse.View.LoginOpView;
//

public class LoginOpController {
	private LoginOpView view;
	private WarehouseOperator op;

	public LoginOpController(LoginOpView view,WarehouseOperator op) {
		this.view = view;
		this.op = op;
		initComponents();
	}
	
	private void initComponents() {
		ActionListener confirmOp = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageClAction();
			}
				private void manageClAction() {
					op.getId();
					Login logger = new Login();
					view.dispose();

				}

			};

		        view.getConfirmButton().addActionListener(confirmOp);
		    }
			
}

