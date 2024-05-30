package it.unipv.ingsfw.SmartWarehouse.Database;


import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
public interface IUserDAO {

	public User getClientByEmailAndPassword(String email, String password);
	public User getOpById (String id);
	public boolean insertClient (Client u);
	
	
}
