package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;

public interface ISupplyDAO {
	public List<Supply> selectAllSupplies();
	public Supply getSupplyByIDSupply(String IDSupply);
	public Supply getSupplyBySkuAndIds(String sku, String ids);
	public List<Supply> getSupplyBySku(String sku);
	public List<Supply> getSupplyByIds(String ids);
	public boolean insertSupply(Supply s);
	public boolean deleteSupply(Supply s);
	public Supply getCheaperSupplyByInventoryItem(InventoryItem i);
}
