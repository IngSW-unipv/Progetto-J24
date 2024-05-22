package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supplier;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;


public interface ISupplyDAO {
	public List<Supply> selectAllSupplies();
	public Supply getSupplyByIDSupply(String IDSupply);
	public Supply getSupplyBySkuAndIds(String sku, String ids);
	public List<Supply> getSupplyBySku(String sku);
	public boolean insertSupply(Supply s);
	public boolean deleteSupply(Supply s);
	public boolean deleteSupplyOfSupplier(Supplier s);
	public Supply getCheaperSupplyByInventoryItem(InventoryItem i);
}
