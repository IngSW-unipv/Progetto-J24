package it.unipv.ingsfw.SmartWarehouse.Model.authentication;

import javax.swing.JFrame;
import it.unipv.ingsfw.SmartWarehouse.Exception.AccountAlreadyExistsException;
import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyFieldException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;

public class Registration {
	private Client c;

	public Registration(Client c) {
		this.c=c;
	}
	/*
	 * method for the registration of the client
	 */
	
	public boolean registerClient() throws AccountAlreadyExistsException, EmptyFieldException {
        fieldCheck();
        boolean result = false;
        if (SingletonUser.getInstance().getUserDAO().getClientByEmail(c.getEmail()) == null) {
        	SingletonUser.getInstance().getUserDAO().insertClient(c);
            result = true;
        } else {
            throw new AccountAlreadyExistsException();
        }
        return result;
    }
	/*
	 * method to check if there are some empty filed
	 */

	private void fieldCheck() throws EmptyFieldException {
		
		if (this.c.getEmail().isEmpty() || this.c.getName().isEmpty() || this.c.getSurname().isEmpty()
				|| this.c.getEmail().isEmpty()|| String.valueOf(this.c.getPassword()).equals("") )
		{
			throw new EmptyFieldException();
		}
	}



}
