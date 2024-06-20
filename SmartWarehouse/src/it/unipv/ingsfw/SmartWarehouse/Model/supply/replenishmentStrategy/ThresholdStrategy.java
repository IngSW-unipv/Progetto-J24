package it.unipv.ingsfw.SmartWarehouse.Model.supply.replenishmentStrategy;

import java.util.List;

import it.unipv.ingsfw.SmartWarehouse.Exception.AuthorizationDeniedException;
import it.unipv.ingsfw.SmartWarehouse.Model.inventory.IInventoryItem;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.Supply;
import it.unipv.ingsfw.SmartWarehouse.Model.supply.SupplyDAOFacade;

public class ThresholdStrategy implements IReplenishmentStrategy {
	
	public ThresholdStrategy() {
		
	}
	
	/**
	 * The method replenishes the InventoryItems until the threshold (stdLevel) from the cheaper Supplier. 
	 * If an InventoryItem has not a Supplier associated it will not be replenished.
	 */
	@Override
	public void replenish(List<IInventoryItem> items) throws AuthorizationDeniedException {
		int q;
		int orderQty;
		for(IInventoryItem i:items) { 
			q = i.getQty();
			Supply s= SupplyDAOFacade.getInstance().getCheaperSupplyByInventoryItem(i);
			if (s!=null) {
				while (q < i.getStdLevel()) { 
					orderQty = Math.min(s.getMaxqty(), i.getStdLevel()-q);
					s.replenishSupply(orderQty);
					q += orderQty;
				}
			}
		}		
	}
	
	@Override
	public String getName() {
		return "Threshold Strategy";
	}
}
