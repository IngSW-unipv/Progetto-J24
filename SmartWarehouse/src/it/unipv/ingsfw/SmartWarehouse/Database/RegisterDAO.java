package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.Shop.OrderLine;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Category;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.ItemDetails;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;

public class RegisterDAO implements IRegisterDAO {
	
	private String schema;
	private Connection conn;
	
	public RegisterDAO() {
		super();
		this.schema = "warehouse";
	}

	public ArrayList<OrderLine> selectOrder(int id) { 
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		ResultSet rs1;
		String datetimeString;
		LocalDateTime date;
		
		try {
			st1 = conn.createStatement();
			String query1="select* from clientorders where id="+id;
			rs1=st1.executeQuery(query1);
            
			ArrayList<OrderLine> o = new ArrayList<OrderLine>();
			while(rs1.next()) {
				datetimeString = rs1.getString(5);
				date=LocalDateTime.parse(datetimeString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				o.add(new OrderLine(rs1.getInt(1),rs1.getString(2),
						rs1.getInt(3),rs1.getString(4), date, rs1.getBoolean(6)));
			}
			return o;
		}
		catch (Exception e){e.printStackTrace(); return null;}
	}
	
	
	 	public ArrayList< ArrayList<OrderLine> > selectOrderWhereClient(String email) { 
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;
		String datetimeString;
		LocalDateTime date;
		
		try {
			
			String query1="SELECT * FROM clientorders WHERE email = ? ORDER BY id";
			st1 = conn.prepareStatement(query1);
			st1.setString(1,email); 
			rs1=st1.executeQuery();
			ArrayList< ArrayList<OrderLine> > aao=new ArrayList< ArrayList<OrderLine> >();
			ArrayList<OrderLine> o = new ArrayList<OrderLine>();
			int currentOrderId = -1;
			while(rs1.next()) {
			    int orderId = rs1.getInt(1);
			    if (orderId != currentOrderId) {
                	if (!o.isEmpty()) {
                    	aao.add(o);
                	}
                	o = new ArrayList<>();
                	currentOrderId = orderId;
            	}
			    datetimeString = rs1.getString(5);
				date=LocalDateTime.parse(datetimeString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            	o.add(new OrderLine(orderId, rs1.getString(2),
                    rs1.getInt(3), rs1.getString(4), date, rs1.getBoolean(6)));
				
			}
			
			if (!o.isEmpty()) {
            aao.add(o);
        	}
        	return aao;
			 
		}
		catch (Exception e){e.printStackTrace(); return null;}
	}

	public void insertOrder(ArrayList<OrderLine> o) {
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		
		try {
			st1 = conn.createStatement();
			for(OrderLine ord: o) {
				String query = "insert into clientorders values("
						+ord.getId()+",\""+ord.getSku()+"\","+ord.getQty()+
						",\""+ord.getEmail()+"\""
						+ ",\"" + ""+ord.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"\","
						+ ""+ord.isPicked()+")";				
				st1.executeUpdate(query);
			}
		}
		catch (Exception e){e.printStackTrace();}

	}
	
	@Override
	public int selectLastId() {
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		
		try {
			st1=conn.createStatement();
			String query="select max(id) from clientorders";
			ResultSet rs1;
			
			rs1=st1.executeQuery(query);
			if(rs1.next()) {
				return rs1.getInt(1);
			}else return 0;
			
		}catch(Exception e) {e.printStackTrace(); return -1;}
	}
	
	public ArrayList<Integer> selectAllIds() {
	    conn=DBConnection.startConnection(conn,schema);
	    Statement st1;
	    ResultSet rs1;
	    try {
	        st1 = conn.createStatement();
	        String query = "SELECT id FROM clientorders ";
	        rs1 = st1.executeQuery(query);

	        ArrayList<Integer> orderIds = new ArrayList<>();
	        while (rs1.next()) {
	            orderIds.add(rs1.getInt("id"));
	        }
	        return orderIds;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	public ArrayList<OrderLine> showAllOrder() { 
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		ResultSet rs1;
		String datetimeString;
		LocalDateTime date;
		
		try {
			st1 = conn.createStatement();
			String query1="select* from clientorders ";
			rs1=st1.executeQuery(query1);
            
			ArrayList<OrderLine> o = new ArrayList<OrderLine>();
			while(rs1.next()) {
				datetimeString = rs1.getString(5);
				date=LocalDateTime.parse(datetimeString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				o.add(new OrderLine(rs1.getInt(1),rs1.getString(2),
						rs1.getInt(3),rs1.getString(4), date, rs1.getBoolean(6)));
			}
			return o;
		}
		catch (Exception e){e.printStackTrace(); return null;}
	}
	public int selectId(int id) {
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		
		try {
			st1=conn.createStatement();
			String query="select id from clientorders";
			ResultSet rs1;
			
			rs1=st1.executeQuery(query);
			if(rs1.next()) {
				return rs1.getInt(1);
			}else return 0;
			
		}catch(Exception e) {e.printStackTrace(); return -1;}
	} 
	/*
	 * when i finish packing,then i change the picked column
	 */
	public boolean setPicked(int id) {
	    Connection conn = null;
	    PreparedStatement st1 = null;
	    boolean result = false; 
	    try {
	        conn = DBConnection.startConnection(conn, schema);
	        String query = "update clientorders set picked = 1 where id = ?";
	        st1 = conn.prepareStatement(query);
	        st1.setInt(1, id);
	        int rowsUpdated = st1.executeUpdate();
	        if (rowsUpdated > 0) {
	            result = true;
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBConnection.closeConnection(conn);
	    }
	    return result; 
	}
	/*
	 * method for get the picked column 
	 */
	public boolean getPicked(int id) {
	    conn = DBConnection.startConnection(conn, schema);
	    Statement st1;
	    try {
	        st1 = conn.createStatement();
	        String query = "select picked from clientorders WHERE id = " + id; 
	        ResultSet rs1;
	        rs1 = st1.executeQuery(query);
	        if (rs1.next()) {
	            return rs1.getBoolean(1);
	        } else {
	            return false;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
