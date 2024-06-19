package it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy;


import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;

public interface IReplenishmentStrategy {
	public void replenish(List<IInventoryItem> items) throws AuthorizationDeniedException;
	public String getName();
}

 