//
package it.unipv.ingsfw.SmartWarehouse.Model.Return;

import java.util.ArrayList;
import java.util.Map;

import it.unipv.ingsfw.SmartWarehouse.Database.IReturnServiceDAO;
import it.unipv.ingsfw.SmartWarehouse.Database.ReturnServiceDAO;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;

public class ReturnServiceDAOFacade {
	private static ReturnServiceDAOFacade istance;
	private IReturnServiceDAO rsdao;
	private ReturnServiceDAOFacade() {
		super();
		this.rsdao=new ReturnServiceDAO();
	}
	public static ReturnServiceDAOFacade getIstance() {
		if(istance==null) {
			istance=new ReturnServiceDAOFacade();
		}
		return istance;
	}
	
	public ReturnService findByOrder(IReturnable returnableOrder) {
		return rsdao.selectByOrder(returnableOrder);
		
	}
	public Map<IInventoryItem, String> readItemAndReason(IReturnable returnableOrder){
		return rsdao.selectItemAndReason(returnableOrder);
	}
	public ArrayList<IInventoryItem> readItem(IReturnable returnableOrder){
		return rsdao.selectItem(returnableOrder);
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
