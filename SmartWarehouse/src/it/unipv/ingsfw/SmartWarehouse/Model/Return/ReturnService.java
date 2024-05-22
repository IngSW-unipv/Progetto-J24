package it.unipv.ingsfw.SmartWarehouse.Model.Return;
import java.util.HashMap;
import java.util.Map;

import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;


public class ReturnService { 
	private IReturnable returnableOrder;
	private Map<InventoryItem, String> returnedItems;
	private double moneyAlreadyReturned;

	public ReturnService(IReturnable returnableOrder) {
		this.returnableOrder = returnableOrder;
		this.returnedItems = new HashMap<>();
		this.moneyAlreadyReturned=0;
		Reasons.initializeReasons();
	}

	/*
	 * Methods related to the Return process
	 */
	public void addItemToReturn(InventoryItem inventoryItem,String reason) throws UnableToReturnException, MissingReasonException{
		if(!checkReturnability(inventoryItem)){
			throw new UnableToReturnException(returnableOrder.getDescBySku(inventoryItem.getSku()));
		}
		returnedItems.put(inventoryItem,setReason(reason));
	}
	private boolean checkReturnability(InventoryItem inventoryItem){
		if(returnableOrder.getQtyBySku(inventoryItem.getSku())<getQtyReturned(inventoryItem.getSku())+1) {
			return false;
		}
		return true;
	}
	public int getQtyReturned(String sku) {
		int count=0;
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
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
	public void setRefundMode(IRefund rm) throws PaymentException { //valutare la gestione di boolean al posto di void e spostare i metodi scrittura su DB
		rm.issueRefund();
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
			s.append(inventoryItem.getSku()).append(" ").append(inventoryItem.getItem().getDescription()).append(" la cui motivazione è: ").append(returnedItems.get(inventoryItem)).append("\n");
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