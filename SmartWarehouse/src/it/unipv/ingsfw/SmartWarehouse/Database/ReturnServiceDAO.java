//
package it.unipv.ingsfw.SmartWarehouse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonUser;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;

public class ReturnServiceDAO implements IReturnServiceDAO{

	private String schema;
	private Connection conn;


	public ReturnServiceDAO() {
		super();
		this.schema = "warehouse";
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
			String query="SELECT * FROM RETURNSERVICE WHERE OrderID=? AND ClientEmail=?";
			st1 = conn.prepareStatement(query);
			st1.setInt(1,returnableOrder.getId());
			st1.setString(2,SingletonUser.getInstance().getLoggedUser().getEmail()); 
			rs1=st1.executeQuery();

			if(rs1.next())
			{
				result = new ReturnService(returnableOrder);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			DBConnection.closeConnection(conn);
		}
		return result;
	}


	public Map<IInventoryItem, String> selectItemAndReason(IReturnable returnableOrder) {
		// TODO Auto-generated method stub
		Map<IInventoryItem, String>  result = new HashMap<>();

		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;

		ResultSet rs1;

		try
		{
			String query="SELECT ITEM,REASON FROM RETURNSERVICE WHERE ORDERID=? AND ClientEmail=?";
			st1 = conn.prepareStatement(query);
			st1.setInt(1,returnableOrder.getId()); 
			st1.setString(2,SingletonUser.getInstance().getLoggedUser().getEmail()); 
			rs1=st1.executeQuery();

			while(rs1.next())
			{
				IInventoryItem inventoryItem=InventoryDAOFacade.getInstance().findInventoryItemBySku(rs1.getString(1));
				result.put(inventoryItem,rs1.getString(2));
			}
		}catch (Exception e){e.printStackTrace();}
		finally {
			DBConnection.closeConnection(conn);
		}
		return result;
	}
	public ArrayList<IInventoryItem> selectItem(IReturnable returnableOrder) {
		ArrayList<IInventoryItem> result=new ArrayList<IInventoryItem>();

		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;

		ResultSet rs1;

		try
		{
			String query="SELECT ITEM FROM RETURNSERVICE WHERE ORDERID=? AND ClientEmail=?";
			st1 = conn.prepareStatement(query);
			st1.setInt(1,returnableOrder.getId()); 
			st1.setString(2,SingletonUser.getInstance().getLoggedUser().getEmail()); 
			rs1=st1.executeQuery();

			while(rs1.next())
			{
				result.add(InventoryDAOFacade.getInstance().findInventoryItemBySku(rs1.getString(1)));
				
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			DBConnection.closeConnection(conn);
		}
		return result;
	}
	public double selectMoneyAlreadyReturn(IReturnable returnableOrder) {
		double  result = 0;

		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1;
		ResultSet rs1;

		try
		{
			String query="SELECT MONEYALREADYRETURNED FROM RETURNSERVICE WHERE ORDERID=? AND ClientEmail=?";
			st1 = conn.prepareStatement(query);
			st1.setInt(1,returnableOrder.getId());
			st1.setString(2,SingletonUser.getInstance().getLoggedUser().getEmail()); 
			rs1=st1.executeQuery();

			while(rs1.next())
			{
				result=rs1.getDouble(1);
			}
		}catch (Exception e){e.printStackTrace();}

		finally {
			DBConnection.closeConnection(conn);
		}
		return result;
	}

	public boolean insertReturnService(ReturnService returnService) {
		conn=DBConnection.startConnection(conn,schema);
		PreparedStatement st1; 		

		boolean esito=true;

		try
		{
			String query="INSERT INTO RETURNSERVICE (OrderID,Item,Reason,MoneyAlreadyReturned,ClientEmail) VALUES(?,?,?,?,?)";

			st1 = conn.prepareStatement(query);

			for (Map.Entry<IInventoryItem, String> entry : returnService.getReturnedItems().entrySet()) {
				st1.setInt(1, returnService.getReturnableOrder().getId()); //evitare chiamate ricorsive
				st1.setString(2, entry.getKey().getSku()); 
				st1.setString(3, entry.getValue()); 
				st1.setDouble(4, returnService.getMoneyAlreadyReturned());
				st1.setString(5, SingletonUser.getInstance().getLoggedUser().getEmail());
				st1.executeUpdate();
			}

		}catch (Exception e){
			e.printStackTrace();
			esito=false;
		}

		finally {
			DBConnection.closeConnection(conn);
		}
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
			String query="INSERT INTO REFUNDMODE (OrderID,RefundMode,Date,ClientEmail) VALUES(?,?,?,?)";

			st1 = conn.prepareStatement(query);
			st1.setInt(1, rs.getReturnableOrder().getId()); //evitare chiamate ricorsive
			st1.setString(2,rm.toString()); 
			st1.setTimestamp(3,new java.sql.Timestamp(new Date().getTime()));
			st1.setString(4, SingletonUser.getInstance().getLoggedUser().getEmail());
			st1.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
			esito=false;
		}

		finally {
			DBConnection.closeConnection(conn);
		}
		return esito;
	}
	public boolean deleteReturnService(ReturnService rs) {
		conn = DBConnection.startConnection(conn, schema);
		boolean success=true;
		try {
			String query = "DELETE FROM RETURNSERVICE WHERE ORDERID = ? AND ClientEmail=?";
			PreparedStatement st1 = conn.prepareStatement(query);
			st1.setInt(1, rs.getReturnableOrder().getId());
			st1.setString(2, SingletonUser.getInstance().getLoggedUser().getEmail());
			int rowsAffected = st1.executeUpdate();
			if (rowsAffected <= 0) {
				success = false;
				//System.out.println("Nessuna riga è stata eliminata");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		}
		finally {
			DBConnection.closeConnection(conn);
		}
		return success;

	}




}
