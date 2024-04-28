package db;

import java.util.List;

import model.supply.SupplyOrder;

public interface ISupplyOrderDAO {
	public List<SupplyOrder> selectAllSupplyOrders();
	public boolean insertSupplyOrder(SupplyOrder o);
	public int nextNOrder();
}
