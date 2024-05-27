package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supplier;

public class InventoryDAO implements IInventoryDAO { 
	private String schema;
	private Connection conn;
	
	public InventoryDAO () {
		super();
		this.schema="warehouse";
		// this.schema="smartwarehouse";  
	}  

	public List<InventoryItem> selectAllInventory(){  
		List<InventoryItem> inventory=new ArrayList<InventoryItem>();
		conn=DBConnection.startConnection(conn,schema);
		Statement st1; 
		ResultSet rs1;
 
		try
		{
			st1 = conn.createStatement();
			String query="select * from inventory order by description";
			rs1= st1.executeQuery(query);

			while(rs1.next())
			{
				InventoryItem l=new InventoryItem(rs1.getString(2), new ItemDetails(rs1.getInt(9), rs1.getInt(10), Category.fromString(rs1.getString(11))), 
						rs1.getString(1), rs1.getDouble(3), rs1.getInt(4), rs1.getInt(5), new Position(rs1.getString(6), rs1.getString(7),rs1.getString(8)));
				inventory.add(l);
			}
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    DBConnection.closeConnection(conn);
		}
		return inventory;  
	}
	
	//return null if the item is not present
	public InventoryItem getInventoryItemBySku(String sku) {
		InventoryItem result=null; 
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1 = null;
		 
		try {
			String query="select * from inventory where sku= ? ";
			st1 = conn.prepareStatement(query);
			st1.setString(1, sku);	
			rs1=st1.executeQuery();
			
			if(rs1.next()) {
			result = new InventoryItem(rs1.getString(2), new ItemDetails(rs1.getInt(9), rs1.getInt(10), Category.fromString(rs1.getString(11))), 
					rs1.getString(1), rs1.getDouble(3), rs1.getInt(4), rs1.getInt(5), new Position(rs1.getString(6), rs1.getString(7),rs1.getString(8)));
			}
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    DBConnection.closeConnection(conn);
		}
		return result; 
	} 
	
	//every field cannot be empty
	public boolean insertItemToInventory(InventoryItem i) { 
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		boolean result=true;
		
		try {
			String query="insert into inventory (sku,description,price,qty,stdLevel,line,pod,bin,fragility,dimension,category) values (?,?,?,?,?,?,?,?,?,?,?)";
			st1 = conn.prepareStatement(query);
			st1.setString(1, i.getSku());
			st1.setString(2, i.getDescription());
			st1.setDouble(3, i.getPrice());
			st1.setInt(4,i.getQty());
			st1.setInt(5, i.getStdLevel());
			st1.setString(6, i.getPos().getLine());
			st1.setString(7, i.getPos().getPod());
			st1.setString(8, i.getPos().getBin());
			st1.setInt(9, i.getDetails().getFragility());
			st1.setInt(10, i.getDetails().getDimension());
			st1.setString(11, i.getDetails().getCategory().getLabel());
			 
			st1.executeUpdate();
			
		} catch (Exception e) {
		    e.printStackTrace();
		    result=false;
		} finally {
		    DBConnection.closeConnection(conn);
		}
		return result;
	} 
	 
	public InventoryItem getInventoryItemByPosition(Position pos) { 
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;
		InventoryItem result=null; 
		 
	    try { 
			String query ="select * from inventory WHERE line = ? AND pod = ? AND bin = ?";
			st1 = conn.prepareStatement(query);
			st1.setString(1, pos.getLine());
			st1.setString(2, pos.getPod()); 
			st1.setString(3, pos.getBin());
			rs1 = st1.executeQuery();
			 
			if(rs1.next()) {
				result = new InventoryItem(rs1.getString(2), new ItemDetails(rs1.getInt(9), rs1.getInt(10), Category.fromString(rs1.getString(11))), 
						rs1.getString(1), rs1.getDouble(3), rs1.getInt(4), rs1.getInt(5), new Position(rs1.getString(6), rs1.getString(7),rs1.getString(8)));
			}					
	    }catch (Exception e){
			e.printStackTrace();
			return null; 
	    } finally {
		    DBConnection.closeConnection(conn);
		}
		return result;	
	}
	
	public boolean updateInventoryItemQty(String sku, int qty){   
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		int rowsUpdated=0;

		try {
			String query="update inventory set qty= ? where sku= ?";
			st1=conn.prepareStatement(query);
			st1.setInt(1, qty);
			st1.setString(2, sku);
			
			rowsUpdated= st1.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn);
		}
		return rowsUpdated>0;	
	}
	
	public boolean deleteItem(String sku) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		int rowsUpdated=0;

		try {
			String query="delete from inventory where sku=?";
			st1=conn.prepareStatement(query);
			st1.setString(1, sku);
			rowsUpdated= st1.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn);
		}
		return rowsUpdated>0;
	}
	
	public List<InventoryItem> viewItemsUnderStdLevel(){
		List<InventoryItem> result=new ArrayList<InventoryItem>();
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		ResultSet rs1; 
		
		try {
			String query="select * from inventory where qty<stdLevel";
			st1=conn.createStatement();
			rs1=st1.executeQuery(query);
			
			while(rs1.next()) {
				InventoryItem i=new InventoryItem(rs1.getString(2), new ItemDetails(rs1.getInt(9), rs1.getInt(10), Category.fromString(rs1.getString(11))), 
						rs1.getString(1), rs1.getDouble(3), rs1.getInt(4), rs1.getInt(5), new Position(rs1.getString(6), rs1.getString(7),rs1.getString(8)));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn);
		}
		return result;
	}
	 
	public List<Object[]> getSuppliersInfo(InventoryItem i) { 
		List<Object[]> result=new ArrayList<Object[]>();
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;
		Object[] row= new Object[3];
				
		try { 
			String query= "select su.*, s.price, s.maxqty from supply s join supplier su on s.ids=su.ids where sku=? order by s.price";
			st1=conn.prepareStatement(query);
			st1.setString(1, i.getSku());
			rs1=st1.executeQuery();
			while (rs1.next()) {
				row[0]= new Supplier(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4));
				row[1]= rs1.getDouble(5);
				row[2]= rs1.getInt(6); 
				result.add(row);
			}
			
		} catch  (Exception e) {
			e.printStackTrace();
		} finally {
	        DBConnection.closeConnection(conn); 
	    }
		return result;
	}
	
} 

