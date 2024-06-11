package it.unipv.ingsfw.SmartWarehouse.Database;


import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.WarehouseOperator;
import java.sql.Connection;

public class UserDAO implements IUserDAO{
	private String schema;
	private Connection conn;
	
	public UserDAO() {
		super();
		this.schema = "warehouse";
	}
	
	public User getClientByEmail(String email) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;
		User result=null;
				
		try {
			String query= "select * from clients where email = ? ";
			st1=conn.prepareStatement(query);
			st1.setString(1, email);
			rs1=st1.executeQuery();
			
			if(rs1.next()) {
				System.out.println(rs1.getString(4));
				result= new Client(rs1.getString(1),rs1.getString(2), rs1.getString(3),rs1.getString(4));
			}
			
		} catch  (Exception e) { 
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return result;
	}

	
	
	public boolean insertClient (Client c) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		
		try {
			String query="insert into clients (name,surname,email,password) values (?,?,?,?)";
			st1 = conn.prepareStatement(query);
			st1.setString(1, c.getName());
			st1.setString(2, c.getSurname());
			st1.setString(3, c.getEmail());
			st1.setString(4, c.getPassword());
			st1.executeUpdate(); 
			return true;
		} catch  (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
	        DBConnection.closeConnection(conn); 
	    }

	}
		
	public String selectPassword(User u) {
        String email = u.getEmail();
        String result = "";
        conn = DBConnection.startConnection(conn, schema);
        PreparedStatement st1;
        ResultSet rs1;

        try {
            String query = "select password from clients where email= ?";
            st1 = conn.prepareStatement(query);
            st1.setString(1, email);
            rs1 = st1.executeQuery(); 

            if (rs1.next()) {
                result = rs1.getString("password"); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
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
			String query = "select email from clients " + "where email= '" + email + "'";

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
		String pw=null; 
		User result = null;
		conn = DBConnection.startConnection(conn, schema);
		Statement st1;
		ResultSet rs1;

		try {
			st1 = conn.createStatement();
			String query = "select * from clients where email= '" + emails ;

			rs1 = st1.executeQuery(query);

			if (rs1.next()) {

				String name= rs1.getString("name");
				String surname = rs1.getString("surname");
				String email = rs1.getString("email");
				String password = rs1.getString("password");
				result = new User(name, surname, email);
			}else {
				return u;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return result;
	}
	
	public boolean deleteUser(User u) {

		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1;

		boolean esito = true;

		try {
			
			
			String query = "delete from `clients` where (`email` = '"+u.getEmail()+"')";
					
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
	            String query = "select * from user"; 
	            statement = conn.prepareStatement(query);
	            resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                String name = resultSet.getString("name");
	                String surname = resultSet.getString("surname");
	                String email = resultSet.getString("email");
	                User user = new User(name, surname, email);
	                userList.add(user);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 
	        
	        DBConnection.closeConnection(conn);
	        return userList;
	    }
	 public User getOpById (String id) {
			conn=DBConnection.startConnection(conn,schema);
			PreparedStatement st1;
			ResultSet rs1;
			User result=null;
					
			try {
				String query= "select * from operator where OperatorID = ? ";
				st1=conn.prepareStatement(query);
				st1.setString(1, id);
				rs1=st1.executeQuery();
				if(rs1.next()) {
					result= new WarehouseOperator(rs1.getString(1),rs1.getString(2), rs1.getString(3),rs1.getString(4));
				}
				
			} catch  (Exception e) { 
				e.printStackTrace();
			} finally {
		        DBConnection.closeConnection(conn); 
		    }
			return result;
		}
	
		public boolean insertOperator(WarehouseOperator op) {
			conn=DBConnection.startConnection(conn,schema);
			PreparedStatement st1;
			boolean result=true;
			
			try {
				String query=" insert into operator (OperatorID,name,surname,email) values (?,?,?,?)";
				st1 = conn.prepareStatement(query);
				st1.setString(1, op.getId());
				st1.setString(2, op.getName());
				st1.setString(3, op.getSurname());
				st1.setString(4,op.getEmail());
				st1.executeUpdate();
				
			} catch (Exception e) {
			    e.printStackTrace();
			    result=false;
			} finally {
			    DBConnection.closeConnection(conn);
			}
			return result;
			
		}
}
