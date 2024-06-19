package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;

public interface IInventoryDAO {
	List<InventoryItem> selectAllInventory();
	public boolean insertItemToInventory(InventoryItem i);
	public boolean deleteItem(String sku);
	public InventoryItem getInventoryItemBySku(String sku);
	public InventoryItem getInventoryItemByPosition(Position pos);
	public int getItemQty(IInventoryItem i);
	public boolean updateInventoryItemQty(String sku, int qty);
	public List<InventoryItem> viewItemsUnderStdLevel();
	public List<Object[]> getSuppliersInfo(InventoryItem i);
}
