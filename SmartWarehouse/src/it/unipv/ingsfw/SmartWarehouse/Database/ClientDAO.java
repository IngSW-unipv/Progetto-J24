package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import it.unipv.ingsfw.SmartWarehouse.Model.user.Client;

public class ClientDAO implements IClientDAO{
	private String schema;
	private Connection conn;
	
	public ClientDAO() {
		super();
		this.schema = "warehouse";
	}
	
	@Override
	public void updatePrime(Client c) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
				
		try {
			String query= "update clients set prime = ? where email = ?";
			st1=conn.prepareStatement(query);
			st1.setBoolean(1, c.getPrime());
			st1.setString(2, c.getEmail());
			st1.executeUpdate();
			
		} catch  (Exception e) { 
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
	}
	
	@Override
	public void updateWallet(Client c) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
				
		try {
			String query= "update clients set wallet = ? where email = ?";
			st1=conn.prepareStatement(query);
			st1.setDouble(1, c.getWallet());
			st1.setString(2, c.getEmail());
			st1.executeUpdate();
			
		} catch  (Exception e) { 
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
	}
}
