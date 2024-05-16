package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ItemToBeReturned;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;

public class ReturnServiceDAO implements IReturnServiceDAO{
	
	private String schema;
	private Connection conn;


	public ReturnServiceDAO() {
		super();
		this.schema = "smartwarehouse";
	}
	@Override
	public ReturnService selectByOrder(IReturnable returnableOrder) {
		// TODO Auto-generated method stub
		
		ReturnService result = null;

		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
	
		ResultSet rs1;

		try
		{
			String query="SELECT * FROM RETURNSERVICE WHERE OrderID=?";
			st1 = conn.prepareStatement(query);
			st1.setInt(1,returnableOrder.getId()); 
			rs1=st1.executeQuery();

			if(rs1.next())
			{
				result = new ReturnService(returnableOrder);
			}
		}catch (Exception e){e.printStackTrace();}

		DBConnection.closeConnection(conn);
		return result;
	}


	public Map<ItemToBeReturned, String> selectItemAndReason(IReturnable returnableOrder) {
		// TODO Auto-generated method stub
		Map<ItemToBeReturned, String>  result = new HashMap<>();

		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
	
		ResultSet rs1;

		try
		{
			String query="SELECT ITEM,REASON FROM RETURNSERVICE WHERE ORDERID=?";
			st1 = conn.prepareStatement(query);
			st1.setInt(1,returnableOrder.getId()); 
			rs1=st1.executeQuery();

			while(rs1.next())
			{
				String id=rs1.getString(1);

				ItemToBeReturned itbr=new ItemToBeReturned(returnableOrder.getItem(id.substring(0,id.indexOf("-"))),id);
			
				result.put(itbr,rs1.getString(2));
			}
		}catch (Exception e){e.printStackTrace();}

		DBConnection.closeConnection(conn);
		return result;
	}
	public double selectMoneyAlreadyReturn(IReturnable returnableOrder) {
		double  result = 0;

		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
	
		ResultSet rs1;

		try
		{
			String query="SELECT MONEYALREADYRETURNED FROM RETURNSERVICE WHERE ORDERID=?";
			st1 = conn.prepareStatement(query);
			st1.setInt(1,returnableOrder.getId()); 
			rs1=st1.executeQuery();

			while(rs1.next())
			{
				result=rs1.getDouble(1);
			}
		}catch (Exception e){e.printStackTrace();}

		DBConnection.closeConnection(conn);
		return result;
	}
	
	public boolean insertReturnService(ReturnService rs) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1; 		
		
		boolean esito=true;

		try
		{
			String query="INSERT INTO RETURNSERVICE (OrderID,Item,Reason,MoneyAlreadyReturned) VALUES(?,?,?,?)";

			st1 = conn.prepareStatement(query);
			
			for (Map.Entry<ItemToBeReturned, String> entry : rs.getReturnedItems().entrySet()) {
	            st1.setInt(1, rs.getReturnableOrder().getId()); //evitare chiamate ricorsive
	            st1.setString(2, entry.getKey().getID()); 
	            st1.setString(3, entry.getValue()); 
	            st1.setDouble(4, rs.getMoneyAlreadyReturned());
	            st1.executeUpdate();
	        }

		}catch (Exception e){
			e.printStackTrace();
			esito=false;
		}

		DBConnection.closeConnection(conn);
		return esito;

	}
	@Override
	public boolean insertRefundMode(ReturnService rs, IRefund rm) {
		// TODO Auto-generated method stub
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1; 		
		
		boolean esito=true;

		try
		{
			String query="INSERT INTO REFUNDMODE (OrderID,RefundMode,Date) VALUES(?,?,?)";

			st1 = conn.prepareStatement(query);
	        st1.setInt(1, rs.getReturnableOrder().getId()); //evitare chiamate ricorsive
	        st1.setString(2,rm.toString()); 
	        st1.setTimestamp(3,new java.sql.Timestamp(new Date().getTime()));
	        st1.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
			esito=false;
		}

		DBConnection.closeConnection(conn);
		return esito;
	}
	public boolean deleteReturnService(ReturnService rs) {
		    conn = DBConnection.startConnection(conn, schema);
		    boolean success=true;
		    try {
		        String query = "DELETE FROM RETURNSERVICE WHERE ORDERID = ?";
		        PreparedStatement st1 = conn.prepareStatement(query);
		        st1.setInt(1, rs.getReturnableOrder().getId()); 
		        int rowsAffected = st1.executeUpdate();
		        if (rowsAffected <= 0) {
		            success = false;
		            //System.out.println("Nessuna riga è stata eliminata");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        success = false;
		    }
		    DBConnection.closeConnection(conn);
		    return success;
		
	}

	@Override
	public ArrayList<ReturnService> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}


	

}
