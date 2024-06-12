
package it.unipv.ingsfw.SmartWarehouse.Model;

import it.unipv.ingsfw.SmartWarehouse.Database.IUserDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.UserDAO;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;

public class SingletonUser {

	private static SingletonUser instance= null;
	private IUserDAO userDAO;
	private User loggedUser;
	
	private SingletonUser() {
		userDAO=new UserDAO();
	}   
	
	public static synchronized SingletonUser getInstance() {
		if(instance==null) {
			instance = new SingletonUser();
		}
		return instance;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	

}
