package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.util.HashSet;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public interface IReturnable {
	public InventoryItem getItemBySku(String sku);
	public int getQtyBySku(String sku);
	public String getDescBySku(String sku);
	public int getId();
	public String getDate();
	public HashSet<InventoryItem> getSet();
	public void setDate(String date);
}
