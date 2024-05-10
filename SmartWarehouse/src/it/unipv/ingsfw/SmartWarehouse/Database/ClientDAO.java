package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import it.unipv.ingsfw.SmartWarehouse.Model.Client;


public class ClientDAO implements IClientDAO{
	
	private String schema;
	private Connection conn;
	
	public ClientDAO() {
		super();
		this.schema = "smartwarehouse";
	}
	
	@Override
	public Client selectClient(String email) {
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		ResultSet rs1;
		Client c=null;
		
		try {
			st1 = conn.createStatement();
			String query1="select* from clientorders where email="+email;
			rs1=st1.executeQuery(query1);
			while(rs1.next()) {
				 c=new Client(rs1.getString(0),rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4));
			}
			
		}catch(Exception e){e.printStackTrace(); return c;}
		return null;
	}

	@Override
	public void InsertClient(Client c) {
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		try {
			st1 = conn.createStatement();
			String query="update table Clients insert value(\""+c.getName()+"\",\""+c.getSurname()+"\",\""+c.getEmail()+"\",\""+c.getAddress()+"\",\""+c.getPassword()+"\",\""+c.getType()+"\";";
			st1.executeUpdate(query);
		}catch (Exception e){e.printStackTrace();}
		
	}

}
