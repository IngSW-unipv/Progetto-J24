package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import it.unipv.ingsfw.SmartWarehouse.Database.ReturnServiceFacade;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;

public class ResoManager {
	private ReturnServiceFacade returnServiceFacade;
	private static ResoManager istance;
	private ResoManager() {
		super();
        returnServiceFacade=ReturnServiceFacade.getIstance();
	}
	public static ResoManager getIstance() {
		if(istance==null){
			istance=new ResoManager();
		}
		return istance;
	}
    public ReturnService getReturnService(IReturnable returnableOrder) {
    	ReturnService rs=returnServiceFacade.findByOrder(returnableOrder);
    	System.out.println(rs);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    /*private static Map<IReturnable, ReturnService> resiCreati = new HashMap<>();

    public static ReturnService getReso(IReturnable returnableOrder) {
        if (resiCreati.containsKey(returnableOrder)) {
            System.out.println("---------Restituisco l'istanza di Reso già esistente per l'oggetto rendibile fornito-----------.");
            return resiCreati.get(returnableOrder);
        } else {
        	ReturnService r = new ReturnService(returnableOrder);
            resiCreati.put(returnableOrder, r);
            return r;
        }
    }*/
}
