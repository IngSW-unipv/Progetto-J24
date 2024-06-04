package it.unipv.ingsfw.SmartWarehouse.Model.authentication;

import javax.swing.JFrame;




import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyFieldException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongFieldException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongOperatorException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.WarehouseOperator;


public class Login {
	
	public Login () {
		
	}

	public Client loginClient (String email, String password) throws EmptyFieldException, NullPointerException {
		fieldClCheck(email, password);	
		User u = SingletonManager.getInstance().getUserDAO().getClientByEmail(email);
		SingletonManager.getInstance().setLoggedUser(u);
		if(u==null) {
			throw new NullPointerException("Client not valid");
		}
		return (Client)u;
		
	}

	private void fieldClCheck(String email, String password) throws EmptyFieldException {
		if (email.isEmpty() == true || password.isEmpty() == true) {
			throw new EmptyFieldException();
		}
		
	}
	
	public WarehouseOperator loginOp (String id) throws WrongOperatorException,EmptyFieldException{
		fieldOpCheck(id);	
		User u = SingletonManager.getInstance().getUserDAO().getOpById(id);
		SingletonManager.getInstance().setLoggedUser(u);
		if(u==null) {
			throw new WrongOperatorException();
		} 
		return (WarehouseOperator)u;
	}	
	private void fieldOpCheck(String id) throws EmptyFieldException {
		if(id.isEmpty()==true) {
			throw new EmptyFieldException();
		}
	}

	 private void passwordClCheck(String email, String password) throws WrongFieldException {
		 	User u= SingletonManager.getInstance().getUserDAO().getClientByEmail(email);
		 	String pw=SingletonManager.getInstance().getUserDAO().selectPassword(u);
		 	if (pw!=password) {
	            throw new WrongFieldException();
	        }
	    }
	
}
