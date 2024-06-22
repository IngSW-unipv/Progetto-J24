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
	
    /**
     * Retrieves the ReturnService for a given returnable order.
     * If the return service does not exist, a new one is created.
     * @param returnableOrder the order to be returned
     * @return the ReturnService associated with the given order
     * @throws UnableToReturnException if the order is not returnable
     * @throws ReturnableOrderNullPointerException if the returnable order is null
     */
    public ReturnService getReturnService(IReturnable returnableOrder) throws UnableToReturnException {
    	if(returnableOrder==null) {
    		throw new ReturnableOrderNullPointerException();
    	}
    	ReturnService rs=returnServiceDAOFacade.findByOrder(returnableOrder);
    	 rs = (rs == null) ? new ReturnService(returnableOrder) : rs;
    	 rs.setReturnedItems(returnServiceDAOFacade.readItemAndReason(returnableOrder)); //valutare se devo passare solo rs oppure se è giusto come ho fatto
    	 rs.setMoneyAlreadyReturned(returnServiceDAOFacade.readMoneyAlreadyReturned(returnableOrder));
    	 return rs;
    }
}
