package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public interface IReturnable {
	public InventoryItem getItemBySku(String sku);
	public int getQtyBySku(String sku);
	public String getDescBySku(String sku);
}
