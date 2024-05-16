package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.Map;


import it.unipv.ingsfw.SmartWarehouse.Model.Return.ItemToBeReturned;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;

public class ReturnServiceFacade {
	private static ReturnServiceFacade istance;
	private IReturnServiceDAO rsdao;
	private ReturnServiceFacade() {
		super();
		this.rsdao=new ReturnServiceDAO();
	}
	public static ReturnServiceFacade getIstance() {
		if(istance==null) {
			istance=new ReturnServiceFacade();
		}
		return istance;
	} 
	
	public ReturnService findByOrder(IReturnable returnableOrder) {
		return rsdao.selectByOrder(returnableOrder);
		
	}
	public Map<ItemToBeReturned, String> readItemAndReason(IReturnable returnableOrder){
		return rsdao.selectItemAndReason(returnableOrder);
	}
	public double readMoneyAlreadyReturned(IReturnable returnableOrder) {
		return rsdao.selectMoneyAlreadyReturn(returnableOrder);
	}
	public boolean writeReturnService(ReturnService rs) {
		rsdao.deleteReturnService(rs);
		return rsdao.insertReturnService(rs);
	}
	public boolean writeRefundMode(ReturnService rs, IRefund rm) {
		// TODO Auto-generated method stub
		return rsdao.insertRefundMode(rs,rm);
	}
	
	
	
}
