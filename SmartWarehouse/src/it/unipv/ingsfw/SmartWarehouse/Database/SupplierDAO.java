package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supplier;

public class SupplierDAO implements ISupplierDAO{ 
	private String schema;
	private Connection conn;


	public SupplierDAO() {
		super();
		this.schema = "warehouse2";
	}
	
	public List<Supplier> selectAllSupplier(){
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;  
		ResultSet rs1;
		List<Supplier> suppliers=new ArrayList<>();
		
		try {
			String query= "select * from supplier";
			st1=conn.createStatement();
			rs1=st1.executeQuery(query);
			
			while(rs1.next()) {
				suppliers.add(new Supplier(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4)));
			}
			
		} catch  (Exception e) {
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return suppliers;
	}
	
	public Supplier getSupplierByIds(String ids) { 
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;
		Supplier result=null;
				
		try {
			String query= "select * from supplier where ids= ?";
			st1=conn.prepareStatement(query);
			st1.setString(1, ids);
			rs1=st1.executeQuery();
			if(rs1.next()) {
				result=new Supplier(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4));
			}
			
		} catch  (Exception e) {
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return result;
	}
	
	public boolean insertSupplier(Supplier f) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		boolean result=true;
		
		try {
			String query="insert into supplier (ids, fullname, address, email) values (?,?,?,?)";
			st1 = conn.prepareStatement(query);
			st1.setString(1, f.getIDS());
			st1.setString(2, f.getFullName());
			st1.setString(3, f.getAddress());
			st1.setString(4, f.getEmail());
			st1.executeUpdate(); 
			
		} catch  (Exception e) {
			e.printStackTrace();
			result=false;
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return result;
	}
	
	public boolean deleteSupplier(Supplier s) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		int rowsUpdated=0;
		
		try {
			String query="delete from supply where ids= ?";
			st1=conn.prepareStatement(query);
			st1.setString(1, s.getIDS());
			st1.executeUpdate();
			
			query="delete from supplier where ids= ?";
			st1=conn.prepareStatement(query);
			st1.setString(1, s.getIDS());
			rowsUpdated= st1.executeUpdate();
		} catch  (Exception e) {
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return rowsUpdated>0;
	}
	
	
}
 