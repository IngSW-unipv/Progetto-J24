package it.unipv.ingsfw.SmartWarehouse.Database;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.Position;

public interface IInventoryDAO {
	List<IInventoryItem> selectAllInventory();
	public boolean insertItemToInventory(IInventoryItem i);
	public boolean deleteItem(String sku);
	public IInventoryItem getInventoryItemBySku(String sku);
	public IInventoryItem getInventoryItemByPosition(Position pos);
	public int getItemQty(IInventoryItem i);
	public boolean updateInventoryItemQty(String sku, int qty);
	public List<IInventoryItem> viewItemsUnderStdLevel();
	public List<Object[]> getSuppliersInfo(IInventoryItem i);
}
