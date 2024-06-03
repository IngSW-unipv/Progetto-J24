package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.MissingReasonException;
import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.SingletonManager;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.RefundFactory;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.BankTransfer.BankTransfer;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.Voucher.VoucherRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryDAOFacade;

//

public class ReturnService { 
	private IReturnable returnableOrder;
	private Map<IInventoryItem, String> returnedItems;
	private double moneyAlreadyReturned;
	/*
	 * Constructor and Checking the order date to verify returnability.
	 */
	public ReturnService(IReturnable returnableOrder) throws UnableToReturnException  {
		this.returnableOrder = returnableOrder;
		this.returnedItems = new HashMap<>();
		this.moneyAlreadyReturned=0;
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
		returnedItems.put(inventoryItem,setReason(reason));
	}

	public void removeAllFromReturn() {
		returnedItems.clear();
	}
	public String setReason(String reason) throws MissingReasonException {
		return Reasons.findReason(reason);
	}
	public double getMoneyToBeReturned(){
		double moneyToBeReturned=0;
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
			moneyToBeReturned+=inventoryItem.getPrice();
		}
		moneyToBeReturned=moneyToBeReturned-moneyAlreadyReturned;
		return moneyToBeReturned;
	}
	public IRefund createBankTransferRefund() {
		BankTransfer br = new BankTransfer(getMoneyToBeReturned(),"EMAIL MAGAZZINO DA DEFINIRE",SingletonManager.getInstance().getLoggedUser().getEmail() );
		return RefundFactory.getBankTransferAdapter(br);
	}
	public IRefund createVoucherRefund() {
		VoucherRefund vr = new VoucherRefund(getMoneyToBeReturned());
		return RefundFactory.getVoucherAdapter(vr);
	}
	public boolean issueRefund(IRefund rm) throws PaymentException {
		return rm.issueRefund();
	}
	public void updateWarehouseQty(){
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
		ReturnManager resoManager=ReturnManager.getIstance(); //chiedere se è meglio averlo come attributo e chiedere l'istanza una volta sola nel costruttore
		resoManager.addReturnServiceToDB(this);
		resoManager.addRefundModeToDB(this,rm);
	}
	public String toString(){
		StringBuilder s= new StringBuilder();
		s.append("Reso dell'ordine: ").append(returnableOrder.getId());
		s.append("\ngli articoli restituiti sono: \n");
		for(IInventoryItem inventoryItem:returnedItems.keySet()) {
			s.append(inventoryItem.getSku()).append(" ").append(inventoryItem.getDescription()).append(" la cui motivazione è: ").append(returnedItems.get(inventoryItem)).append("\n");
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
		return returnableOrder.getDate().plusDays(30);
	}
	public int getQtyYouAreAllowedToReturn(IInventoryItem inventoryItem) {
		return returnableOrder.getQtyBySku(inventoryItem.getSku());
	}
}