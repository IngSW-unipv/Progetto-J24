package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.unipv.ingsfw.SmartWarehouse.SmartWarehouseInfoPoint;
import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;

public class ReturnService { 
	private IReturnable returnableOrder;
	private Map<IInventoryItem, String> returnedItems;
	private double moneyAlreadyReturned;
	private ReturnServiceDAOFacade returnServiceDAOFacade;
	private int DEADLINE_FOR_MAKING_RETURN;

	
	/*
	 * Constructor and Checking the order date to verify returnability.
	 */
	public ReturnService(IReturnable returnableOrder) throws UnableToReturnException  {
		this.returnableOrder = returnableOrder;
		this.returnedItems = new HashMap<>();
		this.moneyAlreadyReturned=0;
		this.returnServiceDAOFacade=ReturnServiceDAOFacade.getIstance(); 
		DEADLINE_FOR_MAKING_RETURN=SmartWarehouseInfoPoint.getDeadlineForMakingReturn();
		if(!ReturnValidator.checkReturnability(this)) {
			throw new UnableToReturnException();
		}
	}

	/*
	 * Methods related to the Return process
	 */
	public void addItemToReturn(IInventoryItem inventoryItem,String reason) throws UnableToReturnException, MissingReasonException{
		if(!ReturnValidator.checkReturnabilityOfInventoryItem(inventoryItem,this)){
			throw new UnableToReturnException(returnableOrder.getDescBySku(inventoryItem.getSku()));
		}
		returnedItems.put(inventoryItem,getReason(reason));
	}
	public void removeAllFromReturn() {
		returnedItems.clear();
	}
	public double getMoneyToBeReturned(){
		double moneyToBeReturned=0;
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
			moneyToBeReturned+=inventoryItem.getPrice();
		}
		moneyToBeReturned=moneyToBeReturned-moneyAlreadyReturned;
		return moneyToBeReturned;
	}
	private String getReason(String reason) throws MissingReasonException {
		return Reasons.findReason(reason);
	}
	public boolean issueRefund(IRefund rm) throws PaymentException {
		return rm.issueRefund();
	}
	public void updateWarehouseQty(){
		InventoryDAOFacade idv=InventoryDAOFacade.getInstance();
		/*decrease item already Returned*/
		for(IInventoryItem i:returnServiceDAOFacade.readItem(this.returnableOrder)){
			try {
				if(i!=null)
					idv.findInventoryItemBySku(i.getSku()).decreaseQty();
			} catch (IllegalArgumentException | ItemNotFoundException e) {
				e.printStackTrace();
			}
		}
		/*increase all item qty*/
		for(IInventoryItem i:getReturnedItemsKeySet()) {
			try {
				InventoryDAOFacade.getInstance().findInventoryItemBySku(i.getSku()).increaseQty();
			} catch (IllegalArgumentException | ItemNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public void AddReturnToDB(IRefund rm) {
		//Adding the return to the DB
		returnServiceDAOFacade.writeReturnService(this);
		returnServiceDAOFacade.writeRefundMode(this,rm);
	}

	/*
	 * toString() Method
	 */
	public String toString(){
		StringBuilder s= new StringBuilder();
		s.append("Here are the items you have returned to date\n");
		s.append("Return Service: Order: ").append(returnableOrder.getId());
		s.append("\n Returned items are: \n");
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
			s.append(inventoryItem.getDescription()).append(". The reason is: ").append(returnedItems.get(inventoryItem)).append("\n");
		}
		return s.toString();
	} 

	/*
	 * setters and getters
	 */
	public void setReturnableOrder(IReturnable returnableOrder) {
		this.returnableOrder = returnableOrder;
	}
	public void setReturnedItems(Map<IInventoryItem, String> returnedItems) {
		this.returnedItems = returnedItems;
	}
	public void setMoneyAlreadyReturned(double moneyAlreadyReturned) {
		this.moneyAlreadyReturned = moneyAlreadyReturned;
	}

	public IReturnable getReturnableOrder() {
		return returnableOrder;
	}
	public Map<IInventoryItem, String> getReturnedItems() {
		return returnedItems;
	}
	public double getMoneyAlreadyReturned() {
		return this.moneyAlreadyReturned;
	}

	/*
	 * setters and getters ad hoc
	 */
	public Set<IInventoryItem> getReturnedItemsKeySet() {
		return getReturnedItems().keySet();
	}
	public LocalDateTime getCriticalDate() {
		return returnableOrder.getDate().plusDays(DEADLINE_FOR_MAKING_RETURN);
	}
	public int getQtyYouAreAllowedToReturn(IInventoryItem inventoryItem) {
		return returnableOrder.getQtyBySku(inventoryItem.getSku());
	}
	public String getEmailOfReturnableOrder(){
		return returnableOrder.getEmail();	
	}
	public int getIdOfReturnableOrder() {
		return returnableOrder.getId();
	}
}