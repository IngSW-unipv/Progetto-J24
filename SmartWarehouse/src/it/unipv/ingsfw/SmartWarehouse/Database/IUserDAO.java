package it.unipv.ingsfw.SmartWarehouse.Database;
import java.util.List;
public interface IUserDAO {

	public boolean insertUser(User u);
	public String selectPassword(User u);
	public List<User> getUsersFromDatabase();
	public boolean deleteUser(User u);
	public User selectUserByEmail(User u);
	public String selectEmail(User u);
	
	
}
