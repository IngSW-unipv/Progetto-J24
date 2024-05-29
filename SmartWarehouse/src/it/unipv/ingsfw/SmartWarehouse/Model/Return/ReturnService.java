package it.unipv.ingsfw.SmartWarehouse.Model.Return;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
	private Map<InventoryItem, String> returnedItems;
	private double moneyAlreadyReturned;

	/*
	 * Constructor and Checking the order date to verify returnability.
	 */
	public ReturnService(IReturnable returnableOrder) throws UnableToReturnException, ParseException  {
		if(!checkReturnability(returnableOrder)) {
			throw new UnableToReturnException();
		}
		this.returnableOrder = returnableOrder;
		this.returnedItems = new HashMap<>();
		this.moneyAlreadyReturned=0;
	}
	private boolean checkReturnability(IReturnable returnableOrder) throws ParseException {
		String orderDateString = returnableOrder.getDate();

		/*
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
		Date orderDate = dateFormat.parse(orderDateString);
		 */

		// Specifica il formato della data
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ITALIAN);

		// Parso la stringa della data
		Date orderDate = dateFormat.parse(orderDateString);

		// Aggiungere 5 giorni alla data dell'ordine
		Calendar cal = Calendar.getInstance();
		cal.setTime(orderDate);
		cal.add(Calendar.DAY_OF_MONTH, 5);
		Date criticalDate = cal.getTime();

		// Ottenere la data attuale
		Date currentDate = new Date();

		// Controllare se la data attuale è precedente alla data critica
		return currentDate.before(criticalDate);
	}




	/*
	 * Methods related to the Return process
	 */
	public void addItemToReturn(InventoryItem inventoryItem,String reason) throws UnableToReturnException, MissingReasonException{
		if(!checkReturnabilityOfInventoryItem(inventoryItem)){
			throw new UnableToReturnException(returnableOrder.getDescBySku(inventoryItem.getSku()));
		}
		returnedItems.put(inventoryItem,setReason(reason));
	}
	private boolean checkReturnabilityOfInventoryItem(InventoryItem inventoryItem){
		if(returnableOrder.getQtyBySku(inventoryItem.getSku())<getQtyReturned(inventoryItem.getSku())+1) {
			return false;
		}
		return true;
	}
	public int getQtyReturned(String sku) {
		int count=0;
		for(InventoryItem inventoryItem:returnedItems.keySet()) {
			if(inventoryItem.getSku().equals(sku)) {
				count++; 
			}
		}
		return count;
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
		for(InventoryItem inventoryItem:returnedItems.keySet()) {
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
		ResoManager resoManager=ResoManager.getIstance(); //chiedere se è meglio averlo come attributo e chiedere l'istanza una volta sola nel costruttore
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
	public void setReturnedItems(Map<InventoryItem, String> returnedItems) {
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
	public Map<InventoryItem, String> getReturnedItems() {
		return returnedItems;
	}
}