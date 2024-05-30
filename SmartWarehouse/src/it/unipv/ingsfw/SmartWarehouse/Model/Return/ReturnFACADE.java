//
package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.text.ParseException;
import java.util.Map;

import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public class ReturnFACADE {
	private ReturnService rs;
	public ReturnFACADE() {
	}
	public ReturnFACADE(IReturnable order) throws UnableToReturnException, ParseException { 
		if(order==null) {
			throw new NullPointerException("Selezionare un ordine per continuare");
		}
		this.rs=ReturnManager.getIstance().getReturnService(order);
	}
	public void addItemToReturn(InventoryItem inventoryItem,String reason) throws UnableToReturnException, MissingReasonException {
		rs.addItemToReturn(inventoryItem, reason);
	}

	public void removeAllFromReturn() {
		rs.removeAllFromReturn();
	}
	
	public String toString(){
	
		return rs.toString();
	}
	public void setRefundMode(IRefund rm) throws PaymentException {
		rs.setRefundMode(rm);
	}
	public ReturnService getRs() {
		return rs;
	}
	public double getMoneyToBeReturned() {
		// TODO Auto-generated method stub
		return rs.getMoneyToBeReturned();
	}
	public Map<IInventoryItem, String> getReturnedItems() {
		return rs.getReturnedItems();
	}
	public void AddReturnToDB(IRefund refundMode) {
		// TODO Auto-generated method stub
		rs.AddReturnToDB(refundMode);
		
	}
	public void setMoneyAlreadyReturned(double moneyAlreadyReturned) {
		// TODO Auto-generated method stub
		rs.setMoneyAlreadyReturned(moneyAlreadyReturned);
		
	}
	public double getMoneyAlreadyReturned() {
		// TODO Auto-generated method stub
		return rs.getMoneyAlreadyReturned();
	}
	public IReturnable getReturnableOrder() {
		// TODO Auto-generated method stub
		return rs.getReturnableOrder();
	}

}
