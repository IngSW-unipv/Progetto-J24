package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import it.unipv.ingsfw.SmartWarehouse.Model.Shop.OrderLine;

public class RegisterDAO implements IRegisterDAO {
	
	private String schema;
	private Connection conn;
	
	public RegisterDAO() {
		super();
		this.schema = "smartwarehouse";
	}

	public ArrayList<OrderLine> selectOrder(int id) { 
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		ResultSet rs1;
		
		try {
			st1 = conn.createStatement();
			String query1="select* from clientorders where id="+id;
			rs1=st1.executeQuery(query1);
            
			ArrayList<OrderLine> o = new ArrayList<OrderLine>();
			while(rs1.next()) {
				o.add(new OrderLine(rs1.getInt(1),rs1.getString(2),
						rs1.getInt(3),rs1.getString(4),rs1.getString(5)));
			}
			return o;
		}
		catch (Exception e){e.printStackTrace(); return null;}
	}
	
	
	 	/*public ArrayList< ArrayList<OrderLine> > selectOrderWhereClient(String email) { 
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		ResultSet rs1;
		
		try {
			st1 = conn.createStatement();
			String query1="select * from clientorders where email="+email+"order by id";
			rs1=st1.executeQuery(query1);
			
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
            	o.add(new OrderLine(orderId, rs1.getString(2),
                    rs1.getInt(3), rs1.getString(4), rs1.getString(5)));
				
			}
			
			if (!o.isEmpty()) {
            aao.add(o);
        	}
        	return aao;
			 
		}
		catch (Exception e){e.printStackTrace(); return null;}
	}*/

	public void insertOrder(ArrayList<OrderLine> o) {
		conn=DBConnection.startConnection(conn,schema);
		Statement st1;
		
		try {
			st1 = conn.createStatement();
			for(OrderLine ord: o) {
				String query = "insert into clientorders values("
						+ord.getId()+",\""+ord.getSku()+"\","+ord.getQty()+
						",\""+ord.getEmail()+"\",\""+ord.getDate()+"\")";				
				st1.executeUpdate(query);
			}
		}
		catch (Exception e){e.printStackTrace();}

	}
	
	@Override
	public int selectLastId() {
		conn=DBConnection.startConnection(conn, schema);
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

}
