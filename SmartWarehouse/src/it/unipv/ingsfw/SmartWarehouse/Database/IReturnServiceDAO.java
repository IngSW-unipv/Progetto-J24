package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.ArrayList;
import java.util.Map;

import it.unipv.ingsfw.SmartWarehouse.Model.Return.IReturnable;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ItemToBeReturned;
import it.unipv.ingsfw.SmartWarehouse.Model.Return.ReturnService;
import it.unipv.ingsfw.SmartWarehouse.Model.Refund.IRefund;

public interface IReturnServiceDAO {
	public ArrayList<ReturnService> selectAll();
	public ReturnService selectByOrder(IReturnable returnableOrder);
	public Map<ItemToBeReturned, String> selectItemAndReason(IReturnable returnableOrder);
	public double selectMoneyAlreadyReturn(IReturnable returnableOrder);
	public boolean insertReturnService(ReturnService rs);
	public boolean deleteReturnService(ReturnService rs);
	public boolean insertRefundMode(ReturnService rs, IRefund rm);
	

}