package it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.InventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyDAOFacade;

public class ThresholdStrategy implements IReplenishmentStrategy {
	
	public ThresholdStrategy() {
		
	}
	
	//replenish items until the threshold
	@Override
	public void replenish(List<InventoryItem> items) throws AuthorizationDeniedException {
		int q;
		for(InventoryItem i:items) { 
			q = i.getQty();
			Supply s= SupplyDAOFacade.getInstance().getCheaperSupplyByInventoryItem(i);
			if (s!=null) {
				while (q < i.getStdLevel()) {
					s.replenishSupply(Math.min(s.getMaxqty(), i.getStdLevel()-q));	
					q += Math.min(s.getMaxqty(), i.getStdLevel()-q);
				}
			}
		}		
	}
	
	@Override
	public String getName() {
		return "Threshold Strategy";
	}
}
