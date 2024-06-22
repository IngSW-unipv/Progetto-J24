package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.SmartWarehouseInfoPoint;

public class ReturnService { 
	private IReturnable returnableOrder;
	private Map<IInventoryItem, String> returnedItems;
	private double moneyAlreadyReturned;
	private ReturnServiceDAOFacade returnServiceDAOFacade;
	private int deadlineForMakingReturn;

	
	/**
	 * Constructor and Checking the order date to verify returnability.
	 * @param returnableOrder the order to be returned
	 * @throws UnableToReturnException if the order is not returnable
	 */
	public ReturnService(IReturnable returnableOrder) throws UnableToReturnException  {
		this.returnableOrder = returnableOrder;
		this.returnedItems = new HashMap<>();
		this.moneyAlreadyReturned=0;
		this.returnServiceDAOFacade=ReturnServiceDAOFacade.getIstance(); 
		deadlineForMakingReturn=SmartWarehouseInfoPoint.getDeadlineForMakingReturn();
		
		if(!ReturnValidator.checkReturnability(this)) {
			throw new UnableToReturnException();
		}
	}

	/**
	 * Adds an item to the return process.
	 * @param inventoryItem the item to be returned
	 * @param reason the reason for the return
	 * @throws UnableToReturnException if the item is not returnable
	 * @throws MissingReasonException if the reason is missing or invalid
	 */
	public void addItemToReturn(IInventoryItem inventoryItem,String reason) throws UnableToReturnException, MissingReasonException{
		if(!ReturnValidator.checkReturnabilityOfInventoryItem(inventoryItem,this)){
			throw new UnableToReturnException(inventoryItem.getDescription());
		}
		returnedItems.put(inventoryItem,findReason(reason));
	}
	
	/**
	 * Removes all items from the return process.
	 */
	public void removeAllFromReturn() {
		returnedItems.clear();
	}
	
	/**
	 * Calculates the total amount of money to be returned.
	 * @return the amount of money to be returned
	 */
	public double getMoneyToBeReturned(){
		double moneyToBeReturned=0;
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
			moneyToBeReturned+=inventoryItem.getPrice();
		}
		moneyToBeReturned=moneyToBeReturned-moneyAlreadyReturned;
		return moneyToBeReturned;
	}
	
	/**
	 * Finds the reason.
	 * @param reason the reason string
	 * @return the reason
	 * @throws MissingReasonException if the reason is missing or invalid
	 */
	private String findReason(String reason) throws MissingReasonException {
		return Reasons.findReason(reason);
	}
	
	/**
	 * Issues a refund.
	 * @param rm IRefund 
	 * @return true if the refund was successful, false otherwise
	 * @throws PaymentException if there is a payment error
	 */
	public boolean issueRefund(IRefund rm) throws PaymentException {
		return rm.issueRefund();
	}
	
	/**
	 * Updates the warehouse quantities for the returned items.
	 */
	public void updateWarehouseQty(){
		/*decrease item already Returned*/
		for(IInventoryItem i:returnServiceDAOFacade.readItem(this.returnableOrder)){
			try {
				//if(i!=null)
					i.decreaseQty();
			} catch (IllegalArgumentException | ItemNotFoundException e) {
				e.printStackTrace();
			}
		}
		/*increase all item qty*/
		for(IInventoryItem i:getReturnedItemsKeySet()) {
			try {
				i.increaseQty();
			} catch (IllegalArgumentException | ItemNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds the return and refund mode to the database.
	 * @param rm IRefund
	 */
	public void AddReturnToDB(IRefund rm) {
		returnServiceDAOFacade.writeReturnService(this);
		returnServiceDAOFacade.writeRefundMode(this,rm);
	}

	/**
	 * toString() Method
	 */
	public String toString(){
		StringBuilder s= new StringBuilder();
		s.append("Here are the items you have returned to date\n");
		s.append("Return Service: Order: ").append(getIdOfReturnableOrder());
		s.append("\n Returned items are: \n");
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
			s.append(inventoryItem.getDescription()).append(". The reason is: ").append(returnedItems.get(inventoryItem)).append("\n");
		}
		return s.toString();
	} 

	/**
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

	/**
	 * setters and getters ad hoc
	 */
	public Set<IInventoryItem> getReturnedItemsKeySet() {
		return getReturnedItems().keySet();
	}
	public LocalDate getCriticalDate() {
		return returnableOrder.getDate().toLocalDate().plusDays(deadlineForMakingReturn);
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