package order;

import java.util.List;

import exception.ItemNotFoundException;
import exception.QuantityMismatchException;
import inventory.InventoryItem;

public interface IPackageable {
	public List<InventoryItem> getSkuqtyAsList();
	public void selectItemqty(InventoryItem item, int desiredQuantity)throws ItemNotFoundException, QuantityMismatchException;
	public void changeDate(Order o);
}
