package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import it.unipv.ingsfw.SmartWarehouse.Exception.PaymentException;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;

public class ReturnFACADE {
	private static ReturnFACADE instance;
	private ReturnService rs;
	public ReturnFACADE() {
	}
	public ReturnFACADE(IReturnable order) {
		this.rs=ResoManager.getIstance().getReturnService(order);
	}
	public static ReturnFACADE getInstance() {
		if(instance==null)
			instance=new ReturnFACADE();
		return instance;
	}
	public void addItemToReturn(String sku,String reason) {
		rs.addItemToReturn(sku, reason);
	}
	public void removeAllFromReturn() {
		rs.removeAllFromReturn();
	}
	public String toString(){
	
		return rs.toString();
	}
	public void setRefundMode(IRefund rm) throws PaymentException {
		rs.setRefundMode(rm);
	}
	public ReturnService getRs() {
		return rs;
	}
	public double getMoneyToBeReturned() {
		// TODO Auto-generated method stub
		return rs.getMoneyToBeReturned();
	}

}
