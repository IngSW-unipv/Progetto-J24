package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyOrder;

public interface ISupplyOrderDAO {
	public List<SupplyOrder> selectAllSupplyOrders();
	public List<SupplyOrder> getSupplyOrdersBySupply(Supply s);
	public boolean insertSupplyOrder(SupplyOrder o);
	public int nextNOrder();
	public boolean deleteSupplyOrder(Supply s);
}
