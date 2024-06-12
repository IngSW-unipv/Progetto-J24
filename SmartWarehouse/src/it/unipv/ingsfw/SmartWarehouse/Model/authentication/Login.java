package it.unipv.ingsfw.SmartWarehouse.Model.authentication;

import javax.swing.JFrame;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyFieldException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongFieldException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongOperatorException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.WarehouseOperator;


public class Login {
	
	public Login () {
		
	}
	/*
	 * login method for the client
	 */
	public void loginClient(String email, String password) throws EmptyFieldException, NullPointerException, WrongFieldException {
        fieldClCheck(email, password);
        User u = SingletonUser.getInstance().getUserDAO().getClientByEmail(email);
        if (u == null) {
            throw new NullPointerException("Client not valid");
        }
        passwordClCheck(email, password);
        SingletonUser.getInstance().setLoggedUser(u);
    }
	private void fieldClCheck(String email, String password) throws EmptyFieldException {
		if (email.isEmpty() == true || password.isEmpty() == true) {
			throw new EmptyFieldException();
		}
		
	}
	/*
	 * login method for the operator
	 */
	
	public void loginOp (String id) throws WrongOperatorException,EmptyFieldException{
		fieldOpCheck(id);	
		User u = SingletonUser.getInstance().getUserDAO().getOpById(id);
		SingletonUser.getInstance().setLoggedUser(u); 
		if(u==null) {
			throw new WrongOperatorException();
		} 
	}	
	
	private void fieldOpCheck(String id) throws EmptyFieldException {
		if(id.isEmpty()==true) {
			throw new EmptyFieldException();
		}
	}
	/*
	 * method for check if the password and the email are correct
	 */

	private void passwordClCheck(String email, String password) throws WrongFieldException {
        User u = SingletonUser.getInstance().getUserDAO().getClientByEmail(email);
        String pw= SingletonUser.getInstance().getUserDAO().selectPassword(u);

        if (!pw.equals(password)) {  
            throw new WrongFieldException();
        }
    }
	
}
