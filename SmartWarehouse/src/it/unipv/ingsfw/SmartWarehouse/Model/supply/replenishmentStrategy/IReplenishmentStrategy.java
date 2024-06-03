package it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy;


import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;

public interface IReplenishmentStrategy {
	public void replenish(List<InventoryItem> items) throws AuthorizationDeniedException;
	public String getName();
}

 