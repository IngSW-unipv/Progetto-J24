package it.unipv.ingsfw.SmartWarehouse.Model.Return;


import java.text.ParseException;

import it.unipv.ingsfw.SmartWarehouse.Exception.UnableToReturnException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;

public class ResoManager {
	private ReturnServiceDAOFacade returnServiceFacade;
	private static ResoManager istance;
	private ResoManager() {
		super();
        returnServiceFacade=ReturnServiceDAOFacade.getIstance();
	}
	public static ResoManager getIstance() {
		if(istance==null){
			istance=new ResoManager();
		}
		return istance;
	}
    public ReturnService getReturnService(IReturnable returnableOrder) throws UnableToReturnException, ParseException {
    	ReturnService rs=returnServiceFacade.findByOrder(returnableOrder);
    	 if(rs==null) {
    		 return new ReturnService(returnableOrder);
    	 }
    	 rs.setReturnedItems(returnServiceFacade.readItemAndReason(returnableOrder)); //valutare se devo passare solo rs oppure se è giusto come ho fatto
    	 rs.setMoneyAlreadyReturned(returnServiceFacade.readMoneyAlreadyReturned(returnableOrder));
    	 return rs;
    }
    public boolean addReturnServiceToDB(ReturnService rs){
    	return returnServiceFacade.writeReturnService(rs);
    	
    }
	public boolean addRefundModeToDB(ReturnService rs, IRefund rm) {
		// TODO Auto-generated method stub
		return returnServiceFacade.writeRefundMode(rs,rm);
	}
}
