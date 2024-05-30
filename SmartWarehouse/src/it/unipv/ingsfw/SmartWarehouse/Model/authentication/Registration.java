package it.unipv.ingsfw.SmartWarehouse.Model.authentication;

import javax.swing.JFrame;

import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class Registration {
	private Client c;

	public Registration(Client c) {
		this.c=c;
	}

	//gestire eccezioni!!!!
	public boolean registerClient() throws Exception {
		//fieldCheck
		if(SingletonManager.getInstance().getUserDAO().getClientByEmailAndPassword(c.getEmail(), c.getPassword())==null){
			SingletonManager.getInstance().getUserDAO().insertClient(c);
		} else {
			throw new Exception();
		}
	}

	private void fieldCheck(String password) throws EmptyFieldException {
		if (this.u.getEmail().isEmpty() || this.u.getName().isEmpty() || this.u.getSurname().isEmpty()
				|| this.u.getEmail().isEmpty() || this.u.getAddress().isEmpty()
				|| String.valueOf(this.u.getPassword()).equals("") || password.equals("")) {
			throw new EmptyFieldException();
		}
	}



}