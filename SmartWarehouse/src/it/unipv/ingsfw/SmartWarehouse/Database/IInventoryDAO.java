package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;
import java.util.Set;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;



public interface IInventoryDAO {
	List<InventoryItem> selectAllInventory();
	public InventoryItem getInventoryItemBySku(String sku);
	public boolean insertItemToInventory(InventoryItem i);
	public boolean deleteItem(String sku);
	public InventoryItem getInventoryItemByPosition(Position pos); //o return Item?
	//public boolean checkIfPositionAlreadyUsed(Position pos);
	public boolean updateInventoryItemQty(String sku, int qty);
	public Set<InventoryItem> viewItemsUnderStdLevel();
	public List<Object[]> getSuppliersInfo(InventoryItem i);
}
