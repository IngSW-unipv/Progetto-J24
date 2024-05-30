package it.unipv.ingsfw.SmartWarehouse.Model.Return;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

//

public class ReturnService { 
	private IReturnable returnableOrder;
	private Map<IInventoryItem, String> returnedItems;
	private double moneyAlreadyReturned;

	/*
	 * Constructor and Checking the order date to verify returnability.
	 */
	public ReturnService(IReturnable returnableOrder) throws UnableToReturnException  {
		if(!ReturnValidator.checkReturnability(this)) {
			throw new UnableToReturnException();
		}
		this.returnableOrder = returnableOrder;
		this.returnedItems = new HashMap<>();
		this.moneyAlreadyReturned=0;
	}

	/*
	 * Methods related to the Return process
	 */
	public void addItemToReturn(IInventoryItem inventoryItem,String reason) throws UnableToReturnException, MissingReasonException{
		if(!ReturnValidator.checkReturnabilityOfInventoryItem(inventoryItem,this)){
			throw new UnableToReturnException(returnableOrder.getDescBySku(inventoryItem.getSku()));
		}
		returnedItems.put(inventoryItem,setReason(reason));
	}

	public void removeAllFromReturn() {
		returnedItems.clear();
	}
	public String setReason(String reason) throws MissingReasonException {
		return Reasons.findReason(reason);
	}
	public double getMoneyToBeReturned()
	{
		double moneyToBeReturned=0;
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
			moneyToBeReturned+=inventoryItem.getPrice();

		}
		moneyToBeReturned=moneyToBeReturned-moneyAlreadyReturned;
		return moneyToBeReturned;
	}
	public boolean setRefundMode(IRefund rm) throws PaymentException { //valutare la gestione di boolean al posto di void
		return rm.issueRefund();
	}
	public void AddReturnToDB(IRefund rm) {
		//Adding the return to the DB
		ReturnManager resoManager=ReturnManager.getIstance(); //chiedere se è meglio averlo come attributo e chiedere l'istanza una volta sola nel costruttore
		resoManager.addReturnServiceToDB(this);
		resoManager.addRefundModeToDB(this,rm);
	}
	public String toString()
	{
		StringBuilder s= new StringBuilder();
		s.append("Reso dell'ordine: ").append(returnableOrder.getId());
		s.append("\ngli articoli restituiti sono: \n");
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
			s.append(inventoryItem.getSku()).append(" ").append(inventoryItem.getDescription()).append(" la cui motivazione è: ").append(returnedItems.get(inventoryItem)).append(" in data "+LocalDate.now()).append("\n");
		}
		return s.toString();

	} 

	/*
	 * setters and getters
	 */
	public void setReturnedItems(Map<IInventoryItem, String> returnedItems) {
		this.returnedItems = returnedItems;
	}

	public void setMoneyAlreadyReturned(double moneyAlreadyReturned) {
		this.moneyAlreadyReturned = moneyAlreadyReturned;
	}

	public void setReturnableOrder(IReturnable returnableOrder) {
		this.returnableOrder = returnableOrder;
	}
	public IReturnable getReturnableOrder() {
		return returnableOrder;
	}
	public double getMoneyAlreadyReturned() {
		return this.moneyAlreadyReturned;
	}
	public Map<IInventoryItem, String> getReturnedItems() {
		return returnedItems;
	}
	/*
	 * setters and getters ad hoc
	 */
	public Set<IInventoryItem> getReturnedItemsKeySet() {
		return getReturnedItems().keySet();
	}
	public LocalDateTime getCriticalDate() {
		return returnableOrder.getDate().plusDays(5);
	}
	public int getqtyYouAreAllowedToReturn(IInventoryItem inventoryItem) {
		return returnableOrder.getQtyBySku(inventoryItem.getSku());
	}
}