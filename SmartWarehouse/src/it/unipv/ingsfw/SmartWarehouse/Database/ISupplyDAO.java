package db;

import java.util.List;
import java.util.Set;

import model.supply.Supply;

public interface ISupplyDAO {
	public List<Supply> selectAllSupplies();
	public Supply getSupplyByIDSupply(String IDSupply);
	public Supply getSupplyBySkuAndIds(String sku, String ids);
	public boolean insertSupply(Supply s);
	public boolean deleteSupply(Supply s);
}
