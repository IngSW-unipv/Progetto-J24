package it.unipv.ingsfw.SmartWarehouse.Database;


import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Controller.PickingController;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;
import it.unipv.ingsfw.SmartWarehouse.Model.user.User;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.InventoryOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.PickingOperator;
import it.unipv.ingsfw.SmartWarehouse.Model.user.operator.SupplyOperator;
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
					switch(id.charAt(0)) {
						case 'i':
							result = new InventoryOperator(rs1.getString(1),rs1.getString(2), rs1.getString(3),rs1.getString(4));
							break;
						case 's':
							result = new SupplyOperator(rs1.getString(1),rs1.getString(2), rs1.getString(3),rs1.getString(4));
							break;
						case 'p':
							result = new PickingOperator(rs1.getString(1),rs1.getString(2), rs1.getString(3),rs1.getString(4));
							break;
					}
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
