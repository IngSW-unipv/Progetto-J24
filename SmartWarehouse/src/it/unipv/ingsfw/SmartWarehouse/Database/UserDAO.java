package it.unipv.ingsfw.SmartWarehouse.Database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

public class UserDAO implements IUserDAO {
	private String schema;
	private Connection conn;
	public UserDAO() {
		super();
		this.schema = "smartwharehouse";
	}
	public boolean insertUser(User u) {
		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1;
		boolean esito = true;
		try {
			String query = "INSERT INTO `smartwarehouse`.`user` (`name`, `surname`, `email`, `address`, `Email`, `password`, `type`)"
					+ " VALUES(?,?,?,?,?,?,?)";
			st1 = conn.prepareStatement(query);
			st1.setString(1, u.getName());
			st1.setString(2, u.getSurname());
			st1.setString(3, u.getEmail());
			st1.setString(4, u.getAddress());
			st1.setString(5, u.getPassword());
			st1.setString(6,u.getType() );
			st1.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			esito = false;
		}

		DBConnection.closeConnection(conn);
		return esito;

	}
	public String selectPassword(User u) {
		String email = u.getEmail();
		String result = new String();
		conn = DBConnection.startConnection(conn, schema);
		Statement st1;
		ResultSet rs1;

		try {
			st1 = conn.createStatement();
			String query = "SELECT Password FROM smartwarehouse.user " + "WHERE Email= '" + email + "'";

			rs1 = st1.executeQuery(query);

			if (rs1.next()) {
				result = rs1.getString("Password");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return result;
	}

	public String selectEmail(User u) {

		String email = u.getEmail();
		String result = new String();
		conn = DBConnection.startConnection(conn, schema);
		Statement st1;
		ResultSet rs1;

		try {
			st1 = conn.createStatement();
			String query = "SELECT email FROM smartwarehouse.user " + "WHERE email= '" + email + "'";

			rs1 = st1.executeQuery(query);

			if (rs1.next()) {
				result = rs1.getString("email");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return result;
	}

	public User selectUserByEmail(User u) {

		String emails = u.getEmail();
		String pw = u.getPassword();
		User result = null;

		conn = DBConnection.startConnection(conn, schema);
		Statement st1;
		ResultSet rs1;

		try {
			st1 = conn.createStatement();
			String query = "SELECT * FROM smartwharehouse.user WHERE email= '" + emails + "'"
					+ "and utente.Password='"+pw+"'";

			rs1 = st1.executeQuery(query);

			if (rs1.next()) {

				String name= rs1.getString("name");
				String surname = rs1.getString("surname");
				String address = rs1.getString("address");
				String email = rs1.getString("email");
				String type = rs1.getString("type");
				String password = rs1.getString("password");
				result = new User(name, surname, address, email,type, password);
			}else {
				return u;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return result;
	}
	
	//Metodo utile nel test della registrazione
	public boolean deleteUser(User u) {

		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1;

		boolean esito = true;

		try {
			
			
			String query = "DELETE FROM `smartwarehous`.`user` WHERE (`email` = '"+u.getEmail()+"')";
					
			st1 = conn.prepareStatement(query);

			st1.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			esito = false;
		}

		DBConnection.closeConnection(conn);
		return esito;

	}
	
	 public List<User> getUsersFromDatabase() {
	        List<User> userList = new ArrayList<>();
	        Connection conn = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            conn = DBConnection.startConnection(conn, schema);
	            String query = "SELECT * FROM user"; // Query per selezionare tutti gli utenti
	            statement = conn.prepareStatement(query);
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                String name = resultSet.getString("name");
	                String surname = resultSet.getString("surname");
	                String address = resultSet.getString("addres");
	                String email = resultSet.getString("email");
	                String type = resultSet.getString("type");
	                String password = resultSet.getString("Password");

	                User user = new User(name, surname, address, email,type, password);
	                userList.add(user);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 
	        
	        DBConnection.closeConnection(conn);
	        return userList;
	    }
}
