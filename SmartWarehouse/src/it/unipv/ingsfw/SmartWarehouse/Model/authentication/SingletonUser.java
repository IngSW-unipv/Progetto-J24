package it.unipv.ingsfw.SmartWarehouse.Model.authentication;

import it.unipv.ingsfw.SmartWarehouse.operator.*;


public class SingletonUser {
	private static SingletonUser instance;
	private WarehouseOperator op;
	private UserDAO userDAO;
	private MessaggioDAO messaggioDAO;
	
	private SingletonUser() {
		op=new InventoryOperator();
		new User(null, null, null, null, null, null);
	}   
	
	public static synchronized SingletonUser getInstance() {
		if(instance==null) {
			instance = new SingletonUser();
		}
		return instance;
	}
	
	public WarehouseOperator getOp() {
		return op;
	}
	public void setLoggedUser(User u) {
		
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public MessaggioDAO getMessaggioDAO() {
		return messaggioDAO;
	}
}
