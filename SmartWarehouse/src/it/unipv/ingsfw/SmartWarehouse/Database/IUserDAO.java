package it.unipv.ingsfw.SmartWarehouse.Database;


import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
public interface IUserDAO {

	public User getClientByEmail(String email);
	public User getOpById (String id);
	public boolean insertClient (Client u);
	public String selectPassword(User u);
	
	
}
