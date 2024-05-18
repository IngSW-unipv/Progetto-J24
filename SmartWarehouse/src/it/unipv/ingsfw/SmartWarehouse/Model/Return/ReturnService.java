package it.unipv.ingsfw.SmartWarehouse.Model.Return;
import java.util.HashMap;
import java.util.Map;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;


public class ReturnService { 
    private IReturnable returnableOrder;
    private Map<ItemToBeReturned,String> returnedItems;
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
    public void addItemToReturn(String sku,String reason) { //throws ImpossibileToReturnException
    	if(!checkReturnability(sku))
    	{
    		System.err.println("Quantità massima restituibile raggiunta per l'articolo "+returnableOrder.getDescBySku(sku)); //gestire eccezione.
    		//System.exit(-1);
    		return;
    	}
    	ItemToBeReturned itbr=new ItemToBeReturned(returnableOrder.getItemBySku(sku)); //chiedi se è meglio passargli solo this e poi in ItemToBeReturned usare i metodi di this per ricavarsi l'item
    	returnedItems.put(itbr,setReason(reason));
    	System.out.println(returnableOrder.getDescBySku(sku)+" è stato aggiunto al reso");
    	}
    private boolean checkReturnability(String sku){
    	if(returnableOrder.getQtyBySku(sku)<getQtyReturned(sku)+1) {
    		return false;
    	}
    	return true;
    }
    public int getQtyReturned(String sku) {
    	int count=0;
    	for(ItemToBeReturned itbr:returnedItems.keySet()) {
    		if(itbr.getSku().equals(sku)) {
    			count++; 
    		}
    	}
    	 return count;
    }
    public void removeAllFromReturn() {
    	returnedItems.clear();
    }
    public String setReason(String reason) {
    	return Reasons.findReason(reason);
     }
    public void setRefundMode(IRefund rm) throws PaymentException { //valutare la gestione di boolean al posto di void e spostare i metodi scrittura su DB
    	rm.issueRefund();
    	
    	
    	 //Adding the return to the DB
    	ResoManager resoManager=ResoManager.getIstance(); //chiedere se è meglio averlo come attributo e chiedere l'istanza una volta sola nel costruttore
    	resoManager.addReturnServiceToDB(this);
    	resoManager.addRefundModeToDB(this,rm);
    }
    public double getMoneyToBeReturned()
    {
    	double moneyToBeReturned=0;
    	for(ItemToBeReturned itbr:returnedItems.keySet()) {
    		moneyToBeReturned+=itbr.getPriceItem();
    	}
    	moneyToBeReturned=moneyToBeReturned-moneyAlreadyReturned;
    	moneyAlreadyReturned=moneyToBeReturned;
    	return moneyToBeReturned;
    }
    public String toString()
    {
    	StringBuilder s= new StringBuilder();
    	s.append("Reso dell'ordine: ").append(returnableOrder.getId());
    	s.append("\ngli articoli restituiti sono: \n");
    	for(ItemToBeReturned itbr:returnedItems.keySet()) {
    		s.append(itbr.getSku()).append(" ").append(itbr.getDescription()).append(" la cui motivazione è: ").append(returnedItems.get(itbr)).append("\n");
    	}
    	return s.toString();
    	
    } 
    
  /*
   * setters and getters
   */
	public void setReturnedItems(Map<ItemToBeReturned, String> returnedItems) {
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
		return moneyAlreadyReturned;
	}
	public Map<ItemToBeReturned, String> getReturnedItems() {
		return returnedItems;
	}
}