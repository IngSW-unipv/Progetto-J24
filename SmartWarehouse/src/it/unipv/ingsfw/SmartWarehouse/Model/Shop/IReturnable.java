package it.unipv.ingsfw.SmartWarehouse.Model.Shop;

import java.time.LocalDateTime;
import java.util.HashSet;

import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;

public interface IReturnable {
	public String getEmail();
	public int getQtyBySku(String sku);
	public int getId();
	public LocalDateTime getDate();
	public HashSet<IInventoryItem> getSet();
	public void setDate(LocalDateTime date);
}
