package it.unipv.ingsfw.SmartWarehouse.Model.authentication;

import javax.swing.JFrame;

import com.mysql.cj.xdevapi.Client;

import it.unipv.ingsfw.SmartWarehouse.Exception.EmptyFieldException;
import it.unipv.ingsfw.SmartWarehouse.Exception.WrongFieldException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.WarehouseOperator;


public class Login {

	public Login () {
	}

	public Client loginClient (String email, String password) throws EmptyFieldException, NullPointerException {
		fieldCheck(email, password);	
		
		User u = SingletonManager.getInstance().getUserDAO().getClientByEmailAndPassword(email, password);
		SingletonManager.getInstance().setLoggedUser(u);
			
		if(u==null) {
			throw new NullPointerException("Client not valid");
		} 
		return (Client)u;
		
	}

	private void fieldCheck(String email, String password) throws EmptyFieldException {
		
		if (email.isEmpty() == true || password.isEmpty() == true) {
			throw new EmptyFieldException();
		}
		
		
	}
	
	public WarehouseOperator loginOp (String id) throws NullPointerException {
		//fieldCheck(id);	
		
		User u = SingletonManager.getInstance().getUserDAO().getOpById(id);
		SingletonManager.getInstance().setLoggedUser(u);
			
		if(u==null) {
			throw new NullPointerException("Operator not valid");
		} 
		return (WarehouseOperator)u;
	}	
		
	/*
	private void passwordCheck() throws WrongFieldException {

		if (!u.getPassword().equals(SingletonUser.getInstance().getUserDAO().selectPassword(u))) {
			throw new WrongFieldException();
		}

	}
	*/
}
