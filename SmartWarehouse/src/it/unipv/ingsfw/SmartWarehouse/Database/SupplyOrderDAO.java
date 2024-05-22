package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyOrder;

public class SupplyOrderDAO implements ISupplyOrderDAO {
	private String schema;
	private Connection conn;
	
	public SupplyOrderDAO() {
		super();
		this.schema="warehouse"; 
	} 
	
	public List<SupplyOrder> selectAllSupplyOrders(){
		List<SupplyOrder> orders=new ArrayList<>();
		conn=DBConnection.startConnection(conn, schema);
		Statement st1;
		ResultSet rs1;
		
		String datetimeString;
		LocalDateTime date;
		
		try {
			st1=conn.createStatement();
			String query="select * from supplyOrders";
			rs1=st1.executeQuery(query);
			
			while(rs1.next()) { 
				datetimeString = rs1.getString(5);
				date=LocalDateTime.parse(datetimeString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				orders.add(new SupplyOrder(rs1.getInt(1),rs1.getString(2),rs1.getInt(3),rs1.getDouble(4),date));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn);
		}
		return orders;
	} 
	
	 
	public List<SupplyOrder> getSupplyOrdersBySupply(Supply s){
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;
		List<SupplyOrder> result = new ArrayList<SupplyOrder>();
		
		String datetimeString;
		LocalDateTime date;
				
		try {
			String query= "select * from supplyOrders where idsupply = ?";
			st1=conn.prepareStatement(query);
			st1.setString(1, s.getID_Supply());
			rs1=st1.executeQuery();
			if(rs1.next()) {
				datetimeString = rs1.getString(5);
				date=LocalDateTime.parse(datetimeString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				result.add(new SupplyOrder(rs1.getInt(1),rs1.getString(2),rs1.getInt(3),rs1.getDouble(4),date));
			}
			
		} catch  (Exception e) { 
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return result;
	} 
	
	public boolean insertSupplyOrder(SupplyOrder o) {
		conn=DBConnection.startConnection(conn, schema);
		PreparedStatement st1;
		boolean b=true;
		
		try {
			String query="insert into supplyOrders(ids_order,idsupply,qty,price,date) values (?,?,?,?,?)";
			st1=conn.prepareStatement(query);
			st1.setInt(1, o.getN_order());
			st1.setString(2, o.getSupply().getID_Supply());
			st1.setInt(3, o.getQty());
			st1.setDouble(4, o.getPrice());
			st1.setString(5, o.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			st1.executeUpdate();
			
		} catch(Exception e) {
			b=false;
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn);
		}
		return b; 
	}
	
	//returns NOrder of the next supply order
	public int nextNOrder() {
		conn=DBConnection.startConnection(conn, schema);
		Statement st1;
		ResultSet rs1;
		int nmax=0;
		
		try {
			st1=conn.createStatement();
			String query="select max(ids_order) from supplyOrders ";
			rs1=st1.executeQuery(query);
			
			if(rs1.next()) { 
				nmax=rs1.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn);
		}
		return nmax+1;
	} 
	
	public boolean deleteSupplyOrder(Supply s) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		boolean result=true;
		
		try {
			String query="delete from supplyOrders where idsupply = ?";
			st1 = conn.prepareStatement(query);
			st1.setString(1, s.getID_Supply());
			st1.executeUpdate(); 
			
		} catch  (Exception e) {
			e.printStackTrace();
			result=false;
		} finally {
	        DBConnection.closeConnection(conn);
	    }
		return result;
	}
		
}
