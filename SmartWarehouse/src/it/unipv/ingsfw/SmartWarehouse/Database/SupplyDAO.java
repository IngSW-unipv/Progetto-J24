package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;

public class SupplyDAO implements ISupplyDAO {
	private String schema;
	private Connection conn;

	public SupplyDAO() {
		super();
		this.schema = "warehouse2";
	}
	
	public List<Supply> selectAllSupplies() {
		conn=DBConnection.startConnection(conn,schema);
		java.sql.Statement st1;
		ResultSet rs1;
		List<Supply> supplies=new ArrayList<>();
		
		try {
			String query= "select * from supply";
			st1=conn.createStatement();
			rs1=st1.executeQuery(query);
			
			while(rs1.next()) {
				supplies.add(new Supply(rs1.getString(1),rs1.getString(2), rs1.getString(3),rs1.getDouble(4),rs1.getInt(5)));
			}
			
		} catch  (Exception e) {
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return supplies; 
	}
	
	public Supply getSupplyByIDSupply(String IDSupply) { 
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;
		Supply result=null;
				
		try {
			String query= "select * from supply where idsupply = ?";
			st1=conn.prepareStatement(query);
			st1.setString(1, IDSupply);
			rs1=st1.executeQuery();
			if(rs1.next()) {
				result= new Supply(rs1.getString(1),rs1.getString(2), rs1.getString(3),rs1.getDouble(4),rs1.getInt(5));
			}
			
		} catch  (Exception e) { 
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return result;
	}
	
	public Supply getSupplyBySkuAndIds(String sku, String ids) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;
		Supply result=null;
				
		try {
			String query= "select * from supply where sku = ? and ids = ?";
			st1=conn.prepareStatement(query);
			st1.setString(1, sku);
			st1.setString(2, ids);
			rs1=st1.executeQuery();
			if(rs1.next()) {
				result= new Supply(rs1.getString(1),rs1.getString(2), rs1.getString(3),rs1.getDouble(4),rs1.getInt(5));
			}
			
		} catch  (Exception e) { 
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return result;
	}
	
	//tutti gli attributi di supply non devono essere null
	public boolean insertSupply(Supply s) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		
		try {
			String query="insert into supply (idsupply,sku,ids,price,maxqty) values (?,?,?,?,?)";
			st1 = conn.prepareStatement(query);
			st1.setString(1, s.getID_Supply());
			st1.setString(2, s.getInventoryItem().getSku());
			st1.setString(3, s.getSupplier().getIDS());
			st1.setDouble(4, s.getPrice());
			st1.setInt(5, s.getMaxqty());
			st1.executeUpdate(); 
			return true;
		} catch  (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
	}
	
	public boolean deleteSupply(Supply s) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		boolean result=true;
		
		try {
			String query="delete from supply where id_supply=?";
			st1 = conn.prepareStatement(query);
			st1.setString(1, s.getID_Supply());
			st1.executeUpdate(); //non mettere query tra parentesi
			
		} catch  (Exception e) {
			e.printStackTrace();
			result=false;
		} finally {
	        DBConnection.closeConnection(conn);
	    }
		return result;
	}
}
