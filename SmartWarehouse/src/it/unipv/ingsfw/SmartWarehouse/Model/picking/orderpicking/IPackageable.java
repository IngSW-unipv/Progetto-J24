package it.unipv.ingsfw.SmartWarehouse.Model.picking.orderpicking;
import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.ItemNotFoundException;
import it.unipv.ingsfw.SmartWarehouse.Exception.QuantityMismatchException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.Shop.*;
public interface IPackageable {
	public List<InventoryItem> takeInfoItem();
	public int calculateTotalSize(); 
	public int totaldimItem();
	public boolean tfFragility();
	public void setSkuqtyIntegerValue(InventoryItem item, int value);	
	public List<InventoryItem> getSkuqtyAsList();
	public void changeDate(Order o); 
	public void selectItemqty(InventoryItem item, int desiredQuantity) throws ItemNotFoundException, QuantityMismatchException;

}


