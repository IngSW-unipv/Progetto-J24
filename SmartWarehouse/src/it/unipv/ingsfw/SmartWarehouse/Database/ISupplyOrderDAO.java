package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyOrder;

public interface ISupplyOrderDAO {
	public List<SupplyOrder> selectAllSupplyOrders();
	public boolean insertSupplyOrder(SupplyOrder o);
	public int nextNOrder();
}
