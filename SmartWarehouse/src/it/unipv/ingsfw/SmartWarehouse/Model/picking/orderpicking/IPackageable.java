package it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public interface IPackageable {
	public List<InventoryItem> getSkuqtyAsList();
	public void selectItemqty(InventoryItem item, int desiredQuantity)throws ItemNotFoundException, QuantityMismatchException;
	public void changeDate(Orderp o);
}
