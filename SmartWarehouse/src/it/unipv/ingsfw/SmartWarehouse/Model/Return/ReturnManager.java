//
package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import it.unipv.ingsfw.SmartWarehouse.Exception.ReturnableOrderNullPointerException;
import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;

public class ReturnManager {
	private ReturnServiceDAOFacade returnServiceDAOFacade;
	private static ReturnManager istance;
	/*
	 * Singleton Methods
	 */
	private ReturnManager() {
		super();
        returnServiceDAOFacade=ReturnServiceDAOFacade.getIstance();
	}
	public static ReturnManager getIstance() {
		if(istance==null){
			istance=new ReturnManager();
		}
		return istance;
	}
	
	/*
	 * DAO Methods
	 */
    public ReturnService getReturnService(IReturnable returnableOrder) throws UnableToReturnException {
    	if(returnableOrder==null) {
    		throw new ReturnableOrderNullPointerException();
    	}
    	ReturnService rs=returnServiceDAOFacade.findByOrder(returnableOrder);
    	 if(rs==null) {
    		 return new ReturnService(returnableOrder);
    	 }
    	 rs.setReturnedItems(returnServiceDAOFacade.readItemAndReason(returnableOrder)); //valutare se devo passare solo rs oppure se è giusto come ho fatto
    	 rs.setMoneyAlreadyReturned(returnServiceDAOFacade.readMoneyAlreadyReturned(returnableOrder));
    	 return rs;
    }
}
